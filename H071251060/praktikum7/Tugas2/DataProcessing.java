package praktikum7.Tugas2;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class DataProcessing implements Runnable {
    private final String fileName;
    private final ConcurrentHashMap<String, HasilProses> storage;
    private final CountDownLatch latch;
    private final Random random = new Random();

    public DataProcessing (String fileName, ConcurrentHashMap<String, HasilProses> storage, CountDownLatch latch) {
        this.fileName = fileName;
        this.storage = storage;
        this.latch = latch;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        String currentThreadName = Thread.currentThread().getName();
        
        try {
            long jedaSimulasi = 500 + random.nextInt(1501);
            Thread.sleep(jedaSimulasi);
            
            int jumlahKata = 50 + random.nextInt(451);
            long endTime = System.currentTimeMillis();
            long durasiProses = endTime - startTime;
            
            storage.put(fileName, new HasilProses(jumlahKata, currentThreadName, durasiProses));
            
            System.out.printf("[%s] Selesai memproses %s (%d kata) dalam %d ms.\n", 
                currentThreadName, fileName, jumlahKata, durasiProses);
            
        } catch (InterruptedException e) {
            System.out.println("[" + currentThreadName + "] Terganggu saat memproses " + fileName);
            Thread.currentThread().interrupt();
        } finally {
            latch.countDown();
        }
    }
}