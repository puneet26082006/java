import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

class Row implements Serializable {
    Map<String, Object> values = new HashMap<>();

    public void addValue(String columnName, Object value) {
        values.put(columnName, value);
    }
    
    public Object getValue(String columnName) {
        return values.get(columnName);
    }
}