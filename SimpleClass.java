package DenemeAgent;

public class SimpleClass {

    public static String baslangicMetodu(String Degisken) {
        return "baslangic metodunun ilk degeri :  " + Degisken + 
                " ve devam edecek metoddaki deger : " + devamEtmekteOlanMetod();
    }

    public static String devamEtmekteOlanMetod() {
        return "Devam etmekte olan degerler.";
    }

    public static String bitisMetodu() {
        parametreliMetod01(7,77);
        parametreliMetod02("birinci cumle","kelime");
        baslangicMetodu("myo");
        return "Bitis degeri.";
    }
    
    public static String bostaBulunanMetod() {
        return "Calismiyor.";
    }
    
    public static String parametreliMetod01(int sayi1, int sayi2) {
        return "Sonuc : " + (sayi1+sayi2);
    }
    
    public static String parametreliMetod02(String isim, String soyisim) {
        return "Yazdir : " + (isim + " " + soyisim);
    }
}
