package model;

public class HashTable {
    class TableHolder {
        static Object table;
    }
    private void readData(){

    }
    private void initTable(){

    }
    public HashTable(boolean Use_Advanced_Data_Type,int tableSize) {
        if (Use_Advanced_Data_Type){
        
        }
        else {
            TableHolder.table = new int[tableSize];
        }
    }
    
}
