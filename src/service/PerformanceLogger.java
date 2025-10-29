package service;

import util.TimerUtil;

public class PerformanceLogger {
    private final FileManager fm = new FileManager();
    private final TimerUtil tu = new TimerUtil();
    private static final String filePath = "/resource/data/performance";
    
    public void log(String taskName,Runnable task){
        String data;
        try {
            data = taskName + " " + tu.measureTime(task);
        } catch (InterruptedException e) {
            data = taskName + " " + "an error occurred";
            e.printStackTrace();
        }
        fm.writeFile(filePath, data);
    }
}
