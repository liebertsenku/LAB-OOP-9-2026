package praktikum7.Tugas1;

public class Gudang {
    private int stok = 0;
    private final int kapasitasMaksimal;

    public Gudang(int kapasitasMaksimal) {
        this.kapasitasMaksimal = kapasitasMaksimal;
    }

    public synchronized void tambahStok(int jumlah, String namaPemasok) {
        while (stok + jumlah > kapasitasMaksimal) {
            try {
                System.out.println("[" + namaPemasok + "] Menunggu... Gudang penuh (Stok: " + stok + ").");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        stok += jumlah;
        System.out.println("[" + namaPemasok + "] Berhasil menambah " + jumlah + " barang. (Stok sekarang: " + stok + ")");
        notifyAll();
    }   

    public synchronized void ambilStok(int jumlah, String namaKurir) {
        while (stok < jumlah) {
            try {
                System.out.println("[" + namaKurir + "] Menunggu... Stok tidak cukup (Stok: " + stok + ").");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        stok -= jumlah;
        System.out.println("[" + namaKurir + "] Berhasil mengambil " + jumlah + " barang. (Stok sekarang: " + stok + ")");
        notifyAll(); 
    }

    public synchronized int getStok() {
        return this.stok;
    }

    public int getKapasitasMaksimal() {
        return this.kapasitasMaksimal;
    }
}