package model;

import util.HashUtil;
import model.sub_models.*;

public class HashTable{

    public final HashUtil hashUtil;
    public final Storage storage;
    public final Solition solition;
    public final int size;
    public final HashTable bucket;

    public HashTable(boolean useAdvancedDataTypes,boolean useBucket,int size){
        this.size = size;
        storage = useAdvancedDataTypes ? new Advanced(size) : new Simple(size);
        solition = useBucket ? new LinearBucket() : new LinearProbing();
        hashUtil = new HashUtil(size);
        if (useBucket){
            bucket = new HashTable(useAdvancedDataTypes, false, (int)(size*0.3));
        }
        else{
            bucket=null;
        }
    }
}
