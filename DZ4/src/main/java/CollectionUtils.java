import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

//Параметризовать методы, используя правило PECS, и реализовать их.
public class CollectionUtils {

    public static <T> void addAll(List<? extends T> source, List<? super T> destination) {
        destination.addAll(source);
    }

    public static <T> List<T> newArrayList() {
        return new ArrayList<>();
    }

    public static <T> int indexOf(List<? extends T> source, T o) {
        return source.indexOf(o);
    }

    public static <T> List<T> limit(List<? extends T> source, int size) {
        List<T> list = new ArrayList<>();
        if (size < 1) {
            return list;
        }
        int sourceSize = source.size();
        if (sourceSize < size) {
            size = sourceSize;
        }
        for (int i = 0; i < size; ++i) {
            list.add(source.get(i));
        }
        return list;
    }

    public static <T> void add(List<? super T> source, T o) {
        source.add(o);
    }

    public static <T> void removeAll(List<? super T> removeFrom, List<? extends T> c2) {
        removeFrom.removeAll(c2);
    }

    //true если первый лист содержит все элементы второго
    public static <T> boolean containsAll(List<? super T> c1, List<? extends T> c2) {
        return c1.containsAll(c2);
    }

    //true если первый лист содержит хотя-бы 1 второго
    public static <T> boolean containsAny(List<? super T> c1, List<? extends T> c2) {
        return c2.stream().anyMatch((e) -> (c1.contains(e)));
    }

    //Возвращает лист, содержащий элементы из входного листа в диапазоне от min до max. 
    // Элементы сравнивать через Comparable.
    // Прмер range(Arrays.asList(8,1,3,5,6,4), 3, 6) вернет {3,4,5,6}
    public static <T extends Comparable<T>> List<T> range(List<? extends T> list, T min, T max) {
        List<T> result = new ArrayList<>();
        CollectionUtils.<T>addAll(list, result);
        list.stream().filter((e) -> (e.compareTo(min) < 0 || e.compareTo(max) > 0)).forEachOrdered((e) -> {
            result.remove(e);
        });
        return result;
    }

    //Возвращает лист, содержащий элементы из входного листа в диапазоне от min до max. 
    // Элементы сравнивать через Comparator.
    // Прмер range(Arrays.asList(8,1,3,5,6,4), 3, 6) вернет {3,4,5,6}
    public static <T> List<T> range(List<? extends T> list, T min, T max, Comparator<? super T> comparator) {
        List<T> result = new ArrayList<>();
        CollectionUtils.<T>addAll(list, result);
        result.sort(comparator);
        return result.subList(result.indexOf(min), result.lastIndexOf(max) + 1);
    }

}