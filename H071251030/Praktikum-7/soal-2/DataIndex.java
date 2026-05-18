public class DataIndex {
    int process(String fileName) {
        int totalWord = (int) Math.random() * 10000000;
        try {
            System.out.println("[" + Thread.currentThread().getName() + "]: " + fileName + " sedang diproses");
            Thread.sleep(1000 + (int) (Math.random() * 2000));
            System.out.println("[" + Thread.currentThread().getName() + "] " + fileName + " selesai diproses");
        } catch (InterruptedException e) {
            System.out.println(fileName + " diputus oleh thread " + Thread.currentThread().getName());
        }
        return totalWord;
    }
}
