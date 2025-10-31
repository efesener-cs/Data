package controller;

import model.*;
import model.sub_models.LinearProbing;
import service.FileManager;

public class StudentManager {

    private final HashTable table;
    private StringBuilder sb;
    private final FileManager fm = new FileManager();

    public StudentManager(HashTable table){
        this.table = table;
    }

    public void addStudent(Student student){
        int index = table.solition.solve(table,table.hashUtil.hash(student.getNo()));
        if (index < table.size){
            table.storage.add( index,student);
        }
        else {
            table.bucket.storage.add(index, student);
        }
    }
    public void updateStudent(int index,Student newStudent){
        table.storage.add(index, newStudent);
    }
    public void deleteStudent(int index){
        table.storage.delete(index);
    }
    public String[] listStudents(){
        sb = new StringBuilder();
        for (int i=0; i<table.size;i++){
            Student item = table.storage.get(i);
            if (item != null){
                String str = item.toString();
                IO.println(str);
                sb.append(str).append("\n");
            }
        }
        if(table.bucket != null){
            for (int i=0; i<table.bucket.size;i++){
                Student item = table.bucket.storage.get(i);
                if (item != null){
                    String str = item.toString();
                    IO.println(str);
                    sb.append(str).append("\n");
                }
            }
        }
        String[] students = sb.toString().split("\n");
        fm.writeFile("/resource/data/ogrenciler",students);
        return students;
    }
    
    public void listByClass(){
    Student[] students = getStudents();
    int[] key = new int[students.length];
    int[] classValues = new int[students.length];
    double[] ganoValues = new double[students.length];
    
    for (int i = 0; i < students.length; i++){
        key[i] = i;
        classValues[i] = students[i].getclass();
        ganoValues[i] = students[i].getGano();
    }
    sort(key, classValues);
    sortByGanoWithinClass(key, students, classValues);
    
    String[] datas = new String[students.length];
    for(int i = 0; i < students.length; i++){
        datas[i] = students[key[i]].toString();
    }
    fm.writeFile("/resource/data/sinif_sirasi", datas);
}

private void sortByGanoWithinClass(int[] key, Student[] students, int[] classValues){
    int start = 0;
    
    for (int i = 1; i <= classValues.length; i++){
        // Sınıf değiştiğinde veya son elemana gelindiğinde o sınıfı sırala
        if (i == classValues.length || classValues[i] != classValues[start]){
            sortByGanoInRange(key, students, start, i - 1);
            start = i;
        }
    }
}
private void sortByGanoInRange(int[] key, Student[] students, int start, int end){
    if (start >= end) return;
    int[] subKey = new int[end - start + 1];
    double[] ganoValues = new double[end - start + 1];
    
    for (int i = 0; i < subKey.length; i++){
        subKey[i] = key[start + i];
        ganoValues[i] = students[key[start + i]].getGano();
    }
    sort(subKey, ganoValues);
    for (int i = 0; i < subKey.length; i++){
        key[start + i] = subKey[i];
    }
}
    public void listByDepartment(){
        Student[] students = getStudents();
        int[] key = new int[students.length];
        double[] value = new double[students.length];
        for (int i=0;i<students.length;i++){
            key[i] = i;
            value[i] = students[i].getGano();
        }
        sort(key, value);
        String[] data = new String[students.length];
        for(int i=0;i<students.length;i++){
            data[i] = students[key[i]].toString();
        }
        fm.writeFile("/resource/data/bolum_sirasi", data);
    }
    public void listByGender(){
          Student[] students = getStudents();
        int[] key = new int[students.length];
        int[] value = new int[students.length];
        for (int i=0;i<students.length;i++){
            key[i] = i;
            value[i] = students[i].getGender();
        }
        sort(key, value);
        String[] data = new String[students.length];
        for(int i=0;i<students.length;i++){
            data[i] = students[key[i]].toString();
            IO.print(data[i]);
        }
    }
    public void listByNo(){
          Student[] students = getStudents();
        int[] key = new int[students.length];
        int[] value = new int[students.length];
        for (int i=0;i<students.length;i++){
            key[i] = i;
            value[i] = students[i].getNo();
        }
        sort(key, value);
        String[] data = new String[students.length];
        for(int i=0;i<students.length;i++){
            data[i] = students[key[i]].toString();
        }
        fm.writeFile("/resource/data/ogrenci_no_sirali", data);
    }

    public void searchByNo(int No){
        int index = table.hashUtil.hash(No);
        if (table.solition instanceof LinearProbing){
            for(int i=index;i<table.size;i++){
                Student temp = table.storage.get(i);
                int thisNo = temp.getNo();
                if (thisNo == No) {
                    IO.println(temp.toString());
                    return;
                }
            }
        }
        else{
            for(int i=index;i<table.size;i++){
                Student temp = table.storage.get(i);
                int thisNo = temp.getNo();
                if (thisNo == No) {
                    IO.println(temp.toString());
                    return;
                }
            }
            for(int i=index;i<table.bucket.size;i++){
                Student temp = table.bucket.storage.get(i);
                int thisNo = temp.getNo();
                if (thisNo == No) {
                    IO.println(temp.toString());
                    return;
                }
            }
        }
    }
    public void searchByName(String studentName){
        
        if (table.solition instanceof LinearProbing){
            for(int i=0;i<table.size;i++){
                Student temp = table.storage.get(i);
                String thisName = temp.getName();
                if (thisName.equals(studentName)) {
                    IO.println(temp.toString());
                    return;
                }
            }
        }
        else{
            for(int i=0;i<table.size;i++){
                Student temp = table.storage.get(i);
                String thisName = temp.getName();
                if (thisName.equals(studentName)) {
                    IO.println(temp.toString());
                    return;
                }
            }
            for(int i=0;i<table.bucket.size;i++){
                Student temp = table.bucket.storage.get(i);
                String thisName = temp.getName();
                if (thisName.equals(studentName)) {
                    IO.println(temp.toString());
                    return;
                }
            }
        }
        
    }

    private String[] showHashTable(){
        sb = new StringBuilder();
        String text = "index name surname no gano department classrank class gender";
        IO.println(text);
        sb.append(text).append("\n");
        for (int i=0; i<table.size;i++){
            Object item = table.storage.get(i);
            if (item != null){
                String content = i+' '+item.toString().replace(',', ' ');
                IO.println(content);
                sb.append(content).append("\n");
            }
            else {
                IO.println(i);
                sb.append(i).append("\n");
            }
        }
        if (table.bucket!=null){
            for(int i=0; i<table.bucket.size;i++){
                Object item = table.storage.get(i);
                if (item != null){
                    String content = i+' '+table.bucket.storage.get(i).toString().replace(' ', ' ');
                    IO.println(content);
                    sb.append(content).append("\n");
                }
            }
        }
        return sb.toString().split("\n");
    }

    private Student[] getStudents(){
        Student[] students = new Student[table.size];
        int count = 0;
        for (int i=0;i<table.size;i++){
            Student data = table.storage.get(i);
            if (data != null){
                students[count] = data;
                count++;
            }
        }
        if (table.bucket != null){
            for (int i=0;i<table.bucket.size;i++){
                Student data = table.bucket.storage.get(i);
                if (data != null){
                    students[count] = data;
                    count++;
                }
            }
        }
        return students;
    }

    private void sort(int[] key, int[] value){
        for (int i = 0; i < value.length - 1; i++) {
        for (int j = 0; j < value.length - i - 1; j++) {
            if (value[j] > value[j + 1]) {
                int tempValue = value[j];
                value[j] = value[j + 1];
                value[j + 1] = tempValue;
                int tempKey = key[j];
                key[j] = key[j + 1];
                key[j + 1] = tempKey;
            }
        }
    }
        
    }
    private void sort(int[] key, double[] value){
        for (int i = 0; i < value.length - 1; i++) {
        for (int j = 0; j < value.length - i - 1; j++) {
            if (value[j] > value[j + 1]) {
                double tempValue = value[j];
                value[j] = value[j + 1];
                value[j + 1] = tempValue;
                int tempKey = key[j];
                key[j] = key[j + 1];
                key[j + 1] = tempKey;
            }
        }
    }
    }

    public void indexHashTable(){
        fm.writeFile("/resource/data/hash_index", showHashTable());
    }
}