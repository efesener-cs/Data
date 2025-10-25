package model.sub_models;

abstract class Storage{
    void add(String data);
    void update(int key);
    Void delete (int key);
    void get(int key);
    }
