package Database;

import java.util.*;

public class Table {
    private Map < String , Row >  listOfRow;
    private  Schema schema;
    private String primaryKey;
    private Object lock = new Object();

    public Table(Schema schema , String primaryKey){
        this.listOfRow = new HashMap<>();
        this.schema = schema;
        this.primaryKey = primaryKey;
    }

    public  void insertRow(Map< String , Value > newRow){
        for (Map.Entry<String , Value > column : newRow.entrySet()){
            if (!typeChecking(column.getKey())){
                throw new IllegalArgumentException();
            }
        }
        if(listOfRow.containsKey(newRow.get(this.primaryKey)))
            new IllegalArgumentException("Row cannot be inserted duplicate primary key");
        listOfRow.putIfAbsent(newRow.get("ID").getItem().toString() , new Row(newRow , newRow.get("ID")));
    }
    private boolean typeChecking(String column_name){
        return true;
        //return schema.checkColumnDatatype(column_name);

    }

    public void selectRows(Set< String > columns , Map < String , Value > eligibleRows){
        for (Map.Entry<String , Row> row : listOfRow.entrySet()){
            if (row.getValue().checkEligibleRow(eligibleRows))
                row.getValue().outputtRow(columns);
        }
    }

    public Value getRowValue(String columnName , String id){
        synchronized (lock) {
            return listOfRow.get(id).rowvalues.get(columnName);
        }
    }
    public void deleteRows(Map < String , Value > eligibleRows){
        List < String > keyrows = new ArrayList<>(this.listOfRow.keySet());
        for (String row : keyrows){
            if (this.listOfRow.get(row).checkEligibleRow(eligibleRows))
                this.listOfRow.remove(row);
        }
    }
    public  void updateRow(Map < String , Value > updateValue , Map<String , Value> eligibleRows){
        synchronized (lock) {
            for (Map.Entry<String, Row> row : listOfRow.entrySet()) {
                if (row.getValue().checkEligibleRow(eligibleRows)) {
                    Row newRow = row.getValue().updateRow(updateValue);
                    this.listOfRow.put(row.getKey(), newRow);
                }
            }
        }
    }
}
