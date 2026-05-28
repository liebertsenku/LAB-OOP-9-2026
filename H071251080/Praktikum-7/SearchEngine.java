import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchEngine {
    public static void main(String[] args) throws InterruptedException {
        int jumlahDokumen = 10;
        ExecutorService executor = Executors.newFixedThreadPool(4);

        Map<String, Integer> hasilKata = new ConcurrentHashMap<>();
        CountDownLatch latch = new CountDownLatch(jumlahDokumen);
        DataProcessor processor = new DataProcessor();
        List<String[]> ringkasan = new CopyOnWriteArrayList<>();

        long waktuMulai = System.currentTimeMillis();

        for (int i = 1; i <= jumlahDokumen; i++) {
            String namaDokumen = "Dokumen_" + i + ".txt";
            executor.execute(() -> {
                long mulaiProses = System.currentTimeMillis();
                int kata = processor.process(namaDokumen);
                long selesaiProses = System.currentTimeMillis();
                long durasi = selesaiProses - mulaiProses;

                hasilKata.put(namaDokumen, kata);
                String namaThread = Thread.currentThread().getName();
                ringkasan.add(new String[] {namaDokumen, namaThread, durasi + "ms"});
                System.out.println("[" + namaThread + "] Selesai memproses " + namaDokumen + " (" + kata + ") kata");
                latch.countDown();
            });
        }
        latch.await();
        executor.shutdown();

        System.out.println("\n" + "=".repeat(60));
        System.out.printf("%-20s | %-15s | %-10s\n", "Nama Dokumen", "Thread", "Durasi");
        System.out.println("-".repeat(60));

        int totalKata = 0;
        long totalWaktu = 0;

        for (String [] baris : ringkasan) {
            System.out.printf("%-20s | %-15s | %-10s\n", baris[0], baris[1], baris[2]);
            totalKata += hasilKata.get(baris[0]);
            totalWaktu += Long.parseLong(baris[2].replace("ms", ""));
        }
        System.out.println("-".repeat(60));
        System.out.println("Total Kata Keseluruhan: " + totalKata);
        System.out.println("Rata-rata Waktu Proses: " + (totalWaktu / jumlahDokumen) + " ms");
    }
}