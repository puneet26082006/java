import java.io.Serializable;

class Column implements Serializable {
    String name;
    String type; // "INT" or "STRING"

    public Column(String name, String type) {
        this.name = name;
        this.type = type.toUpperCase();
    }
}