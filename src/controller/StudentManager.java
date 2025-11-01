package controller;

import model.*;
import model.sub_models.LinearProbing;
import service.FileManager;

import java.util.ArrayList;
import java.util.List;

/*
--- KOD İÇİ YORUMLAR VE NOTLAR ---

Aşağıdaki yorumlar, kodun okunabilirliğini artırmak için 'StudentManager' sınıfının
içinden çıkarılıp bu blokta toplanmıştır.

** Genel İyileştirmeler ve Hata Düzeltmeleri: **
* getStudents() metodu, 'null' elemanlar içermeyen, tam dolu bir dizi dönecek şekilde
    yeniden yazıldı.
* 'listBy...' ile başlayan metodlardaki (listByClass, listByDepartment vb.)
    'students[i].getGano()' gibi erişimler, 'getStudents()' metodundan gelen dizide
    'null' eleman olmayacağı için artık 'NullPointerException' hatasına karşı güvendedir.
* 'searchByNo' ve 'searchByName' metodlarına, 'temp' değişkeni üzerinde işlem yapmadan
    önce 'null' kontrolleri eklendi.
* 'listByGender' metodundaki dosya yazma işlemi (fm.writeFile) döngü dışına alındı.
    (Önceki halinde her öğrenci için dosyanın üzerine yazıyordu.)
* 'showHashTable' metodunda, 'bucket' (taşma) alanı taranırken 'table.storage.get(i)'
    olan bariz mantık hatası, 'table.bucket.storage.get(i)' olarak düzeltildi.
* 'getStudent' metoduna 'null' kontrolü eklendi.

** Metod Bazlı Notlar: **

* import ArrayList/List:
    * Dinamik liste için eklendi ('getStudents' metodunda kullanılıyor).
* addStudent:
    * 'bucket'in null olmadığından emin olmakta fayda var.
    * 'index'in hesaplanma şekli 'bucket' için sorunlu olabilir (modele bağlı).
* updateStudent / deleteStudent:
    * 'index'in sınırlar içinde olup olmadığını kontrol etmek iyi bir pratiktir.
    * Mevcut kodda 'bucket' (taşma) alanı için bir güncelleme/silme mantığı eksik.
* listStudents:
    * 'item != null' kontrolü zaten mevcuttu.
    * 'bucket' döngüsünde 'Doğru storage'dan al' (bucket.storage) sağlandı.
* sortByGanoInRange:
    * 'students' dizisi 'getStudents()' metodundan geldiği için 'null' içermez,
        'students[key[start + i]]' erişimi güvendedir.
* searchByNo:
    * Linear probing (doğrusal sondalama) mantığı, orijinal koddaki gibi basit
        tutuldu (döngü sonunu veya başa dönmeyi tam olarak ele almıyor olabilir).
    * 'else' bloğundaki mantık (hem 'index'ten 'size'a hem de bucket'a bakmak)
        biraz kafa karıştırıcı olabilir, ancak 'null' kontrolleri eklendi.
    * 'bucket' alanı taranırken tüm 'bucket'a bakılması sağlandı.
* searchByName:
    * 'studentName' için 'null' veya boş isim kontrolü eklendi.
* getStudents (REFACTOR EDİLMİŞ METOD):
    * Bu metod artık 'null' elemanlar içeren seyrek (sparse) bir dizi DEĞİL,
        sadece var olan Student nesnelerini içeren dolu (dense) bir dizi döndürür.
    * Bu, 'listBy...' metodlarındaki NullPointerException hatalarını engeller.
    * Metodun adımları:
    * 1. Önce 'null' olmayan tüm öğrencileri bulmak için dinamik bir liste kullan.
    * 2. Ana depolama alanını (storage) tara.
    * 3. Varsa, taşma (bucket) alanını tara.
    * 4. Listeyi, tam olarak doğru boyutta olan bir Student dizisine dönüştür.
* sort (Metodlar arası):
    * Sıralama Metodları (Değişiklik yok).

*/
public class StudentManager {

    private final HashTable table;
    private StringBuilder sb;
    private final FileManager fm = new FileManager();

    public StudentManager(HashTable table) {
        this.table = table;
    }

    public void addStudent(Student student) {
        int index = table.solition.solve(table, table.hashUtil.hash(student.getNo()));
        if (index < table.size) {
            table.storage.add(index, student);
        } else {
            if (table.bucket != null ) {
                table.bucket.storage.add(index, student);
            }
        }
    }

    public void updateStudent(int index, Student newStudent) {
        if (index >= 0 && index < table.size) {
            table.storage.add(index, newStudent);
        }
    }

    public void deleteStudent(int index) {
        if (index >= 0 && index < table.size) {
            table.storage.delete(index);
        }
    }

    public void listStudents() {
        sb = new StringBuilder();
        for (int i = 0; i < table.size; i++) {
            Student item = table.storage.get(i);
            if (item != null) {
                String str = item.toString();
                IO.println(str);
                sb.append(str).append("\n");
            }
        }
        if (table.bucket != null) {
            for (int i = 0; i < table.bucket.size; i++) {
                Student item = table.bucket.storage.get(i);
                if (item != null) {
                    String str = item.toString();
                    IO.println(str);
                    sb.append(str).append("\n");
                }
            }
        }
        String[] students = sb.toString().split("\n");
        fm.writeFile("resource/data/ogrenciler", students);
    }

    public void listByClass() {
        Student[] students = getStudents();
        int[] key = new int[students.length];
        int[] classValues = new int[students.length];
        double[] ganoValues = new double[students.length];

        for (int i = 0; i < students.length; i++) {
            key[i] = i;
            classValues[i] = students[i].getclass();
            ganoValues[i] = students[i].getGano();
        }
        sort(key, classValues);
        sortByGano(key, students, classValues);

        String[] datas = new String[students.length];
        for (int i = 0; i < students.length; i++) {
            datas[i] = students[key[i]].toString();
        }
        fm.writeFile("resource/data/sinif_sirasi", datas);
    }

    private void sortByGano(int[] key, Student[] students, int[] classValues) {
        int start = 0;
        for (int i = 1; i <= classValues.length; i++) {
            if (i == classValues.length || classValues[i] != classValues[start]) {
                sortByGanoInRange(key, students, start, i - 1);
                start = i;
            }
        }
    }

    private void sortByGanoInRange(int[] key, Student[] students, int start, int end) {
        if (start >= end) return;
        int[] subKey = new int[end - start + 1];
        double[] ganoValues = new double[end - start + 1];

        for (int i = 0; i < subKey.length; i++) {
            subKey[i] = key[start + i];
            ganoValues[i] = students[key[start + i]].getGano();
        }
        sort(subKey, ganoValues);
        System.arraycopy(subKey, 0, key, start, subKey.length);
    }

    public void listByDepartment() {
        Student[] students = getStudents();
        int[] key = new int[students.length];
        double[] value = new double[students.length];
        for (int i = 0; i < students.length; i++) {
            key[i] = i;
            value[i] = students[i].getGano();
        }
        sort(key, value);
        String[] data = new String[students.length];
        for (int i = 0; i < students.length; i++) {
            data[i] = students[key[i]].toString();
        }
        fm.writeFile("resource/data/bolum_sirasi", data);
    }

    public void listByGender() {
        Student[] students = getStudents();
        int[] key = new int[students.length];
        int[] value = new int[students.length];
        for (int i = 0; i < students.length; i++) {
            key[i] = i;
            value[i] = students[i].getGender();
        }
        sort(key, value);
        String[] data = new String[students.length];
        for (int i = 0; i < students.length; i++) {
            data[i] = students[key[i]].toString();
        }
        fm.writeFile("resource/data/cinsiyetsirasi", data);
    }

    public void listByNo() {
        Student[] students = getStudents();
        int[] key = new int[students.length];
        int[] value = new int[students.length];
        for (int i = 0; i < students.length; i++) {
            key[i] = i;
            value[i] = students[i].getNo();
        }
        sort(key, value);
        String[] data = new String[students.length];
        for (int i = 0; i < students.length; i++) {
            data[i] = students[key[i]].toString();
        }
        fm.writeFile("resource/data/ogrenci_no_sirali", data);
    }

    public void searchByNo(int No) {
        int index = table.hashUtil.hash(No);
        if (table.solition instanceof LinearProbing) {
            for (int i = index; i < table.size; i++) {
                Student temp = table.storage.get(i);
                if (temp != null && temp.getNo() == No) {
                    IO.println(temp.toString()+" index: "+i);
                    return;
                }
            }
        } else {
            for (int i = index; i < table.size; i++) {
                Student temp = table.storage.get(i);
                if (temp != null && temp.getNo() == No) {
                    IO.println(temp.toString()+" index: "+i);
                    return;
                }
            }
            if (table.bucket != null) {
                for (int i = 0; i < table.bucket.size; i++) {
                    Student temp = table.bucket.storage.get(i);
                    if (temp != null && temp.getNo() == No) {
                        IO.println(temp.toString()+" index: "+i);
                        return;
                    }
                }
            }
        }
    }

    public void searchByName(String studentName) {
        if (studentName == null || studentName.isEmpty()) {
            return;
        }

        if (table.solition instanceof LinearProbing) {
            for (int i = 0; i < table.size; i++) {
                Student temp = table.storage.get(i);
                if (temp != null && temp.getName().equals(studentName)) {
                    IO.println(temp.toString()+" index: "+i);
                    return;
                }
            }
        } else {
            for (int i = 0; i < table.size; i++) {
                Student temp = table.storage.get(i);
                if (temp != null && temp.getName().equals(studentName)) {
                    IO.println(temp.toString()+" index: "+i);
                    return;
                }
            }
            if (table.bucket != null) {
                for (int i = 0; i < table.bucket.size; i++) {
                    Student temp = table.bucket.storage.get(i);
                    if (temp != null && temp.getName().equals(studentName)) {
                        IO.println(temp.toString()+" index: "+i);
                        return;
                    }
                }
            }
        }
    }

    private String[] showHashTable() {
        sb = new StringBuilder();
        String text = "index name surname no gano department classrank class gender";
        IO.println(text);
        sb.append(text).append("\n");
        for (int i = 0; i < table.size; i++) {
            Object item = table.storage.get(i);
            if (item != null) {
                String content = i + ' ' + item.toString().replace(',', ' ');
                IO.println(content);
                sb.append(content).append("\n");
            } else {
                IO.println(i);
                sb.append(i).append("\n");
            }
        }
        if (table.bucket != null) {
            for (int i = 0; i < table.bucket.size; i++) {
                Object item = table.bucket.storage.get(i);
                if (item != null) {
                    String content = i + ' ' + item.toString().replace(',', ' ');
                    IO.println(content);
                    sb.append(content).append("\n");
                }
            }
        }
        return sb.toString().split("\n");
    }

    private Student[] getStudents() {
        List<Student> studentList = new ArrayList<>();

        for (int i = 0; i < table.size; i++) {
            Student data = table.storage.get(i);
            if (data != null) {
                studentList.add(data);
            }
        }

        if (table.bucket != null) {
            for (int i = 0; i < table.bucket.size; i++) {
                Student data = table.bucket.storage.get(i);
                if (data != null) {
                    studentList.add(data);
                }
            }
        }

        return studentList.toArray(new Student[0]);
    }

    private void sort(int[] key, int[] value) {
        for (int i = 0; i < value.length - 1; i++) {
            for (int j = 0; j < value.length - i - 1; j++) {
                if (value[j] > value[j + 1]) {
                    int tempValue = value[j];
                    value[j] = value[j + 1];
                    value[j + 1] = tempValue;
                    int tempKey = key[j];
                    key[j] = key[j + 1];
                    key[j + 1] = tempKey;
                }
            }
        }
    }

    private void sort(int[] key, double[] value) {
        for (int i = 0; i < value.length - 1; i++) {
            for (int j = 0; j < value.length - i - 1; j++) {
                if (value[j] > value[j + 1]) {
                    double tempValue = value[j];
                    value[j] = value[j + 1];
                    value[j + 1] = tempValue;
                    int tempKey = key[j];
                    key[j] = key[j + 1];
                    key[j + 1] = tempKey;
                }
            }
        }
    }

    public String getStudent(int index) {
        Student student = table.storage.get(index);
        return (student != null) ? student.toString() : "Ogrenci bulunamadi";
    }

    public void indexHashTable() {
        fm.writeFile("resource/data/hash_index", showHashTable());
    }
}