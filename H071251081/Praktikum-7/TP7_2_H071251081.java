import java.util.Random;
import java.util.concurrent.*;
import java.util.Map;

class DataProcessor implements Runnable {
    private final String fileName;
    private final ConcurrentHashMap<String, Integer> results;
    private final CountDownLatch latch;
    private final Random random = new Random();

    public DataProcessor(String fileName, ConcurrentHashMap<String, Integer> results, CountDownLatch latch) {
        this.fileName = fileName;
        this.results = results;
        this.latch = latch;
    }

    public int process(String fileName) {
        try {
            int duration = random.nextInt(1501) + 500; 
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return random.nextInt(401) + 100;
    }

    @Override
    public void run() {
        int wordCount = process(fileName);
        results.put(fileName, wordCount);
        
        System.out.printf("[%s] Selesai memproses %s (%d kata).\n", 
            Thread.currentThread().getName(), fileName, wordCount);
        
        latch.countDown(); 
    }
}

public class TP7_2_H071251081 {
    public static void main(String[] args) {
        int jumlahDokumen = 10;
        int jumlahThread = 4;
        
        ExecutorService executor = Executors.newFixedThreadPool(jumlahThread);
        
        ConcurrentHashMap<String, Integer> results = new ConcurrentHashMap<>();
        
        CountDownLatch latch = new CountDownLatch(jumlahDokumen);

        System.out.println("--- Memulai Pemrosesan Dokumen ---\n");
        long startTime = System.currentTimeMillis();

        for (int i = 1; i <= jumlahDokumen; i++) {
            String docName = "Dokumen_" + (char)('A' + i - 1) + ".txt";
            executor.execute(new DataProcessor(docName, results, latch));
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long totalTime = System.currentTimeMillis() - startTime;
        executor.shutdown();

        System.out.println("\n--- Hasil Akhir (Ringkasan) ---");
        System.out.println("-------------------------------------------");
        System.out.printf("%-20s | %-15s\n", "Nama Dokumen", "Jumlah Kata");
        System.out.println("-------------------------------------------");
        
        int totalKata = 0;
        for (Map.Entry<String, Integer> entry : results.entrySet()) {
            System.out.printf("%-20s | %-15d\n", entry.getKey(), entry.getValue());
            totalKata += entry.getValue();
        }
        
        System.out.println("-------------------------------------------");
        System.out.println("Total Kata Keseluruhan : " + totalKata);
        System.out.printf("Rata-rata Waktu Proses : %.2f ms\n", (double) totalTime / jumlahDokumen);
        System.out.println("Sistem Berhenti.");
    }
}