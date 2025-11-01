package model.sub_models;
import java.util.HashMap;
import java.util.Map;

import model.Student;
public class Advanced implements Storage{
    private final Map<Integer,Student> students;
    public Advanced(int size){
        students = new HashMap<>(size);
    }
    @Override
    public void add(int key,Student student){
        students.put(key, student);
    }
    @Override
    public Student get(int key){
        return students.get(key);
    }

    @Override
    public void delete(int key){
        students.remove(key);
    }
        
}