import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class SharedResource {
    private int stok = 0;
    private final int kapasitasMax;

    public SharedResource(int kapasitasMax) {
        this.kapasitasMax = kapasitasMax;
    }

    public synchronized int getStok() {
        return stok;
    }

    public synchronized int getKapasitasMax() {
        return kapasitasMax;
    }

    public synchronized void tambahStok(int jumlah) {
        while (stok + jumlah > kapasitasMax) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        stok += jumlah;
        System.out.println("[Pemasok] Menambahkan: " + jumlah + " | Total: " + stok);
        notifyAll();
    }

    public synchronized void ambilStok(int jumlah) {
        while (stok - jumlah < 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        stok -= jumlah;
        System.out.println("[Kurir] Mengambil: " + jumlah + " | Total: " + stok);
        notifyAll();
    }
}

class Pemasok implements Runnable {
    private final SharedResource resource;
    private final Random random = new Random();

    public Pemasok(SharedResource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(random.nextInt(1001) + 1000); 
                resource.tambahStok(random.nextInt(3) + 1); 
            }
        } catch (InterruptedException e) {
           
        }
    }
}

class Kurir implements Runnable {
    private final SharedResource resource;
    private final Random random = new Random();

    public Kurir(SharedResource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(random.nextInt(1001) + 2000); 
                resource.ambilStok(random.nextInt(3) + 1);
            }
        } catch (InterruptedException e) {
        }
    }
}

class ObserverThread extends Thread {
    private final SharedResource resource;

    public ObserverThread(SharedResource resource) {
        this.resource = resource;
        this.setDaemon(true); 
    } 

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                int stokNow = resource.getStok();
                int kapasitas = resource.getKapasitasMax();
                int persentase = (stokNow * 100) / kapasitas;
                int jumlahHash = persentase / 10;

                StringBuilder progressBar = new StringBuilder();
                for (int i = 0; i < 10; i++) {
                    progressBar.append(i < jumlahHash ? "#" : "-");
                }
                
                System.out.printf("\nStatus Gudang: [%s] %d%%\n", progressBar.toString(), persentase);
                Thread.sleep(1000); 
            }
        } catch (InterruptedException e) {
            System.out.println("Observer berhenti.");
        }
    }
}

public class TP7_1_H07125081 {
    public static void main(String[] args) {
        SharedResource gudang = new SharedResource(10);
        
        ExecutorService pemasokPool = Executors.newFixedThreadPool(2);
        ExecutorService kurirPool = Executors.newFixedThreadPool(3);
        
        ObserverThread observer = new ObserverThread(gudang);
        observer.start();

        for (int i = 0; i < 2; i++) pemasokPool.execute(new Pemasok(gudang));
        for (int i = 0; i < 3; i++) kurirPool.execute(new Kurir(gudang));

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n--- Waktu habis, menghentikan sistem... ---");
        pemasokPool.shutdownNow();
        kurirPool.shutdownNow();
        observer.interrupt();

        try {
            if (!pemasokPool.awaitTermination(5, TimeUnit.SECONDS) || 
                !kurirPool.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("Memaksa penghentian thread...");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Sistem logistik selesai.");
    }
}