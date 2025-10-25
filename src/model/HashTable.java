package model;

import util.HashUtil;

public class HashTable{

    private final HashUtil hu;
    private final int size;
    public HashTable(boolean useAdvancedDataTypes,int collisionMethod,int size){
        this.size = size;
        hu = new HashUtil(size);
    }
}
