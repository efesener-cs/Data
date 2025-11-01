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
        sc.nextLine();
        String name = sc.nextLine();
        String surname = sc.nextLine();
        int no = sc.nextInt();
        double gano = sc.nextDouble();
        int _class = sc.nextInt();
        sc.nextLine();
        char gender = sc.nextLine().charAt(0);
        return new Student(name,surname,no,gano,_class,gender);
    }
    static void main(){

        logger.log("init", new Runnable() {
            @Override
            public void run() {
                new InitializeSystem();
                manager.listByNo();
                manager.listByDepartment();
                manager.listByGender();
                manager.listByClass();
            }
        });
        while (true){
            IO.println("""
                    1: yeni öğrenci ekle (isim,soyisim,no,gano,sınıf cinsiyet)
                    2: öğrenci güncelle (index,isim,soyisim,no,gano,sınıf,cinsiyet)
                    3: öğrenci sil(index)
                    4: öğrencileri listele
                    5: no ile ara
                    6: ad ile ara
                    7: tablo göster
                    8: öğrenci getir (index)
                    9: çıkış
                    """);
            int option = sc.nextInt();
            switch(option){
                case 1:
                logger.log("addStudent", () -> manager.addStudent(studentInput()));
                break;
                case 2:
                    logger.log("updateStudent", () -> manager.updateStudent(sc.nextInt(),studentInput()));
                break;
                case 3:
                    logger.log("deleteStudent", () -> manager.deleteStudent(sc.nextInt()));
                break;
                case 4:
                    logger.log("listStudent", () -> manager.listStudents());
                break;
                case 5:
                    logger.log("searchStudent", () -> manager.searchByNo(sc.nextInt()));
                break;
                case 6:
                    logger.log("searchStudent", () -> {
                        sc.nextLine();
                        manager.searchByName(sc.nextLine());});
                break;
                case 7:
                    logger.log("showtable", () -> manager.indexHashTable());
                break;
                case 8:
                    logger.log("get student", () -> IO.println(manager.getStudent(sc.nextInt())));
                break;
                case 9:
                manager.listStudents();
                return;
                default:
                IO.print("geçersiz işlem");
            }
        }
    }
}
