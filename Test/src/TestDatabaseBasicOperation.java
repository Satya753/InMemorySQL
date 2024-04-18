import Database.Column;
import Database.Schema;
import Database.Table;
import Database.Value;
import org.junit.Test;

import java.util.*;

public class TestDatabaseBasicOperation<T> {

    @Test
    public void createTable(){
        Map< String , Column> newSchema = new HashMap<>();
        String primaryKey = "ID";
        newSchema.put("ID" , new Column("ID" , "Integer"));
        newSchema.put("NAME" , new Column("NAME" , "String"));
        newSchema.put("AGE" , new Column("AGE" , "Integer"));
        newSchema.put("CITY" ,  new Column("CITY" , "Integer"));
        Schema schema = new Schema(newSchema);
        Table table = new Table(schema ,primaryKey);

        // Rows insertions
        Map<String, Value> newRow = new HashMap<>();
        Value <Integer > id = new Value<>(23);
        Value<String> name = new Value<>("Satyajeet");
        Value<String > city = new Value<>("Delhi");
        newRow.put("ID" ,id);
        newRow.put("NAME" , name);
        newRow.put("CITY" , city);
        table.insertRow(newRow);
        Map<String, Value> newRow2 = new HashMap<>();
        Value <Integer > id1 = new Value<>(24);
        Value<String> name1 = new Value<>("Satyajeet");
        Value<String > city1 = new Value<>("Delhi");
        newRow2.put("ID" ,id1);
        newRow2.put("NAME" , name1);
        newRow2.put("CITY" , city1);
        table.insertRow(newRow2);
        Set< String > columns = new HashSet<>();
        columns.add(primaryKey);
        columns.add("NAME");
        Map < String , Value > eligiblerow = new HashMap<>();

        table.selectRows(columns , eligiblerow);

        Map < String , Value > del = new HashMap<>();
        del.put(primaryKey , new Value<Integer>(23));
        System.out.println("----------------------After deletion--------------------------------------");
        table.deleteRows(del);
        table.selectRows(columns , eligiblerow);


        // Test update queries

        System.out.println("----------------------After the update queries----------------------------");
        Map < String , Value > updateValue = new HashMap<>();

        updateValue.put("NAME" , new Value<String>("MARIKO SAMA"));
        Map < String , Value > eligibleUpdate = new HashMap<>();
        eligibleUpdate.put(primaryKey , new Value<Integer> (24));
        table.updateRow(updateValue , eligibleUpdate);
        table.selectRows(columns , eligiblerow);



    }
}
