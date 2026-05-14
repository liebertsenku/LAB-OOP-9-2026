package praktikum7.Tugas2;

public class HasilProses {
    private final int jumlahKata;
    private final String namaThread;
    private final long durasi;

    public HasilProses(int jumlahKata, String namaThread, long durasi) {
        this.jumlahKata = jumlahKata;
        this.namaThread = namaThread;
        this.durasi = durasi;
    }

    public int getJumlahKata() {
        return jumlahKata;
    }

    public String getNamaThread() {
        return namaThread;
    }

    public long getDurasi() {
        return durasi;
    }
}
