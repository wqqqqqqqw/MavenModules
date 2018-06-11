package dynamicproxy;

import annotations.ACache;
import service.IService;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

public class DynamicCachingProxy implements InvocationHandler {

    private IService serviceInstance;
    private LinkedHashMap<Integer, Object> cacheMemory;
    private String cacheFileName;

    final private String cacheEntryDelimiter = "#<";
    final private String cacheResultDelimiter = "%>";
    final private StringBuilder stringBuilder;

    public DynamicCachingProxy(IService serviceInstance, String cacheFileName) {
        // изначально загружаем кэш из файла.
        // затем будем работать с этой копией, а при отсутствии в ней результата будем добавлять его в память и/или в файл.
        // в процессе выполнения будем брать результаты только из памяти.
        // если затем произойдет перезапуск процесса, все вновь добавленные данные должны остаться на диске
        // и при следующем старте этот образ будет считан с диска при необходимости

        this.serviceInstance = serviceInstance;
        this.stringBuilder = new StringBuilder();
        this.cacheMemory = new LinkedHashMap<>();
        this.cacheFileName = cacheFileName;

        File f = new File(cacheFileName);
        if (f.exists())
            deserialize();
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = 0.0;
        Method serviceInstanceMethod = serviceInstance.getClass().getMethod(method.getName(), method.getParameterTypes());

        if (methodNeedsCaching(serviceInstanceMethod)) {
            Integer methodID = getMethodID(serviceInstanceMethod, args);
            ACache.CacheType cacheType = serviceInstanceMethod.getAnnotation(ACache.class).cacheType();

            System.out.println("\nМеханизм прокси-кэширования. Cache length: " + cacheMemory.size() +
                                                            ". Type: " + cacheType);

            // если в памяти есть, возвращаем результат
            if (cacheMemory.containsKey(methodID)) {
                System.out.println("Get from Mem");
                return cacheMemory.get(methodID);
            } else {
                // если в памяти нет
                // вычисляем и добавляем в память
                result = method.invoke(serviceInstance, args);
                System.out.println("Put into Mem");
                cacheMemory.put(methodID, result);

                // если выбрано кэширование в файл, сериализуем дописыванием в файл
                if (cacheType == ACache.CacheType.FILE) {
                    System.out.println("Serialize");
                    serialize();
                }
            }
        } else {
            System.out.println("\nОбычное выполнение.");
            result = method.invoke(serviceInstance, args);
        }
        System.out.println("Result: " + result);
        return result;
    }

    private boolean methodNeedsCaching(Method method) {
        return method != null && method.getAnnotation(ACache.class) != null;
    }

    private Integer getMethodID(Method method, Object[] args) {
        Class[] params = method.getAnnotation(ACache.class).identityBy();
        int paramsLength = params.length;
        int argsLength = args.length;

        StringBuilder stringBuilder = new StringBuilder(method.getName());

        // если в функцию были переданы аргументы
        if (argsLength != 0) {
            // если указан пустой массив аргументов метода (params.length == 0),
            // учитываемых для определения уникальности результата, //(по умолчанию)
            // то учитываем все аргументы и имя метода
            if (paramsLength == 0) {
                for (Object o : args) {
                    stringBuilder.append(o);
                }
            }
            // в противном случае
            // перебираем классы в params, пока не достигнем конца args или params,
            // и сравниваем на равенство классы (при равенстве классов учитываем соответствующий параметр).
            // если класс в params примитивный, сравниваем с началом имени класса в args (т.к. args массив Object)
            else {
                for (int paramsCntr = 0; paramsCntr < paramsLength && paramsCntr < argsLength; ++paramsCntr) {
                    if (params[paramsCntr] == args[paramsCntr].getClass()) {
                        stringBuilder.append(args[paramsCntr]);
                    } else if (params[paramsCntr].isPrimitive()) {
                        if (args[paramsCntr].getClass().getSimpleName().toLowerCase()
                                .startsWith(params[paramsCntr].toString())) {
                            stringBuilder.append(args[paramsCntr]);
                        }
                    }
                }
            }
        }
        // если в функцию не были переданы аргументы,
        // учитываем только имя метода

        return stringBuilder.toString().hashCode();
    }

    private void serialize() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(cacheFileName)) {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream)) {
                for (Integer methodID : cacheMemory.keySet()) {
                    stringBuilder.append(methodID)
                            .append(cacheResultDelimiter)
                            .append(cacheMemory.get(methodID))
                            .append(cacheEntryDelimiter);
                }

                outputStream.writeUTF(stringBuilder.toString());

                //System.out.println(stringBuilder.toString());
                stringBuilder.setLength(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deserialize() {
        try (FileInputStream fileInputStream = new FileInputStream(cacheFileName)) {
            try (ObjectInputStream inputStream = new ObjectInputStream(fileInputStream)) {
                String cachedContent = "";
                cachedContent = inputStream.readUTF();

                String[] cacheEntries = cachedContent.split(cacheEntryDelimiter);
                System.out.println("-----Кэш с диска");
                for (String entryString : cacheEntries) {
                    String[] entry = entryString.split(cacheResultDelimiter);
                    Integer methodID = Integer.parseInt(entry[0]);
                    double result = Double.parseDouble(entry[1]);
                    cacheMemory.put(methodID, result);
                    System.out.println(methodID + " -> " + result);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("--=> Ошибка: Файл " + cacheFileName + " не найден\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
