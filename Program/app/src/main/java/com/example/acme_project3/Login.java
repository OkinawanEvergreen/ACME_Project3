package com.example.acme_project3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Map;

public class Login extends AppCompatActivity {

    private EditText eUsername, ePassword; //Creates instance of EditText for the eUsername and ePassword
    private TextInputLayout inputlayout_LogUsername, inputlayout_LogPassword; //Creates instances of these Text Input Layouts
    private Button eLogin; //Creates instance of Button for the eLogin
    private TextView linkHome, linkLogin, linkRegister; //Creates instance of TextView for eRegister
    private CheckBox eRememberMe; //Creates instance of CheckBox for eRememberMe

    boolean isValid = false; //Setting the validation as false initially since we do not want to provide automatic access

    public Credentials credentials; //Accesses the class "Credentials"

    private DatabaseHelper databaseHelper; //Labels the database object as database helper
    private final AppCompatActivity activity = Login.this; //Initiates activity for this Login class

    /*Class below that will ensure there is a file stored on the phone to store all registration credentials
        so that it is not erased once the mobile session ends*/
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    //Coding below is standard coding when Android Mobile Apps are created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //End of standard coding

        initiateViews(); //Initiates view
        initiateOnClickLinks(); //Initiates text view links only

        credentials = new Credentials();//Initiates the credentials class
        databaseHelper = new DatabaseHelper(activity); //Initiates the databasehelper class

        sharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDB", MODE_PRIVATE);
            //Above allows you to get the shared preferences in a file name called "CredentialsDB" in private mode
        sharedPreferencesEditor = sharedPreferences.edit(); //Initiates the ability to edit shared preferences

        if (sharedPreferences != null){ //Checks to see if the sharedPreferences file exists

            Map<String, ?> preferencesMap = sharedPreferences.getAll(); //Retrieving the map from the sharedPreferences file

            if(preferencesMap.size() != 0){ //Checks to see if the preferencesMap has data. If it is NOT empty, then continues
                credentials.loadCredentials(preferencesMap); //Passes the preferences map into the loadCredentials function
            }

            String savedUsername = sharedPreferences.getString("LastSavedUsername", ""); //If it does exist, creates a key value pair of Username and null
            String savedPassword = sharedPreferences.getString("LastSavedPassword","");  //If it does exist, creates a key value pair of Password and null

            if(sharedPreferences.getBoolean("RememberMeCheckBox", false)){ //Checks to see if remember me check box was clicked. Sets it as true if it was and continues
                eUsername.setText(savedUsername); //sets the text found in eUsername as the 'savedUsername'
                ePassword.setText(savedPassword); //sets the text found in ePassword as the 'savedPassword'
                eRememberMe.setChecked(true); //changing this to true
            }
        }

        eRememberMe.setOnClickListener(new View.OnClickListener() { //Commands what happens when clicking the Remember Me? checkbox
            @Override
            public void onClick(View v) {
                sharedPreferencesEditor.putBoolean("RememberMeCheckBox",eRememberMe.isChecked()); //Once users click on login, app will look to see if Remember Me? checkbox is checked.
                sharedPreferencesEditor.apply();//Commits the changes above
            }
        });


        linkRegister.setOnClickListener(new View.OnClickListener() { //Commands what happens when clicking the "New User? Register here" TextView link
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Login.this, Registration.class)); //Creates intent and command to connect Login to the Registration screen
            }
        });


        eLogin.setOnClickListener(new View.OnClickListener() { //Commands what happens when clicking Login button
            @Override
            public void onClick(View v) {

                String input_username = eUsername.getText().toString(); //Converts the app input in the eUsername to a string
                String input_password = ePassword.getText().toString(); //Converts the app input in the ePassword to a string

                if (validUsername() && validPassword()) //Checks to see if the username or password field is empty
                {
                    //isValid = validate(input_username, input_password); //stores the result of the validation function (which will return true or false) into boolean isValid
                    isValid = databaseHelper.checkCredentials(input_username, input_password);

                    if (!isValid) { //if validation failed which makes isValid is false
                        //The below creates an error message saying that the input is empty/incorrect and to re-enter the credentials.
                        Toast.makeText(Login.this, "Incorrect credentials entered.", Toast.LENGTH_SHORT).show();

                    } else { //If validation did NOT fail and login credentials are correct
                        //The below pops a message stating that Login was successful.
                        Toast.makeText(Login.this, "Login was successful!", Toast.LENGTH_SHORT).show();

                        sharedPreferencesEditor.putString("LastSavedUsername", input_username);
                        sharedPreferencesEditor.putString("LastSavedPassword", input_password);
                        sharedPreferencesEditor.apply(); //Commits the changes above


                        startActivity(new Intent(Login.this, Inventory.class)); //Creates intent and command to connect Login to the Inventory screen
                    }
                }
            }
        });
    }

    private void initiateViews(){
        eUsername = (EditText) findViewById(R.id.etLogUsername); // References Login_EditText_username field in activity_login.xml
        ePassword = (EditText) findViewById(R.id.etLogPassword); // References Login_EditText_Password field in activity_login.xml
        eLogin = (Button) findViewById(R.id.btnLogin); //References Login_Button_Login button in activity_login.xml
        linkHome = (TextView) findViewById(R.id.tvHome); //References Link_RegisterHere in activity_login.xml
        linkLogin = (TextView) findViewById(R.id.tvLogin); //References Link_RegisterHere in activity_login.xml
        linkRegister = (TextView) findViewById(R.id.Link_RegisterHere); //References Link_RegisterHere in activity_login.xml

        eRememberMe = (CheckBox) findViewById(R.id.cbRememberMe); //References cbRememberMe in activity_login.xml

        inputlayout_LogUsername = (TextInputLayout) findViewById(R.id.inputlayout_LogUsername); //References inputlayout for username field
        inputlayout_LogPassword = (TextInputLayout) findViewById(R.id.inputlayout_LogPassword); //References inputlayout for password field
    }

    private void initiateOnClickLinks(){
        linkHome.setOnClickListener(new View.OnClickListener() { //Commands what happens when clicking the "Home" text view
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, MainActivity.class)); //Creates intent and command to connect Login to the Home screen
            }
        });

        linkLogin.setOnClickListener(new View.OnClickListener() { //Commands what happens when clicking the "Home" text view
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Login.class)); //Creates intent and command to connect Login to the Home screen
            }
        });

    }

    private boolean validUsername(){
        if (eUsername.getText().toString().trim().isEmpty()) {
            inputlayout_LogUsername.setErrorEnabled(true);
            inputlayout_LogUsername.setError("Please enter a username.");
            return false;
        }
        inputlayout_LogUsername.setErrorEnabled(false);
        return true;
    }

    private boolean validPassword(){
        if (ePassword.getText().toString().trim().isEmpty()) {
            inputlayout_LogPassword.setErrorEnabled(true);
            inputlayout_LogPassword.setError("Please enter a password.");
            return false;
        }

        inputlayout_LogPassword.setErrorEnabled(false);
        return true;
    }

}