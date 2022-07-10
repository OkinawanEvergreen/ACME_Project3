package com.example.acme_project3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class Inventory extends AppCompatActivity {

    private ImageButton eUserProfile;
    private Button bAddInventory, bUpdateInventory, bDelete;
    private TextView linkHome, linkInventory;

    private AppCompatActivity activity = Inventory.this;
    private AppCompatTextView textViewName;
    //private RecyclerView recyclerViewItems;
    private DatabaseHelper databaseHelper;

    private RecyclerView rvInventory;
    ItemsAdapter itemsAdapter;
    RecyclerView.LayoutManager layoutManager;
    private List<Item> itemsList = new ArrayList<>();


    /*Class below that will ensure there is a file stored on the phone to store all registration credentials
        so that it is not erased once the mobile session ends*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        //End of standard coding

        databaseHelper = new DatabaseHelper(Inventory.this);


        itemsList = databaseHelper.getAllItems();
        rvInventory = findViewById(R.id.rvInventory);
        rvInventory.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(Inventory.this);
        rvInventory.setLayoutManager(layoutManager);

        itemsAdapter = new ItemsAdapter(Inventory.this, itemsList, rvInventory);
        rvInventory.setAdapter(itemsAdapter); //changed from usersAdapter to itemsAdapter
        new ItemTouchHelper(itemTouchHelperCallback_SwipeRight).attachToRecyclerView(rvInventory);

        initiateViews();
        initiateOnClickLinks();
    }

    /**
     * Methods that help this Inventory class
     */
    private void initiateViews(){ //Methods to initialize views
        eUserProfile = (ImageButton) findViewById(R.id.ib_UserProfile_Inventory); //References Userprofile image button in activity_inventory.xml
        bAddInventory = (Button) findViewById(R.id.btnAddInventory); //References Add Inventory button in activity_inventory.xml
        bUpdateInventory = (Button) findViewById(R.id.btnUpdateInventory2); //References Add Inventory button in activity_inventory.xml
        bDelete = (Button) findViewById(R.id.btnDelete); //References Add Inventory button in activity_inventory.xml

        linkHome = (TextView) findViewById(R.id.link_Home);
        linkInventory = (TextView) findViewById(R.id.link_Inventory);
    }

    private void initiateOnClickLinks(){
        eUserProfile.setOnClickListener(new View.OnClickListener() { //Commands what happens when clicking the User profile image button
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Inventory.this, UserProfile.class)); //Creates intent and command to connect Inventory to the UserProfile screen
            }
        });

        bUpdateInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Inventory.this, "BEFORE Update button clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Inventory.this, UpdateItem.class)); //Creates intent to command and connect to UpdateInventory screen
                Toast.makeText(Inventory.this, "AFTER Update button clicked", Toast.LENGTH_SHORT).show();
            }
        });

        bAddInventory.setOnClickListener(new View.OnClickListener() { //Commands what happens when clicking the Add Inventory button
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Inventory.this, AddInventory.class)); //Creates intent and command to connect Inventory to the AddInventory screen
            }
        });

        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Inventory.this, "To delete, swipe item to the right.", Toast.LENGTH_SHORT).show();
            }
        });

        linkHome.setOnClickListener(new View.OnClickListener() { //Commands what happens when clicking the Add Inventory button
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Inventory.this, MainActivity.class)); //Creates intent and command to connect Inventory to the AddInventory screen
            }
        });

        linkInventory.setOnClickListener(new View.OnClickListener() { //Commands what happens when clicking the Add Inventory button
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Inventory.this, Inventory.class)); //Creates intent and command to connect Inventory to the AddInventory screen
            }
        });

    }



        ItemTouchHelper.SimpleCallback itemTouchHelperCallback_SwipeRight = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {//Uses the ability to swipe to the right and delete items from the inventory list
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) { //Commands what happens once the swipe command is completed

            int position = viewHolder.getAdapterPosition(); //Retrieves position of item on adapter
            int item_listID = itemsList.get(position).getListID(); //Looks for the position and retrieves the specific SKU ID

            itemsList.remove(viewHolder.getAdapterPosition()); //Removes item from the adapter
            databaseHelper.deleteItem(item_listID);
            itemsAdapter.notifyDataSetChanged();

        }
    };


    /*ItemTouchHelper.SimpleCallback itemTouchHelperCallback_SwipeLeft = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){//Uses the ability to swipe to the right and delete items from the inventory list
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) { //Commands what happens once the swipe command is completed

            int position = viewHolder.getAdapterPosition(); //Retrieves position of item on adapter
            String item_USERNAME = usersList.get(position).getUsername(); //
            String item_PASSWORD = usersList.get(position).getPassword(); //


            databaseHelper.updateUser(item_USERNAME, item_PASSWORD); //
            usersAdapter.notifyDataSetChanged(); //Notifies the adapter of the change to reflect on screen
        }
    };*/

   }