package util;

public class HashUtil {

    private final int seed;

    public HashUtil(int seed){
        this.seed = seed;
    }

    public int hash(int data ){
        return data%seed;
    }
}
