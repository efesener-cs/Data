package service;

import java.io.FileReader;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FileManager {
    public void readFile(String filePath){
        String[] fileLines = {"init for test"};
        try{
                InputStream input = getClass().getResourceAsStream(filePath);
                // i use them because if i package to .jar i can't read resources with classical method
                BufferedReader br = new BufferedReader(new InputStreamReader(input));
                fileLines = br.readAllAsString().split("\n");
            if (fileLines == null){
                FileReader fr = new FileReader(filePath);
                String file = fr.readAllAsString();
                fr.close();
                fileLines = file.split("\n");
                // i hope not expect read char to char and manually split and manually
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally{
            for(String line : fileLines){
            System.out.println(line);
        }
        }
       
    }
}

