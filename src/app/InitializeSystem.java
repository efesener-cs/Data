package app;

import model.HashTable;
import model.Student;
import service.FileManager;
import service.PerformanceLogger;
import controller.StudentGenerator;
import controller.StudentManager;
public class InitializeSystem{

    public InitializeSystem(boolean useNewDatabase) {
        int n = 10_000;
        boolean useAdvancedDataType = false;
        boolean useBucket = false;
        if (useNewDatabase) new StudentGenerator(n);
        HashTable table = new HashTable(useAdvancedDataType, useBucket, n);
        StudentManager manager = new StudentManager(table);
        FileManager fm = new FileManager();
        String []data = fm.readFile("/resource/data/ogrenciler");
        for(int i=0; i< data.length;i++){
            String[] student = data[i].split(",");
            manager.addStudent(new Student(
                student[0],
                student[1],
                Integer.parseInt(student[2]),
                Float.parseFloat(student[3]),
                Integer.parseInt(student[4]),
                student[5].charAt(0)
            ));
        }
        Main.manager = manager;

    }
}