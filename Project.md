BMÜ3311 VERİ YÖNETİMİ Dersi — 1. Ödev
Konu: Ardışık Dosya Organizasyonu ile Öğrenci Kayıt Sistemi Geliştirme
Ödev Türü: Araştırma + Uygulama (Proje)

    1. Ödevin Amacı
Bu ödevin amacı, ardışık dosya organizasyonu ve hashing yapısını kullanarak bir öğrenci kayıt sistemi geliştirmektir.
Öğrenciler, aynı işlemleri iki farklı programlama kısıtında gerçekleştireceklerdir:
    1. Use_Advanced_Data_Type = false
→ Sadece temel yapılar: dizi (array), struct, string.
→ Modern veri yapıları (vector, map, stack, hash map) yasak.
    2. Use_Advanced_Data_Type = true
→ Dile özgü gelişmiş veri yapıları kullanılabilir (C++: vector, unordered_map, list; Java: ArrayList, HashMap, Stack vb.).
Bu iki modun amacı, veri yönetimi problemlerini düşük seviyeli ve yüksek seviyeli veri yapılarıyla karşılaştırarak kavratmaktır.

2. Temel Gereksinimler
    • Diller: C, C++, C#, veya Java.
    • Dosya işlemleri ardışık erişim yöntemiyle yapılacaktır (fopen/fprintf/fscanf veya FileReader/FileWriter).
    • 10.000 kayıt üretilecek ve dosyada saklanacak.
    • Hash tablosu kullanılacak ve çakışma (collision) çözümü iki farklı şekilde uygulanacaktır:

3. Çakışma (Collision) Çözüm Yöntemleri
3.1. Yöntem 1 — Taşma Alanı Kullanmadan Linear Probing
Temel mantık:
index = hash(ogrenci_no)
if (hash_table[index] dolu)
    index = (index + 1) % tablo_boyutu // boş yer bulunana kadar
Bu yöntem, tek bir tabloda ardışık ilerlemeyi kullanır.
Silme işlemlerinde "tombstone" (ör. -1 veya boş kayıt) bırakılmalıdır, yoksa zincir kopar.

3.2. Yöntem 2 — Taşma Alanı Kullanarak Linear Bucket (Overflow Area)
Mantık:
    • Ana tablo (primary area) ve taşma (overflow area) olmak üzere iki alan vardır.
    • hash(k) sonucu ana tablodaki bir hücreye gider.
    • Eğer doluysa, yeni kayıt taşma bölgesine eklenir ve ana tabloda taşma bağlantısı tutulur.
Görsel yapı:
Ana tablo (Primary):  [0] [1] [2] [3] [4] ...
Taşma alanı (Overflow):  [10000] [10001] [10002] ...
Bu yöntemde taşma zinciri oluşturularak çakışmalar yönetilir.

4. Hash Fonksiyonu
hash(k) = k % M
    • M genellikle asal sayı (ör. 12007 veya 20011).
    • Hash tablosu boyutu ≈ 1.3 × toplam kayıt sayısı önerilir.
Hash için isim ve öğrenci numarası kullanılarak polinom veya farklı hash fonksiyonlarıda kullanılabilir.

5. Fonksiyonel Gereksinimler
Program her iki modda da aşağıdaki işlevleri sağlamalıdır:
Menü:
    1. Yeni öğrenci ekle
    2. Öğrenci bilgisi güncelle
    3. Öğrenci sil
    4. Tüm öğrencileri listele
    5. Sınıfa göre listele
    6. Bölüme göre listele
    7. Cinsiyete göre listele
    8. Öğrenci numarasına göre ara
    9. Ad ile ara
    10. Hash tablosunu göster

6. Arama İşlemleri
6.1. Öğrenci Numarasına Göre Arama (Hash ile)
    • ogr_no değeri hash fonksiyonuna girer.
    • Çakışma çözüm yöntemi aktif moda göre değişir:
        ◦ Linear probing: sıradaki hücreler kontrol edilir.
        ◦ Taşma alanı: ana tablo + bağlı overflow zinciri kontrol edilir.
6.2. Ada Göre Arama
    • Hash tablosu sadece ogrenci_no için kullanıldığı için,
ada göre arama ardışık tarama (sequential search) ile yapılır:
        ◦ fscanf veya getline ile her satır okunur.
        ◦ strcmp(ad, aranan_ad) eşleşirse öğrenci bilgisi gösterilir.
Gelişmiş modda (Use_Advanced_Data_Type = true)
ada göre arama için unordered_map<string, vector<int>> gibi yapı kullanılabilir.

7. Program Akışı
int main() {
    bool Use_Advanced_Data_Type = true; // veya false
    
    initialize_system(Use_Advanced_Data_Type);
    
    while(true) {
        show_menu();
        int secim;
        cin >> secim;
        switch(secim) {
            case 1: yeni_ogrenci_ekle(Use_Advanced_Data_Type); break;
            case 2: ogrenci_guncelle(Use_Advanced_Data_Type); break;
            case 3: ogrenci_sil(Use_Advanced_Data_Type); break;
            case 4: tum_ogrencileri_listele(); break;
            case 8: ogrenci_no_ile_ara(); break;
            case 9: ad_ile_ara(); break;
            case 10: hash_tablo_goster(); break;
            case 0: exit(0);
        }
    }
}

8. Veri Alanları (Örnek Struct)
struct Ogrenci {
    char isim[20];
    char soyad[20];
    int ogr_no;
    float gano;
    int bolum_sira;
    int sinif_sira;
    int sinif;
    char cinsiyet;
};

9. Raporlama
Program sonunda aşağıdaki dosyalar oluşturulacaktır:
Dosya Adı	Açıklama
ogrenciler.txt	Tüm kayıtlar (ardışık formatta)
sinif_sirasi.txt	Sınıf sırasına göre liste
bolum_sirasi.txt	Bölüm sırasına göre liste
ogrenci_no_sirali.txt	Öğrenci numarasına göre liste
hash_index.txt	Hash tablosu içeriği (mod’a göre farklı)
performans.txt	Listeleme süreleri (ms veya s)
Her listeleme işleminde süre ölçülerek bu dosyada saklanmalıdır.

10. Değerlendirme (Ek Kriterlerle)
Kriter	Puan
Kodun düzgünlüğü ve açıklığı	10
Hash fonksiyonu & çakışma çözümü (2 yöntemle)	20
İki modun doğru uygulanması (true/false)	20
Dosya işlemleri ve ardışık erişim doğruluğu	10
Arama (ad & numara ile) işlevleri	10
Raporlama (zaman ölçümü + listeler)	10
Savunma başarısı	20

