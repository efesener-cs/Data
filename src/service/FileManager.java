package service;

import java.io.*;

public class FileManager {
    public String[] readFile(String filePath){
        String[] fileLines = {"File can't read"};
        BufferedReader br;
        File file = new File(filePath);
        try {
            if (file.exists()) {
                br = new BufferedReader(new FileReader(filePath));
            } else {
                InputStream input = getClass().getResourceAsStream("/" + filePath);
                br = new BufferedReader(new InputStreamReader(input));
            }
            fileLines = br.readAllAsString().split("\n");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return fileLines;
    }
}

