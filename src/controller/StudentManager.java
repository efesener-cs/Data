package controller;

import model.*;
import model.sub_models.LinearProbing;

public class StudentManager {

    private final HashTable table;

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
    public void listStudents(){
        for (int i=0; i<table.size;i++){
            Student item = table.storage.get(i);
            if (item != null){
                IO.println(item.toString());
            }
        }
        if(table.bucket != null){
            for (int i=0; i<table.bucket.size;i++){
                Student item = table.bucket.storage.get(i);
                if (item != null){
                    IO.println(item.toString());
                }
            }
        }
    }
    
    public void listByClass(){

    }
    public void listByDepartment(){

    }
    public void listByGender(){

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
    public void showHashTable(){
        IO.println("index name surname no gano department classrank class gender");
        for (int i=0; i<table.size;i++){
            IO.println(i+' '+table.storage.get(i).toString().replace(',', ' '));
        }
        if (table.bucket!=null){
            for(int i=0; i<table.bucket.size;i++){
                IO.println(i+' '+table.bucket.storage.get(i).toString().replace(' ', ' '));
            }
        }
    }
}
