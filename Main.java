import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("    Welcome to JavaMiniDB Engine");
        System.out.println("=========================================");
        
        // Load existing data from disk, or start fresh
        Database db = Database.loadFromDisk();
        SQLParser parser = new SQLParser(db);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("SQL> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("EXIT") || input.equalsIgnoreCase("QUIT")) {
                db.saveToDisk(); // Save before closing!
                System.out.println("Goodbye.");
                break;
            }

            if (!input.isEmpty()) {
                parser.executeQuery(input);
            }
        }
        scanner.close();
    }
}