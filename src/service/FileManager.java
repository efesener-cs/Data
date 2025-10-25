package service;

import java.io.*;

public class FileManager {
    
    public void createFile(String filePath){
        File file = new File(filePath);
        if (!file.exists()){
            try{
                file.createNewFile();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        else{
            deleteFile(filePath);
            createFile(filePath);
        }
    }

    public void createFile(String filePath,String[] datas){
        File file = new File(filePath);
        if (!file.exists()){
            try{
                file.createNewFile();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            writeFile(filePath, datas);
        }
        else {
            deleteFile(filePath);
            writeFile(filePath, datas);
        }
    }

    public void deleteFile(String filePath){
        File file = new File(filePath);
        if(!file.exists()){
            IO.println("error file can't delete may be doesn't exist");
        }
        file.delete();
    }

    public String[] readFile(String filePath){
        String[] fileLines;
        File file = new File(filePath);
        try {
            FileReader fr = new FileReader(file);
            fileLines = fr.readAllAsString().split("\n");
        }
        catch (IOException e){
            e.printStackTrace();
            fileLines = null;
        }
        return fileLines;
    }
    public void writeFile(String filePath,String[] datas){
        File file = new File(filePath);
        if (!file.canWrite()){
            createFile(filePath);
        }
        try{
            FileWriter fw = new FileWriter(file);
            for (String data : datas){
                fw.append(data+"\n");
            }
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}