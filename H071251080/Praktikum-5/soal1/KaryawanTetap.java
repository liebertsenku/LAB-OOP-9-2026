package soal1;
public class KaryawanTetap extends Karyawan {
    private double gajiPokok;
    private double tunjanganMakan;

    public KaryawanTetap(String nama, String idKaryawan, double gajiPokok, double tunjanganMakan){
        super(nama, idKaryawan);
        this.gajiPokok = gajiPokok;
        this.tunjanganMakan = tunjanganMakan; 
    }
    @Override
    public double hitungGaji(){
        return gajiPokok + (tunjanganMakan + getJumlahKehadiran());
    }

    public double hitungGaji(double bonusKinerja){
        return hitungGaji() + bonusKinerja;
    }

}