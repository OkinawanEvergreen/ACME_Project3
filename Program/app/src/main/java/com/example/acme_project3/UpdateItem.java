package com.example.acme_project3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;

public class UpdateItem extends AppCompatActivity {
    private TextView linkHome, linkInventory, linkUpdateInventory; //Creates instance for textview links
    private TextView tItemID, tProductName, tDepartment, tDetails, tQuantity, tCost;

    private EditText eItemID, eProductName, eDepartmentName, eDetails, eQuantity, eCost;

    private TextInputLayout inputlayout_ItemID, inputlayout_ProductName, inputlayout_Department, inputlayout_Details, inputlayout_Quantity, inputlayout_Cost;

    private Button bUpdate, bGenerate; //Creates button instance of Add Inventory button

    private DatabaseHelper databaseHelper; //Labels the database object as database helper
    private final AppCompatActivity activity = UpdateItem.this; //Establishes activity under Update Item

    //Coding below is standard coding when Android Mobile Apps are created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);
        //End of standard coding

        databaseHelper = new DatabaseHelper(activity);

        initiateViews();
        initiateOnClickLinks();

        bUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String itemID = eItemID.getText().toString();
                String ProductName = eProductName.getText().toString();
                String DepartmentName = eDepartmentName.getText().toString();
                String Details = eDetails.getText().toString();
                String Quantity = eQuantity.getText().toString();
                String c = eCost.getText().toString();
                Double Cost = Double.parseDouble(c);

                Toast.makeText(UpdateItem.this, "itemID is: "+itemID, Toast.LENGTH_SHORT).show();
                databaseHelper.updateItem(itemID, ProductName, DepartmentName, Details, Quantity, Cost);

                Toast.makeText(UpdateItem.this, "Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UpdateItem.this, Inventory.class)); //Creates intent and command to connect Inventory to the UserProfile screen
            }
        });

        bGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateItem.this, "Generating Details...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *
     * Methods below assist with this activity
     */

    private void initiateViews(){

        linkHome = (TextView) findViewById(R.id.tvHomeUpdate); //References Home link
        linkInventory = (TextView) findViewById(R.id.tvInventoryUpdate); //References Inventory link
        linkUpdateInventory = (TextView) findViewById(R.id.tvUpdateInventory); //References Update Inventory link


        tItemID = (TextView) findViewById(R.id.tvItemID);
        tProductName = (TextView) findViewById(R.id.tvProductName);
        tDepartment = (TextView) findViewById(R.id.tvDepartment);
        tDetails = (TextView) findViewById(R.id.tvDetails);
        tQuantity = (TextView) findViewById(R.id.tvQuantity);
        tCost = (TextView) findViewById(R.id.tvCost);

        eItemID = (EditText) findViewById(R.id.etItemID); //References Item ID field in activity
        eProductName = (EditText) findViewById(R.id.etProductName); // References Product Name field in activity
        eDepartmentName = (EditText) findViewById(R.id.etDepartment); // References Department name field in activity
        eDetails = (EditText) findViewById(R.id.etDetails); // References Quantity field in activity
        eQuantity = (EditText) findViewById(R.id.etQuantity); // References Quantity field in activity
        eCost = (EditText) findViewById(R.id.etCost); // References cost field in activity

        bUpdate = (Button) findViewById(R.id.btnUpdate); //References Update Inventory button
        bGenerate = (Button) findViewById(R.id.btnGenerate); // References the Generate button in activity

        inputlayout_ItemID = (TextInputLayout) findViewById(R.id.inputlayout_ItemID); //References inputlayout for product name field
        inputlayout_ProductName = (TextInputLayout) findViewById(R.id.inputlayout_ProductName); //References inputlayout for product name field
        inputlayout_Department = (TextInputLayout) findViewById(R.id.inputlayout_Department); //References inputlayout for department field
        inputlayout_Details = (TextInputLayout) findViewById(R.id.inputlayout_Details); //References inputlayout for details field
        inputlayout_Quantity = (TextInputLayout) findViewById(R.id.inputlayout_Quantity); //References inputlayout for quantity field
        inputlayout_Cost = (TextInputLayout) findViewById(R.id.inputlayout_Cost); //References inputlayout for cost field

    }

    private void initiateOnClickLinks(){
        linkHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateItem.this, MainActivity.class)); //Creates intent and command to connect Login to the Home screen
            }
        });

        linkInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateItem.this, Inventory.class)); //Creates intent and command to connect Login to the Home screen
            }
        });

        linkUpdateInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateItem.this, UpdateItem.class)); //Creates intent and command to connect Login to the Home screen
            }
        });

    }

}