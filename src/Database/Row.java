package Database;

import java.util.Map;
import java.util.Set;

public class Row {
    private Value rowId;
    public Map < String ,Value > rowvalues;
    public Row(Map<String , Value > row , Value rowId){
        this.rowId = rowId;
        this.rowvalues = row;
    }
    public void outputtRow(Set< String > columnName){
        for (String column : columnName){
            System.out.print(rowvalues.get(column).getItem() + " | ");
        }
        System.out.println("");
    }
    public boolean checkEligibleRow(Map < String , Value > eligibleColumnValues){
        for (Map.Entry<String ,Value>values : eligibleColumnValues.entrySet()){
            if (!rowvalues.containsKey(values.getKey()) || !rowvalues.get(values.getKey()).getItem().equals(values.getValue().getItem()))
                return false;
        }
        return true;
    }
    public Row updateRow(Map < String , Value > newRowValues){
        for (Map.Entry<String , Value > values : this.rowvalues.entrySet()){
            if (newRowValues.containsKey(values.getKey()))
                this.rowvalues.put(values.getKey() , newRowValues.get(values.getKey()));
        }
        return this;
    }

}
