package cacheproxy;

import com.sun.istack.internal.NotNull;
import dynamicproxy.DynamicCachingProxy;
import service.IService;

import java.lang.reflect.Proxy;

public class CacheProxy {

    private static String cacheFileName;

    public CacheProxy(String cacheFileName) {
        if (cacheFileName == null) {
            CacheProxy.cacheFileName = System.getProperty("user.dir") + "cache.ser";
        } else {
            CacheProxy.cacheFileName = cacheFileName;
        }
    }

    public static IService cache(@NotNull IService service) {
        return (IService) Proxy.newProxyInstance(IService.class.getClassLoader(),
                service.getClass().getInterfaces(),
                new DynamicCachingProxy(service, cacheFileName));
    }
}
