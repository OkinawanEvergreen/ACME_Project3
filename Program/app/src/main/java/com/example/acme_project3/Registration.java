package com.example.acme_project3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Map;

public class Registration extends AppCompatActivity {

    private EditText eRegUsername, eRegPassword; //Creates instances of these EditText fields
    private TextInputLayout inputlayout_RegUsername, inputlayout_RegPassword; //Creates instances of these Text Input Layouts
    private Button eRegister; //Creates instances of this button field

    public Credentials credentials; //Accesses the class "Credentials"
    private DatabaseHelper databaseHelper; //Labels the database object as database helper
    private User user; //Labels the User object as user
    private final AppCompatActivity activity = Registration.this; //Establishes activity under Registration
    int userID = 0;

    /*Class below that will ensure there is a file stored on the phone to store all registration credentials
        so that it is not erased once the mobile session ends*/
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor; //Class that allows us to edit the sharedPreferences class

    //Coding below is standard coding when Android Mobile Apps are created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //End of standard coding

        initiateViews(); //Initiates view

        credentials = new Credentials();
        databaseHelper = new DatabaseHelper(activity);

        sharedPreferences = getApplicationContext().getSharedPreferences( "CredentialsDB",MODE_PRIVATE);
            //Above allows you to get the shared preferences in a file name called "CredentialsDB" in private mode
        sharedPreferencesEditor = sharedPreferences.edit(); //Allows us to add values onto the "CredentialsDB" file in an xml format


        if (sharedPreferences != null){ //Checks to see if the sharedPreferences file exists

            Map<String, ?> preferencesMap = sharedPreferences.getAll(); //Retrieving the map from the sharedPreferences file

            if(preferencesMap.size() != 0){ //Checks to see if the preferencesMap has data. If it is NOT empty, then continues
                credentials.loadCredentials(preferencesMap); //Passes the preferences map into the loadCredentials function
            }
        }

        eRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String regUsername = eRegUsername.getText().toString(); //Grabs the text from the eRegUsername edit text field from user
                String regPassword = eRegPassword.getText().toString(); //Grabs the text from the eRegPassword edit text field from user

                if (validUsername() && validPassword()) {

                    if (credentials.checkUsername(regUsername)){ //Checks the username to see if the regUsername already exists in the Hashmap
                        //The below pops error message to let user know that the username is already taken
                        Toast.makeText(Registration.this, "Sorry. That username is already in use.", Toast.LENGTH_SHORT).show();
                    }else{
                        credentials.addCredentials(regUsername, regPassword); //Passes the regUsername and regPassword to addCredentials class to store into Hashmap

                        sharedPreferencesEditor.putString(regUsername, regPassword); //Storing the credentials into the sharedPreferences file
                        sharedPreferencesEditor.putString("LastSavedUsername",""); //Stores the Last Saved Username
                        sharedPreferencesEditor.putString("LastSavedPassword",""); //Stores the Last Saved Password
                        sharedPreferencesEditor.apply(); //Commits the changes requested above to the sharedPreferences file

                        List<User> tempUserList = databaseHelper.getAllUsers();
                        int tempListSize = tempUserList.size();

                        userID = tempListSize++;

                        user = new User(userID,regUsername,regPassword);

                        user.setUsername(regUsername); //Sets the username entered as the user's username
                        user.setPassword(regPassword); //Sets the password entered as the user's password

                        databaseHelper.addUser(user); //Adds user to the SQL database

                        startActivity(new Intent( Registration.this, Login.class)); //Creates the intent to connect the Registration screen to the Login screen

                        //The below pops a message stating that registration was successful.
                        Toast.makeText(Registration.this, "Registration for "+ user.getUsername() + " was successful!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    private void initiateViews(){
        eRegUsername = findViewById(R.id.etRegUsername); // References etRegUsername editText field in activity_registration.xml
        eRegPassword = findViewById(R.id.etRegPassword); // References etRegPassword editText field in activity_registration.xml
        eRegister = findViewById(R.id.btnRegister); // References buttonRegister button in activity_registration.xml

        inputlayout_RegUsername = (TextInputLayout) findViewById(R.id.inputlayout_RegUsername); //References inputlayout for username field
        inputlayout_RegPassword = (TextInputLayout) findViewById(R.id.inputlayout_RegPassword); //References inputlayout for password field
    }

    private boolean validUsername(){
        if (eRegUsername.getText().toString().trim().isEmpty()) {
            inputlayout_RegUsername.setErrorEnabled(true);
            inputlayout_RegUsername.setError("Please enter a username.");
            return false;
        }
        inputlayout_RegUsername.setErrorEnabled(false);
        return true;
    }

    private boolean validPassword(){
        if (eRegPassword.getText().toString().trim().isEmpty()) {
            inputlayout_RegPassword.setErrorEnabled(true);
            inputlayout_RegPassword.setError("Please enter a password.");
            return false;
        }

        if (eRegPassword.getText().toString().length() <8){
            inputlayout_RegPassword.setErrorEnabled(true);
            inputlayout_RegPassword.setError("Password must be at least 8 digits long.");
            return false;
        }

        inputlayout_RegPassword.setErrorEnabled(false);
        return true;
    }



}