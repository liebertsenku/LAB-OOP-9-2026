package soal1;
public class Main {
    public static void main(String[] args) {
        // === Karyawan Tetap ===
        KaryawanTetap kt = new KaryawanTetap("Andi", "KT-001", 5_000_000, 25_000);
        for (int i = 0; i < 22; i++) kt.absen();

        System.out.println("=== Karyawan Tetap ===");
        System.out.println("Nama        : " + kt.getNama());
        System.out.println("ID          : " + kt.getIdKaryawan());
        System.out.println("Kehadiran   : " + kt.getJumlahKehadiran() + " hari");
        System.out.printf("Gaji Pokok  : Rp%.0f%n", kt.hitungGaji());
        System.out.printf("Gaji + Bonus: Rp%.0f%n", kt.hitungGaji(1_500_000));

        System.out.println();

        // === Karyawan Kontrak ===
        KaryawanKontrak kk = new KaryawanKontrak("Budi", "KK-002", 150_000);
        for (int i = 0; i < 23; i++) kk.absen();

        System.out.println("=== Karyawan Kontrak ===");
        System.out.println("Nama        : " + kk.getNama());
        System.out.println("ID          : " + kk.getIdKaryawan());
        System.out.println("Kehadiran   : " + kk.getJumlahKehadiran() + " hari");
        System.out.printf("Total Gaji  : Rp%.0f%n", kk.hitungGaji());
    }
}