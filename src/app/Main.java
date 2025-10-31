package app;
import java.util.Scanner;

import controller.StudentManager;
import service.PerformanceLogger;
import model.Student;

public class Main {
    static StudentManager manager;
    static PerformanceLogger logger = new PerformanceLogger();
    private static final Scanner sc = new Scanner(System.in);

    private static Student studentInput(){
        return new Student(
            sc.nextLine(),
            sc.nextLine(),
            sc.nextInt(),
            sc.nextDouble(),
            sc.nextInt(),
            sc.nextLine().charAt(0)
        );
    }
    public static void main(){
        new InitializeSystem();
        
        while (true){
            IO.println("""
                    1: yeni öğrenci ekle (isim,soyisim,no,gano,sınıf cinsiye)
                    2: öğrenci güncelle (index,isim,soyisim,no,gano,sınıf,cinsiyet)
                    3: öğrenci sil(index)
                    4: öğrencileri listele
                    5: no ile ara
                    6: ad ile ara
                    7: tablo göster
                    8: çıkış
                    """);
            int option = sc.nextInt();
            switch(option){
                case 1:
                logger.log("addStudent",new Runnable() {
            @Override
            public void run() {
                manager.addStudent(studentInput());
            }
        });
                break;
                case 2:
                    logger.log("updateStudent",new Runnable() {
            @Override
            public void run() {
                manager.updateStudent(sc.nextInt(),studentInput());
            }
        });
                break;
                case 3:
                    logger.log("deleteStudent",new Runnable() {
            @Override
            public void run() {
                manager.deleteStudent(sc.nextInt());
            }
        });
                break;
                case 4:
                    logger.log("listStudent",new Runnable() {
            @Override
            public void run() {
                manager.listStudents();
            }
        });
                break;
                case 5:
                    logger.log("searchStudent",new Runnable() {
            @Override
            public void run() {
                manager.searchByNo(sc.nextInt());
            }
        });
                break;
                case 6:
                    logger.log("searchStudent",new Runnable() {
            @Override
            public void run() {
                manager.searchByName(sc.nextLine());
            }
        });
                break;
                case 7:
                    logger.log("showtable",new Runnable() {
            @Override
            public void run() {
                manager.indexHashTable();
            }
        });
                break;
                case 8:
                return;
                default:
                IO.print("geçersiz işlem");
            }
        }
    }
}
