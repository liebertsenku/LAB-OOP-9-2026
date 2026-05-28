public abstract class PerangkatElektronik {
    private String merk;
    private int dayaListrik;

    public PerangkatElektronik(String merk, int dayaListrik) {
        this.merk = merk;
        this.dayaListrik = dayaListrik;
    }

    public abstract void cekFungsi();

    public void infoPower() {
        System.out.println("[" + merk + "] Perangkat sedang menyedot daya sebesar " + dayaListrik + " Watt.");
    }

    public String getMerk() { return merk; }
    public int getDayaListrik() { return dayaListrik; }
}