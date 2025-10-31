package util;

public class TimerUtil {
    public long measureTime(Runnable task) throws InterruptedException{
        Thread thread = new Thread(task);
        long start = System.currentTimeMillis();
        thread.start();
        thread.join();
        long end = System.currentTimeMillis();
        return end-start;
    }
}
