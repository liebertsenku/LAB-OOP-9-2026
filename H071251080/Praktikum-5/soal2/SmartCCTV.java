public class SmartCCTV extends PerangkatElektronik implements InteraksiInternet {

    public SmartCCTV(String merk, int dayaListrik) {
        super(merk, dayaListrik);
    }

    @Override
    public void cekFungsi() {
        System.out.println("[SmartCCTV] Fungsi: Pemantauan keamanan 24 jam.");
    }

    @Override
    public void hubungkanWiFi() {
        System.out.println("[SmartCCTV] Mengirim data ke server...");
    }
}