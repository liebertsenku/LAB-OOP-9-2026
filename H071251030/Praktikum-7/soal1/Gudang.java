import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Gudang {
    int stok, kapasitasMaks;

    Gudang(int kapasitasMaks) {
        this.stok = 0;
        this.kapasitasMaks = kapasitasMaks;
    }

    public synchronized void tambahStok(int jumlah) {
        while (this.stok + jumlah > kapasitasMaks) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.stok += jumlah;
        notifyAll();
    }

    public synchronized void ambilStok(int jumlah) {
        while (this.stok - jumlah < 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.stok -= jumlah;
        notifyAll();
    }

    public synchronized int getStok() {
        return stok;
    }
}

class Pemasok implements Runnable {
    Gudang g;

    Pemasok(Gudang g) {
        this.g = g;
    }

    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                int jumlah = 1 + (int) (Math.random() * 10);
                g.tambahStok(jumlah);
                Thread.sleep(1000 + (int) (Math.random() * 1000));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Kurir implements Runnable {
    Gudang g;

    Kurir(Gudang g) {
        this.g = g;
    }

    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                int jumlah = 1 + (int) (Math.random() * 10);
                g.ambilStok(jumlah);
                Thread.sleep(2000 + (int) (Math.random() * 1000));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Monitoring extends Thread {
    Gudang g;

    Monitoring(Gudang g) {
        this.g = g;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("Status Kapasitas Gudang: ");
                System.out.printf("%.2f%%\n", ((double) g.getStok() / g.kapasitasMaks) * 100);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}

class Main {
    public static void main(String[] args) {
        Gudang g = new Gudang(50);
        ExecutorService pemasokPool = Executors.newFixedThreadPool(2);
        ExecutorService kurirPool = Executors.newFixedThreadPool(3);

        pemasokPool.submit(new Pemasok(g));
        pemasokPool.submit(new Pemasok(g));
        kurirPool.submit(new Kurir(g));
        kurirPool.submit(new Kurir(g));
        kurirPool.submit(new Kurir(g));

        Thread monitoring = new Monitoring(g);
        monitoring.start();

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        pemasokPool.shutdownNow();
        kurirPool.shutdownNow();
        monitoring.interrupt();

        try {
            pemasokPool.awaitTermination(5, TimeUnit.SECONDS);
            kurirPool.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }

        System.out.println("==============================");
        System.out.print("Status Akhir Kapasitas Gudang: ");
        System.out.println(((double) g.getStok() / g.kapasitasMaks) * 100 + "%");
        System.out.println("Stok akhir: " + g.getStok() + " barang");
    }
}