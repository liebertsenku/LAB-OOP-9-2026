public class SmartLamp extends PerangkatElektronik implements KontrolSuara {

    public SmartLamp(String merk, int dayaListrik) {
        super(merk, dayaListrik);
    }

    @Override
    public void cekFungsi() {
        System.out.println("[SmartLamp] Fungsi: Pencahayaan otomatis.");
    }

    @Override
    public void prosesPerintah(String perintah) {
        if (perintah.equalsIgnoreCase("NYALA")) {
            System.out.println("[SmartLamp] Lampu berpijar!");
        } else {
            System.out.println("[SmartLamp] Perintah tidak dikenal: " + perintah);
        }
    }
}