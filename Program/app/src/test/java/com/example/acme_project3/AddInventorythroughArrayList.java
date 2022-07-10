package com.example.acme_project3;

public @interface AddInventorythroughArrayList {

    /*package com.example.acme_project3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddInventory extends AppCompatActivity {


    public ArrayList<String> data = new ArrayList<String>();
    public ArrayList<String> data1 = new ArrayList<String>();
    public ArrayList<String> data2 = new ArrayList<String>();
    public ArrayList<String> data3 = new ArrayList<String>();


    EditText eProductName; //Creates instance of EditText for the ProductName
    EditText eDepartment; //Creates instance of EditText for the Department
    EditText eQuantity; //Creates instance of EditText for the Quantity
    EditText eCost; //Creates instance of EditText for the Cost
    Button bAddInventory; //Creates button instance of Add Inventory button

    //Coding below is standard coding when Android Mobile Apps are created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);
        //End of standard coding

        eProductName = (EditText) findViewById(R.id.add_ProductName); // References Product Name field in activity
        eDepartment = (EditText) findViewById(R.id.add_Department); // References Department name field in activity
        eQuantity = (EditText) findViewById(R.id.add_Quantity); // References Quantity field in activity
        eCost = (EditText) findViewById(R.id.add_Cost); // References cost field in activity
        bAddInventory = (Button) findViewById(R.id.btn_AddInventory); //References Add Inventory button

        bAddInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });


    }


    public void add(){

        String ProductName = eProductName.getText().toString();
        String DepartmentName = eDepartment.getText().toString();
        int Quantity = Integer.parseInt(eQuantity.getText().toString());
        int Cost = Integer.parseInt(eCost.getText().toString());

        data.add(ProductName);
        data1.add(DepartmentName);
        data2.add(String.valueOf(Quantity));
        data3.add(String.valueOf(Cost));

        TableLayout table = (TableLayout) findViewById(R.id.table_Inventory);
        TableRow row = new TableRow(this);
        TextView t1 = new TextView(this);
        TextView t2 = new TextView(this);
        TextView t3 = new TextView(this);
        TextView t4 = new TextView(this);

        for(int i = 0; i<data.size(); i++){
            String data_ProductName = data.get(i);
            String data_DepartmentName = data1.get(i);
            String data_Quantity = data2.get(i);
            String data_Cost = data3.get(i);

            t1.setText(data_ProductName);
            t2.setText(data_DepartmentName);
            t3.setText(data_Quantity);
            t4.setText(data_Cost);
        }

        row.addView(t1);
        row.addView(t2);
        row.addView(t3);
        row.addView(t4);
        table.addView(row);

        eProductName.setText("");
        eDepartment.setText("");
        eQuantity.setText("");
        eCost.setText("");
        bAddInventory.setText("");
        eProductName.requestFocus();

        //The below creates a message saying that the they were added successfully.
        Toast.makeText(AddInventory.this, "Item(s) successfully added to Inventory", Toast.LENGTH_SHORT).show();

    }




//FROM THE BOTTOM OF ADDINVENTORY.JAVA
}*/


        /*public void add(){

        String ProductName = eProductName.getText().toString();
        String DepartmentName = eDepartment.getText().toString();
        int Quantity = Integer.parseInt(eQuantity.getText().toString());
        int Cost = Integer.parseInt(eCost.getText().toString());

        data.add(ProductName);
        data1.add(DepartmentName);
        data2.add(String.valueOf(Quantity));
        data3.add(String.valueOf(Cost));

        TableLayout table = (TableLayout) findViewById(R.id.table_Inventory);
        TableRow row = new TableRow(this);
        TextView t1 = new TextView(this);
        TextView t2 = new TextView(this);
        TextView t3 = new TextView(this);
        TextView t4 = new TextView(this);

        for(int i = 0; i<data.size(); i++){
            String data_ProductName = data.get(i);
            String data_DepartmentName = data1.get(i);
            String data_Quantity = data2.get(i);
            String data_Cost = data3.get(i);

            t1.setText(data_ProductName);
            t2.setText(data_DepartmentName);
            t3.setText(data_Quantity);
            t4.setText(data_Cost);
        }

        row.addView(t1);
        row.addView(t2);
        row.addView(t3);
        row.addView(t4);
        table.addView(row);

        eProductName.setText("");
        eDepartment.setText("");
        eQuantity.setText("");
        eCost.setText("");
        bAddInventory.setText("");
        eProductName.requestFocus();

        //The below creates a message saying that the they were added successfully.
        Toast.makeText(AddInventory.this, "Item(s) successfully added to Inventory", Toast.LENGTH_SHORT).show();

    }*/



}
