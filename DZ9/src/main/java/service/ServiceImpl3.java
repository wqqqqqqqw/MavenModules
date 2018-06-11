package service;

public class ServiceImpl3 implements IService {

    public double doHardWork(String workName, int number) {
        return number / 1000.;
    }

    public String toString() {
        return "This is ServiceImp 3";
    }
}

