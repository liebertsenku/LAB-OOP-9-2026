package praktikum7.Tugas1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainGudang {
    public static void main(String[] args) {
        Gudang gudang = new Gudang(20); 
        
        ExecutorService pemasokPool = Executors.newFixedThreadPool(2);
        ExecutorService kurirPool = Executors.newFixedThreadPool(3);

        Thread monitoringThread = new Thread(new Monitoring(gudang));

        System.out.println("=== SISTEM LOGISTIK GUDANG DIMULAI ===");

        for (int i = 1; i <= 2; i++) {
            pemasokPool.execute(new Pemasok(gudang, "Pemasok-" + i));
        }

        for (int i = 1; i <= 3; i++) {
            kurirPool.execute(new Kurir(gudang, "Kurir-" + i));
        }

        monitoringThread.start();
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n=== SHUTTING DOWN SISTEM (Mencapai 15 Detik) ===");

        pemasokPool.shutdownNow();
        kurirPool.shutdownNow();
        monitoringThread.interrupt();

        try {
            if (!pemasokPool.awaitTermination(3, TimeUnit.SECONDS)) {
                System.out.println("Beberapa thread pemasok tidak merespon penghentian.");
            }
            if (!kurirPool.awaitTermination(3, TimeUnit.SECONDS)) {
                System.out.println("Beberapa thread kurir tidak merespon penghentian.");
            }
        } catch (InterruptedException e) {
            System.out.println("Proses penghentian terinterupsi.");
        }

        System.out.println("=== SISTEM GUDANG BERHASIL DIHENTIKAN ===");
    }
}