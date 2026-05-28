import java.util.Random;

public class DataProcessor {
    private Random acak = new Random();

    public int process(String fileName) {
        try {
            int durasi = 500 + acak.nextInt(1501);
            Thread.sleep(durasi);

            int jumlahKata = 100 + acak.nextInt(401);
            return jumlahKata;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return 0;
        }
    }
}