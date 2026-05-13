public class SmartSpeaker extends PerangkatElektronik implements InteraksiInternet, KontrolSuara {

    public SmartSpeaker(String merk, int dayaListrik) {
        super(merk, dayaListrik);
    }

    @Override
    public void cekFungsi() {
        System.out.println("[SmartSpeaker] Fungsi: Pemutar musik & asisten suara.");
    }

    @Override
    public void hubungkanWiFi() {
        System.out.println("[SmartSpeaker] Mengirim data ke server...");
    }

    @Override
    public void prosesPerintah(String perintah) {
        System.out.println("[SmartSpeaker] Memproses perintah: \"" + perintah + "\"");
    }
}