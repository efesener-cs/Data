package app;

import model.HashTable;
import service.PerformanceLogger;
import controller.StudentGenerator;
import controller.StudentManager;
public class InitializeSystem{

    public InitializeSystem() {
        int n = 10_000;
        boolean useAdvancedDataType = false;
        boolean useBucket = false;
        new StudentGenerator(n);
        Main.manager = new StudentManager(new HashTable(useAdvancedDataType, useBucket, n));
    }
}