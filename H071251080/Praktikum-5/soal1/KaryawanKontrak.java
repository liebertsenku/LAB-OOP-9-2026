package soal1;
public class KaryawanKontrak extends Karyawan {
    double upahPerHari;

    public KaryawanKontrak(String nama, String idKaryawan, double upahPerHari){
        super(nama, idKaryawan);
        this.upahPerHari = upahPerHari;
    }
    @Override
    public double hitungGaji(){
        double total = upahPerHari * getJumlahKehadiran();

        if(getJumlahKehadiran() > 20){
            total += 500000;
        }
        return total;
    }

    
}