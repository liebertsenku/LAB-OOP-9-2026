import java.util.ArrayList;
import java.util.List;

public class Member {
    protected String name;
    protected int memberId;
    protected List<LibraryItem> borrowedItems = new ArrayList<>();

    public Member(String name, int memberId) {
        this.name = name;
        this.memberId = memberId;
    }

    public String borrow(LibraryItem item, int days) {
        if (item.isBorrowed) throw new IllegalStateException("Item tidak tersedia.");
        String msg = item.borrowItem(days);
        item.isBorrowed = true;
        borrowedItems.add(item);
        return msg;
    }

    public String returnItem(LibraryItem item, int daysLate) {
        if (!borrowedItems.contains(item)) return "Item tidak ada di daftar pinjam anggota.";
        double fine = item.calculateFine(daysLate);
        item.returnItem();
        borrowedItems.remove(item);
        return "Item " + item.title + " berhasil dikembalikan dengan denda: Rp " + String.format("%.0f", fine);
    }

    public void getBorrowedItems() {
        if (borrowedItems.isEmpty()) {
            System.out.println("Tidak ada item yang dipinjam");
            return;
        }
        System.out.println("+-----+------------+");
        System.out.println("| ID  | Judul      |");
        System.out.println("+-----+------------+");
        for (LibraryItem item : borrowedItems) {
            System.out.printf("| %-3d | %-10s |\n", item.itemId, item.title);
        }
        System.out.println("+-----+-----------+");
    }
}