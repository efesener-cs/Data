package model;

import java.util.HashMap;
import util.HashUtil;

public class HashTable {

    private class Table{
        static Object table;
    }
    private final HashUtil hash;

    private static Table table;

    public HashTable(boolean Use_Advanced_Data_Type,int tableSize) {
        hash = new HashUtil(tableSize);
        if (Use_Advanced_Data_Type){
            Table.table= new HashMap<Integer, Student>();
        }
        else {
            Table.table = new Student[tableSize];
        }
    }
}
