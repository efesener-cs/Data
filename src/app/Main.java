package app;

import service.FileManager;

public class Main {
    static void main() {
        FileManager fm = new FileManager();
        fm.readFile("resource/data/for_devs");
    }
}
