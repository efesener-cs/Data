package model.sub_models;

import model.Student;

public interface Storage{

        void add (int key,Student student);

        Student get(int key);

        void update(int key,Student student);

        void delete(int key);
    }