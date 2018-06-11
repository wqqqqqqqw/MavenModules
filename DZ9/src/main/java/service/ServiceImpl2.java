package service;

import annotations.ACache;

public class ServiceImpl2 implements IService {

    @ACache(cacheType = ACache.CacheType.MEMORY, identityBy = {String.class})
    public double doHardWork(String workName, int number) {
        return number/100.;
    }

    public String toString() {
        return "This is ServiceImp 2";
    }
}
