package com.example.acme_project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    //Coding below is standard coding when Android Mobile Apps are created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    //End of standard coding

        Button Button_MobileShopping = (Button) findViewById(R.id.Button_MobileShopping); //References Mobile Shopping button in Home Screen
        Button Button_InventorySystem = (Button) findViewById(R.id.Button_InventorySystem); //References Inventory System button in Home Screen
        ImageButton Icon_UserProfile = (ImageButton) findViewById(R.id.Icon_UserProfile); //References com.example.acme_project3.User Profile icon in Home Screen

        Button_InventorySystem.setOnClickListener(new View.OnClickListener() { //Commands what happens when clicking Inventory System button on Home Screen
            @Override
            public void onClick(View v) {
                Intent connect_LoginScreen = new Intent(getApplicationContext(),Login.class); //Creates intent to connect the Login screen to Inventory System
                startActivity(connect_LoginScreen); //Commands Inventory System button to connect to intent of opening Login Screen
            }
        });





    }
}