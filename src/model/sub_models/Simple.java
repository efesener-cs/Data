package model.sub_models;

import model.Student;

public class Simple implements Storage{
    
    private final Student[] students;
    
    public Simple(int size){
        students = new Student[size];
    }
    @Override
    public void add(int key,Student student){
        students[key] = student;
    }
    @Override
    public Student get(int key){
        return students[key];
    }

    @Override
    public void delete(int key){
        students[key] = null;
    }
}