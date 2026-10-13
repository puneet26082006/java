import java.util.Arrays;

public class SQLParser {
    private Database db;

    public SQLParser(Database db) {
        this.db = db;
    }

    public void executeQuery(String query) {
        query = query.trim().replaceAll(";", ""); // Clean up the query
        String[] tokens = query.split("\\s+");
        String command = tokens[0].toUpperCase();

        try {
            switch (command) {
                case "CREATE":
                    handleCreate(query);
                    break;
                case "INSERT":
                    handleInsert(query);
                    break;
                case "SELECT":
                    handleSelect(tokens);
                    break;
                default:
                    System.out.println("Error: Unknown command '" + command + "'");
            }
        } catch (Exception e) {
            System.out.println("Syntax Error: Check your SQL statement. (" + e.getMessage() + ")");
        }
    }

    // Parses: CREATE TABLE users (id INT, name STRING)
    private void handleCreate(String query) {
        String tableName = query.substring(query.indexOf("TABLE") + 6, query.indexOf("(")).trim();
        String columnsPart = query.substring(query.indexOf("(") + 1, query.indexOf(")"));
        
        if (db.tables.containsKey(tableName)) {
            System.out.println("Error: Table '" + tableName + "' already exists.");
            return;
        }

        Table table = new Table(tableName);
        String[] columns = columnsPart.split(",");
        
        for (String col : columns) {
            String[] colDef = col.trim().split("\\s+");
            table.addColumn(new Column(colDef[0], colDef[1]));
        }
        
        db.tables.put(tableName, table);
        System.out.println("Table '" + tableName + "' created successfully.");
    }

    // Parses: INSERT INTO users VALUES (1, 'Alice')
    private void handleInsert(String query) {
        String tableName = query.substring(query.indexOf("INTO") + 5, query.indexOf("VALUES")).trim();
        Table table = db.tables.get(tableName);
        
        if (table == null) {
            System.out.println("Error: Table does not exist.");
            return;
        }

        String valuesPart = query.substring(query.indexOf("(") + 1, query.indexOf(")"));
        String[] values = valuesPart.split(",");
        
        Row row = new Row();
        for (int i = 0; i < values.length; i++) {
            Column col = table.columns.get(i);
            String val = values[i].trim().replace("'", ""); // Remove single quotes
            
            if (col.type.equals("INT")) {
                row.addValue(col.name, Integer.parseInt(val));
            } else {
                row.addValue(col.name, val);
            }
        }
        table.insertRow(row);
        System.out.println("1 row inserted.");
    }

    // Parses: SELECT * FROM users
    private void handleSelect(String[] tokens) {
        if (!tokens[1].equals("*") || !tokens[2].equalsIgnoreCase("FROM")) {
            System.out.println("Only 'SELECT * FROM tablename' is supported in this version.");
            return;
        }
        
        String tableName = tokens[3];
        Table table = db.tables.get(tableName);
        
        if (table == null) {
            System.out.println("Error: Table '" + tableName + "' does not exist.");
            return;
        }

        // Print Column Headers
        for (Column col : table.columns) {
            System.out.print(col.name + "\t|\t");
        }
        System.out.println("\n-------------------------------------------------");
        
        // Print Rows
        for (Row row : table.rows) {
            for (Column col : table.columns) {
                System.out.print(row.getValue(col.name) + "\t|\t");
            }
            System.out.println();
        }
    }
}
