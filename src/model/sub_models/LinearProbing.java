package model.sub_models;

import model.HashTable;

public class LinearProbing implements Solition {
    @Override
    public int solve(HashTable table,int index){
        while (table.storage.get(index)!= null){
            index = (index+1) % table.size;
        }
        return index;
    }
}