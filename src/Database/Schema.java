package Database;

import java.util.HashMap;
import java.util.Map;

public class Schema{
    // We can have composite primary key as well
    private Column primaryKey;
    private Map< String , Column> columnMap;
    public Schema( Map < String , Column > columnMap){
        this.columnMap = new HashMap<>();
    }
    public void getSchema(){

    }
    public boolean checkColumnDatatype(String column_name){
        return column_name.contains(column_name);

    }
}
