import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Library {
    private List<LibraryItem> items = new ArrayList<>();
    private List<Member> members = new ArrayList<>();
    private LibraryLogger logger = new LibraryLogger();

    public void addItem(LibraryItem item) {
        items.add(item);
        System.out.println(item.title + " berhasil ditambahkan");
    }

    public void addMember(Member member) {
        members.add(member);
        System.out.println("Member " + member.name + " berhasil ditambahkan");
    }

    public LibraryItem findItemById(int itemId) {
        return items.stream().filter(i -> i.itemId == itemId).findFirst()
                .orElseThrow(() -> new NoSuchElementException("Item tidak ditemukan."));
    }

    public Member findMemberById(int memberId) {
        return members.stream().filter(m -> m.memberId == memberId).findFirst()
                .orElseThrow(() -> new NoSuchElementException("Member tidak ditemukan."));
    }

    public void getLibraryStatus() {
        System.out.println("+-----+------------+------------+");
        System.out.println("| ID  | Judul      | Status     |");
        System.out.println("+-----+------------+------------+");
        for (LibraryItem i : items) {
            String status = i.isBorrowed ? "Dipinjam" : "Tersedia";
            System.out.printf("| %-3d | %-10s | %-10s |\n", i.itemId, i.title, status);
        }
        System.out.println("+-----+------------+------------+");
    }

    public LibraryLogger getLogger() { return logger; }
}