import cacheproxy.CacheProxy;
import service.IService;
import service.ServiceImpl2;
import service.ServiceImpl1;
import service.ServiceImpl3;

public class MainDZ9 {

    public static void main(String[] args) {

        //////////////////////////////////////
        // ДОПИСАТЬ ГЕНЕРИКИ

        IService s1 = new ServiceImpl1();
        IService s2 = new ServiceImpl2();
        IService s3 = new ServiceImpl3();

        String cacheFileName = "D://cache.ser";
        CacheProxy cacheProxy = new CacheProxy(cacheFileName);
        IService sp1 = CacheProxy.cache(s1);
        IService sp2 = CacheProxy.cache(s2);
        IService sp3 = CacheProxy.cache(s3);

        foo(sp1);
        foo(sp2);
        foo(sp3);
    }

    private static void foo(IService service){
        service.doHardWork("sds",14);
        service.doHardWork("sds",11);
        service.doHardWork("sds",14);
        service.doHardWork("sds",12);
        service.doHardWork("qwe",5);
        service.doHardWork("rr",34);
    }

}
