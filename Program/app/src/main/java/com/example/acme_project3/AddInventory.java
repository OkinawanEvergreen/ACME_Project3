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

import java.util.List;

public class AddInventory extends AppCompatActivity {

    private EditText eItemID, eProductName, eDepartmentName, eDetails, eQuantity, eCost; //Creates instances of EditText fields
    private TextInputLayout inputlayout_ItemID, inputlayout_ProductName, inputlayout_Department,
            inputlayout_Details, inputlayout_Quantity, inputlayout_Cost; //Creates instances of these Text Input Layouts
    private Button bAddInventory; //Creates button instance of Add Inventory button
    private TextView linkHome, linkInventory, linkAddInventory; //Creates instance for textview links
    private int ListID = 0;

    private DatabaseHelper databaseHelper; //Labels the database object as database helper
    private Item item; //Labels the User object as user
    private final AppCompatActivity activity = AddInventory.this; //Establishes activity under Registration

    //Coding below is standard coding when Android Mobile Apps are created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);
        //End of standard coding

        initiateViews();
        initiateOnClickLinks();

        databaseHelper = new DatabaseHelper(activity);

        bAddInventory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String itemID = eItemID.getText().toString();
                String ProductName = eProductName.getText().toString();
                String DepartmentName = eDepartmentName.getText().toString();
                String Details = eDetails.getText().toString();
                String Quantity = eQuantity.getText().toString();
                String c = eCost.getText().toString();
                Double Cost = Double.parseDouble(c);

                boolean usedProductName = databaseHelper.checkProductName(ProductName);

                if (validProductName() && validDepartment() && validDetails() && validQuantity() && validCost()){

                    if (usedProductName){
                        Toast.makeText(AddInventory.this, "ERROR. Product already exists. Change Product Name.", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        List<Item> tempItemList = databaseHelper.getAllItems();
                        int tempListSize = tempItemList.size();

                        ListID = tempListSize++;

                        item = new Item(ListID, itemID,ProductName,DepartmentName,Details,Quantity,Cost);

                        databaseHelper.addItem(item);

                        //The below creates a message saying that the they were added successfully.
                        Toast.makeText(AddInventory.this, "Item: " + ProductName + " successfully added to Inventory", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(AddInventory.this, Inventory.class));
                    }
                }else{
                    Toast.makeText(AddInventory.this, "Did not pass validation", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     *
     * Methods below assist with the Add Inventory activity
     */

    private void initiateViews(){
        eItemID = (EditText) findViewById(R.id.etItemID); // References Unique Item ID field in activity
        eProductName = (EditText) findViewById(R.id.etProductName); // References Product Name field in activity
        eDepartmentName = (EditText) findViewById(R.id.etDepartment); // References Department name field in activity
        eDetails = (EditText) findViewById(R.id.etDetails); // References Quantity field in activity
        eQuantity = (EditText) findViewById(R.id.etQuantity); // References Quantity field in activity
        eCost = (EditText) findViewById(R.id.etCost); // References cost field in activity
        bAddInventory = (Button) findViewById(R.id.btnAddInventory); //References Add Inventory button

        inputlayout_ItemID = (TextInputLayout) findViewById(R.id.inputlayout_ItemID); //References inputlayout for product name field
        inputlayout_ProductName = (TextInputLayout) findViewById(R.id.inputlayout_ProductName); //References inputlayout for product name field
        inputlayout_Department = (TextInputLayout) findViewById(R.id.inputlayout_Department); //References inputlayout for department field
        inputlayout_Details = (TextInputLayout) findViewById(R.id.inputlayout_Details); //References inputlayout for details field
        inputlayout_Quantity = (TextInputLayout) findViewById(R.id.inputlayout_Quantity); //References inputlayout for quantity field
        inputlayout_Cost = (TextInputLayout) findViewById(R.id.inputlayout_Cost); //References inputlayout for cost field

        linkHome = (TextView) findViewById(R.id.tvHome); //References Home link
        linkInventory = (TextView) findViewById(R.id.tvInventory); //References Inventory link
        linkAddInventory = (TextView) findViewById(R.id.tvAddInventory); //References Add Inventory link
    }

    private void initiateOnClickLinks(){
        linkHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddInventory.this, MainActivity.class)); //Creates intent and command to connect Login to the Home screen
            }
        });

        linkInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddInventory.this, Inventory.class)); //Creates intent and command to connect Login to the Home screen
            }
        });

        linkAddInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddInventory.this, AddInventory.class)); //Creates intent and command to connect Login to the Home screen
            }
        });

    }

    private boolean validProductName(){
        if (eProductName.getText().toString().trim().isEmpty()) {
            inputlayout_ProductName.setErrorEnabled(true);
            inputlayout_ProductName.setError("Please enter a product name.");
            return false;
        }
        inputlayout_ProductName.setErrorEnabled(false);
        return true;
    }

    private boolean validDepartment(){
        if (eDepartmentName.getText().toString().trim().isEmpty()) {
            inputlayout_Department.setErrorEnabled(true);
            inputlayout_Department.setError("Please enter a department.");
            return false;
        }

        inputlayout_Department.setErrorEnabled(false);
        return true;
    }

    private boolean validDetails(){
        if (eDetails.getText().toString().trim().isEmpty()) {
            inputlayout_Details.setErrorEnabled(true);
            inputlayout_Details.setError("Please enter details of the product.");
            return false;
        }

        inputlayout_Details.setErrorEnabled(false);
        return true;
    }

    private boolean validQuantity(){
        if (eQuantity.getText().toString().trim().isEmpty()) {
            inputlayout_Quantity.setErrorEnabled(true);
            inputlayout_Quantity.setError("Please enter product quantity.");
            return false;
        }

        inputlayout_Quantity.setErrorEnabled(false);
        return true;
    }

    private boolean validCost(){
        try {
            String c = eCost.getText().toString();
            Double Cost = Double.parseDouble(c);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

}