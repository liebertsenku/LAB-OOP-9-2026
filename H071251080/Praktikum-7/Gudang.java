import java.util.Random;

public class Gudang {
    int stok;
    int kapasitasMaksimal;
    public Gudang(int stok, int kapasitasMaksimal) {
        this.stok = stok;
        this.kapasitasMaksimal = kapasitasMaksimal;
    }

    public synchronized void tambahStok(int jumlah) throws InterruptedException {
        while (stok + jumlah > kapasitasMaksimal) {
            System.out.println("Stok penuh. Pemasok harus menunggu.");
            wait();
        }
        stok += jumlah;
        notifyAll();
        System.out.print("\nPemasok menambah: +" + jumlah + " stok.");
        System.out.print("\nStok sekarang: " + stok);
    }

    public synchronized void ambilStok(int jumlah) throws InterruptedException {
        while (stok < jumlah) {
            System.out.println("\nStok kosong. Kurir harus menunggu.");
            wait();
        }
        stok -= jumlah;
        notifyAll();
        System.out.print("\nKurir mengambil: -" + jumlah + " stok.");
        System.out.print("\nStok sekarang: " + stok);
    }

    public synchronized int getStok() {
        return stok;
    }

    public synchronized int getKapasitasMaksimal() {
        return kapasitasMaksimal;
    }
}

class Pemasok implements Runnable {
    private Gudang gudang;
    private Random barang = new Random();

    public Pemasok(Gudang gudang) {
        this.gudang = gudang;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                int jumlah = barang.nextInt(7) +1;
                gudang.tambahStok(jumlah);
                Thread.sleep(1000 + barang.nextInt(1001));
            }
        } catch (InterruptedException e) {

        }
    }
}

class Kurir implements Runnable {
    private Gudang gudang;
    private Random barang = new Random();

    public Kurir(Gudang gudang) {
        this.gudang = gudang;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                int jumlah = barang.nextInt(7) +1;
                gudang.ambilStok(jumlah);
                Thread.sleep(2000 + barang.nextInt(1001));
            }
        } catch (InterruptedException e) {

        }
    }
}

class Monitoring implements Runnable {
    private Gudang gudang;

    public Monitoring(Gudang gudang) {
        this.gudang = gudang;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                tampilkanVisual(gudang.getStok(), gudang.getKapasitasMaksimal());
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {

        }
    }

    public void tampilkanVisual(int stok, int kapasitasMaksimal) {
        int persen = (stok * 100) / kapasitasMaksimal;
        int jumlahHash = persen / 10;

        System.out.print("\nStatus Gudang: [");
        for (int i = 0; i < 10; i++) {
            if (i < jumlahHash) {
            System.out.print("#");
            } else {
                System.out.print("-");
            }
        }
        System.out.println("] " + persen + "%");
    }
}