import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LibraryLogger {
    private List<String> logs = new ArrayList<>();
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void logActivity(String type, String title, String memberName, boolean isBorrow) {
        String timestamp = LocalDateTime.now().format(dtf);
        String action = isBorrow ? "dipinjam oleh" : "dikembalikan oleh";
        String activity = String.format("%s [%s] %s %s %s", timestamp, type, title, action, memberName);
        logs.add(activity);
    }

    public String getLogs() {
        if (logs.isEmpty()) return "Belum ada aktivitas.";
        return String.join("\n", logs);
    }

    public void clearLogs() {
        logs.clear();
    }
}