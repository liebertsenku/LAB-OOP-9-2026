package praktikum7.Tugas2;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainSearchEngine {
    public static void main(String[] args) {
        String[] daftarDokumen = {
            "Dokumen_A.txt", "Dokumen_B.txt", "Dokumen_C.txt", "Dokumen_D.txt", "Dokumen_E.txt",
            "Dokumen_F.txt", "Dokumen_G.txt", "Dokumen_H.txt", "Dokumen_I.txt", "Dokumen_J.txt"
        };

        int jumlahThread = 4;
        ExecutorService executor = Executors.newFixedThreadPool(jumlahThread);
        ConcurrentHashMap<String, HasilProses> wadahData = new ConcurrentHashMap<>();
        CountDownLatch barrier = new CountDownLatch(daftarDokumen.length);

        System.out.println("=== SEARCH ENGINE INDEXER DIMULAI ===");
        System.out.println("Memproses " + daftarDokumen.length + " dokumen dengan " + jumlahThread + " thread...\n");

        for (String dokumen : daftarDokumen) {
            executor.execute(new DataProcessing(dokumen, wadahData, barrier));
        }

        try {
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();

        int totalKataKeseluruhan = 0;
        long totalWaktuProses = 0;

        System.out.println("\n=================================== KLASEMEN AKHIR INDEXING ===================================");
        System.out.printf("%-17s | %-15s | %-15s\n", "Nama Dokumen", "Thread Handling", "Durasi Proses");
        System.out.println("-----------------------------------------------------------------------------------------------");

        for (Map.Entry<String, HasilProses> entry : wadahData.entrySet()) {
            String namaDokumen = entry.getKey();
            HasilProses hasil = entry.getValue();
            
            totalKataKeseluruhan += hasil.getJumlahKata();
            totalWaktuProses += hasil.getDurasi();

            System.out.printf("%-17s | %-15s | %d ms\n", 
            namaDokumen, hasil.getNamaThread(), hasil.getDurasi());
        }
        
        System.out.println("-----------------------------------------------------------------------------------------------");
        
        double rataRataWaktu = (double) totalWaktuProses / daftarDokumen.length;
        
        System.out.println("STATISTIK RINGKASAN:");
        System.out.println("-> Total Kata Keseluruhan      : " + totalKataKeseluruhan + " kata");
        System.out.printf("-> Rata-rata Waktu Pemrosesan  : %.2f ms\n", rataRataWaktu);
        System.out.println("===============================================================================================");
    }
}