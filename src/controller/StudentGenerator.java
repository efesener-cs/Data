package controller;

import model.*;

import java.util.Random;

import service.FileManager;

public class StudentGenerator {

    public StudentGenerator(){
    
    }

    private int quantity;
    private static final String PATH = "/resource/data/ogrenciler";
    private static final String CHARS =
    "ABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVYZabcçdefgğhıijklmnoöprsştuüvyz";
    private static final Random random = new Random();
    private static Student[] students;


    private String stringGenerator(){
        int length = random.nextInt(3,12);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0;i<length;i++){
            stringBuilder.append(CHARS.charAt(random.nextInt(0,CHARS.length())));
        }
        return stringBuilder.toString();
    }

    private void generate(int quantity){
        students = new Student[quantity];
        for (int i=0;i<quantity;i++) {
            students[i] = new Student(
            stringGenerator(),
            stringGenerator(),
            random.nextInt(100_000_000,999_999_999),
            random.nextFloat(4.0f),
            random.nextInt(1,30),
            random.nextInt(1,60),
            random.nextInt(5),
            random.nextInt(2)==0 ? 'M' : 'F'
            );
        }
    }

    private Student[] get(){
        return students;
    }

    private void write(){
        String[] data = new String[quantity];
        for (int i=0;i<quantity;i++){
            data[i] = students[i].toString();
        }
        new FileManager().writeFile(PATH,data);
    }
}
