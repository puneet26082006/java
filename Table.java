import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Table implements Serializable {
    String name;
    List<Column> columns = new ArrayList<>();
    List<Row> rows = new ArrayList<>();

    public Table(String name) {
        this.name = name;
    }

    public void addColumn(Column col) {
        columns.add(col);
    }

    public void insertRow(Row row) {
        rows.add(row);
    }
}