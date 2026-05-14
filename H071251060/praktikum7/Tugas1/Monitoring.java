package praktikum7.Tugas1;

public class Monitoring implements Runnable {
    private final Gudang gudang;

    public Monitoring(Gudang gudang) {
        this.gudang = gudang;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(1000); 

                int stokSaatIni = gudang.getStok();
                int maks = gudang.getKapasitasMaksimal();
                int persentase = (stokSaatIni * 100) / maks;
                
                int jumlahHash = persentase / 10;
                StringBuilder bar = new StringBuilder();
                for (int i = 0; i < 10; i++) {
                    if (i < jumlahHash) {
                        bar.append("#");
                    } else {
                        bar.append("-");
                    }
                }
                
                System.out.println("Status : Gudang[" + bar + "] " + persentase + "% (Stok: " + stokSaatIni + ")");
            }
        } catch (InterruptedException e) {
        }
    }
}