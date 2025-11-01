package model.sub_models;

import model.Student;

public interface Storage{

        void add (int key,Student student);

        Student get(int key);

        void delete(int key);
    }