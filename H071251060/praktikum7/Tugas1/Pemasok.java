package praktikum7.Tugas1;

import java.util.Random;

public class Pemasok implements Runnable {
    private final Gudang gudang;
    private final String nama;
    private final Random random = new Random();

    public Pemasok(Gudang gudang, String nama) {
        this.gudang = gudang;
        this.nama = nama;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                int jumlahBarang = random.nextInt(5) + 1;
                gudang.tambahStok(jumlahBarang, nama);
                Thread.sleep(1000 + random.nextInt(1001));
            }
        } catch (InterruptedException e) {
        }
    }
}