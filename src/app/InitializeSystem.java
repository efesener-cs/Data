package app;

import model.HashTable;
import model.Student;
import service.FileManager;
import controller.StudentGenerator;
import controller.StudentManager;
public class InitializeSystem{

    public InitializeSystem() {
        int n = 10_000;
        boolean useAdvancedDataType = false;
        boolean useBucket = false;
        boolean useNewDatabase = true;
        if (useNewDatabase) new StudentGenerator(n-100);
        HashTable table = new HashTable(useAdvancedDataType, useBucket, n);
        StudentManager manager = new StudentManager(table);
        FileManager fm = new FileManager();
        String[] data = fm.readFile("resource/data/ogrenciler");
        for(int i=0; i< data.length;i++){
            String[] student = data[i].split(",");
            manager.addStudent(new Student(
                student[0].split(":")[1],
                student[1].split(":")[1],
                Integer.parseInt(student[2].split(":")[1]),
                Double.parseDouble(student[3].split(":")[1]),
                Integer.parseInt(student[4].split(":")[1]),
                student[5].split(":")[1].charAt(0)
            ));
        }
        Main.manager = manager;

    }
}