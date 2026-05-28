import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Gudang gudang = new Gudang(0, 100);

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 2; i++) {
            executor.execute(new Pemasok(gudang));
        }

        for (int i = 0; i < 3; i++) {
            executor.execute(new Kurir(gudang));
        }

        Thread monitorThread = new Thread(new Monitoring(gudang));
        monitorThread.start();

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n=== WAKTU HABIS! MENGHENTIKAN SISTEM. ===");
        executor.shutdownNow();
        monitorThread.interrupt();

        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("Memaksa berhenti...");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Sistem logistik selesai.");
    }
}