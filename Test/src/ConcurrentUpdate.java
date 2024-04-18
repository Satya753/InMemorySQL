import Database.Column;
import Database.Schema;
import Database.Table;
import Database.Value;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ConcurrentUpdate {


    Map< String , Column> newSchema = new HashMap<>();
    String primaryKey ;
    Table table;
    @Before
    public void createSchema(){
        primaryKey = "ID";
        newSchema.put("ID" , new Column("ID" , "Integer"));
        newSchema.put("NAME" , new Column("NAME" , "String"));
        newSchema.put("AGE" , new Column("AGE" , "Integer"));
        newSchema.put("CITY" ,  new Column("CITY" , "Integer"));
        Schema schema = new Schema(newSchema);
        table = new Table(schema ,primaryKey);

        // Rows insertions
        Map<String, Value> newRow = new HashMap<>();
        Value <Integer > id = new Value<>(23);
        Value<String> name = new Value<>("Satyajeet");
        Value<String > city = new Value<>("Delhi");
        Value < Integer > age = new Value<>(28);
        newRow.put("ID" ,id);
        newRow.put("NAME" , name);
        newRow.put("CITY" , city);
        newRow.put("AGE" , age);
        table.insertRow(newRow);
        Map<String, Value> newRow2 = new HashMap<>();
        Value <Integer > id1 = new Value<>(24);
        Value<String> name1 = new Value<>("Satyajeet");
        Value<String > city1 = new Value<>("Delhi");
        newRow2.put("AGE" , new Value<Integer>(23));
        newRow2.put("ID" ,id1);
        newRow2.put("NAME" , name1);
        newRow2.put("CITY" , city1);
        table.insertRow(newRow2);
        Set< String > columns = new HashSet<>();
        columns.add(primaryKey);
        columns.add("NAME");
        Map < String , Value > eligiblerow = new HashMap<>();
    }


    @Test
    public void testConcurrentUpdate() throws InterruptedException {
        Set< String > columns = new HashSet<>();
        columns.add(primaryKey);
        columns.add("NAME");
        columns.add("AGE");
        Map < String , Value > eligiblerow = new HashMap<>();
        //table.selectRows(columns , eligiblerow);

        Thread update1  = new Thread(()->{  Map < String , Value > updateValue = new HashMap<>();

            updateValue.put("NAME" , new Value<String>("MARIKO SAMA"));
            Map < String , Value > eligibleUpdate = new HashMap<>();
            eligibleUpdate.put(primaryKey , new Value<Integer> (24));
            updateValue.put("AGE" , new Value<Integer> ((Integer) table.getRowValue("AGE" , "24" ).getItem() + 1));
            table.updateRow(updateValue , eligibleUpdate);

        });
        Thread update2 = new Thread(() ->{  Map < String , Value > updateValue = new HashMap<>();

            updateValue.put("NAME" , new Value<String>("ANJIN SAMA"));
            Map < String , Value > eligibleUpdate = new HashMap<>();
            eligibleUpdate.put(primaryKey , new Value<Integer> (24));
            Integer newage = ((Integer)table.getRowValue("AGE" , "24").getItem() + 1);
            System.out.println("AGE---" + newage);
            updateValue.put("AGE" , new Value<Integer> (newage));
            System.out.println("AGE------" + (Integer)table.getRowValue("AGE" , "24").getItem());
            table.updateRow(updateValue , eligibleUpdate);


        });
        update1.start();
        update2.start();
        update2.join();
        update1.join();
        table.selectRows(columns , eligiblerow);
    }

}
