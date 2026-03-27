import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Database implements Serializable {
    private static final String FILE_NAME = "database.dat";
    Map<String, Table> tables = new HashMap<>();

    // Save the entire database state to disk
    public void saveToDisk() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(this);
            System.out.println("Database state saved to disk.");
        } catch (IOException e) {
            System.out.println("Error saving database: " + e.getMessage());
        }
    }

    // Load the database from disk on startup
    public static Database loadFromDisk() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                return (Database) ois.readObject();
            } catch (Exception e) {
                System.out.println("Failed to load database. Starting fresh.");
            }
        }
        return new Database(); // Return empty DB if no file exists
    }
}