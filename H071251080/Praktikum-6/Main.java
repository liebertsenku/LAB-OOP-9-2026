import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Sistem Manajemen Perpustakaan ===");
            System.out.println("1. Tambah Item\n2. Tambah Anggota\n3. Pinjam Item\n4. Kembalikan Item\n5. Status Perpustakaan\n6. Lihat Log\n7. Item Dipinjam Anggota\n8. Keluar");
            System.out.print("Pilih: ");
            try {
                int opt = sc.nextInt();
                if (opt == 8) break;
                switch (opt) {
                    case 1:
                        System.out.print("1. Buku 2. DVD: ");
                        int t = sc.nextInt(); sc.nextLine();
                        System.out.print("Judul: "); String title = sc.nextLine();
                        System.out.print("ID: "); int id = sc.nextInt();
                        if (t == 1) {
                            sc.nextLine(); System.out.print("Author: ");
                            library.addItem(new Book(title, id, sc.nextLine()));
                        } else {
                            System.out.print("Durasi: ");
                            library.addItem(new DVD(title, id, sc.nextInt()));
                        }
                        break;
                    case 2:
                        sc.nextLine(); System.out.print("Nama: "); String n = sc.nextLine();
                        System.out.print("ID Member: ");
                        library.addMember(new Member(n, sc.nextInt()));
                        break;
                    case 3:
                        System.out.print("ID Member: "); int mId = sc.nextInt();
                        System.out.print("ID Item: "); int iId = sc.nextInt();
                        System.out.print("Hari: "); int d = sc.nextInt();
                        Member m = library.findMemberById(mId);
                        LibraryItem it = library.findItemById(iId);
                        System.out.println(m.borrow(it, d));
                        library.getLogger().logActivity(it.getClass().getSimpleName(), it.title, m.name, true);
                        break;
                    case 4:
                        System.out.print("ID Member: "); mId = sc.nextInt();
                        System.out.print("ID Item: "); iId = sc.nextInt();
                        System.out.print("Terlambat (hari): "); int late = sc.nextInt();
                        m = library.findMemberById(mId);
                        it = library.findItemById(iId);
                        System.out.println(m.returnItem(it, late));
                        library.getLogger().logActivity(it.getClass().getSimpleName(), it.title, m.name, false);
                        break;
                    case 5: library.getLibraryStatus(); break;
                    case 6: System.out.println(library.getLogger().getLogs()); break;
                    case 7: 
                        System.out.print("ID Member: ");
                        library.findMemberById(sc.nextInt()).getBorrowedItems();
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                sc.nextLine();
            }
        }
    }
}