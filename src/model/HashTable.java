package model;

import util.HashUtil;
import model.sub_models.*;

public class HashTable{

    public final HashUtil hashUtil;
    public final Storage storage;
    public final Solition solition;

    public HashTable(boolean useAdvancedDataTypes,boolean useBucket,int size){
        storage = useAdvancedDataTypes ? new Advanced(size) : new Simple(size);
        solition = useBucket ? new LinearBucket() : new LinearProbing();
        hashUtil = new HashUtil(size);
    }
}
