package service;

import annotations.ACache;

public class ServiceImpl1 implements IService {

    @ACache(cacheType = ACache.CacheType.FILE)
    //@ACache(cacheType = ACache.CacheType.FILE, identityBy = {String.class})
    //@ACache(cacheType = ACache.CacheType.FILE, identityBy = {String.class, int.class})
    public double doHardWork(String workName, int number) {
        return number/1000.;
    }

    public String toString(){
        return "This is ServiceImp l";
    }
}
