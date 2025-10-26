package model.sub_models;

import model.HashTable;

public class LinearBucket implements Solition{
    private int bucketIndex = -1;
    @Override
    public int solve(HashTable table,int index){
        if (table.storage.get(index)!=null){
            bucketIndex++;
            return table.size+bucketIndex;
        }
        else{
            return index;
        }
    }
}