package com.example.acme_project3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserProfile extends AppCompatActivity {

    public Credentials credentials;
    private TextView eCurrentUsername;
    private EditText eCurrentPassword;
    private EditText eNewPassword;
    private Button eSave;
    boolean isValidPassword = false;
    private User user; //Labels the User object as user
    private DatabaseHelper databaseHelper; //Labels the database object as database helper
    private final AppCompatActivity activity = UserProfile.this; //Establishes activity under Registration



    /*Class below that will ensure there is a file stored on the phone to store all registration credentials
    so that it is not erased once the mobile session ends*/
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor; //Allows the ability to edit the shared preferences


    //Coding below is standard coding when Android Mobile Apps are created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        //End of standard coding


        eCurrentUsername = (TextView) findViewById(R.id.tv_up_Currentusername); // References Text View Current username field in activity_Userprofile.xml
        eCurrentPassword = (EditText) findViewById(R.id.ptCurrentPassword2); // References Edit Text current password field in activity_Userprofile.xml
        eNewPassword = (EditText) findViewById(R.id.ptNewPassword2); // References Edit Text new password field in activity_Userprofile.xml
        eSave = (Button) findViewById(R.id.Button_Save); //References Button Save in activity_Userprofile.xml


        credentials = new Credentials();//Initiates the credentials class
        sharedPreferences = getApplicationContext().getSharedPreferences( "CredentialsDB",MODE_PRIVATE); //Pulls open the CredentialsDB file
        databaseHelper = new DatabaseHelper(activity);

        String currentUsername = sharedPreferences.getString("LastSavedUsername", ""); //Retrieves the LastSavedUsername/login username
        eCurrentUsername.setText("Welcome to your User Profile, " + currentUsername + "!"); //Returns welcome message for logged in user

        Toast.makeText(UserProfile.this, "isValidPassword: " + isValidPassword, Toast.LENGTH_SHORT).show(); //DELETE


        eSave.setOnClickListener(new View.OnClickListener() { //Commands what happens when "Save" button is clicked
            @Override
            public void onClick(View v) {

                String input_CurrentPassword = eCurrentPassword.getText().toString(); //Converts the app input in the Current Password field to a string
                String input_NewPassword = eNewPassword.getText().toString(); //Converts the app input in the New Password field to a string

                if (input_CurrentPassword.isEmpty() || input_NewPassword.isEmpty()) { //Checks to see if the current password and new password fields are empty
                    //The below creates an error message saying that the input is empty/incorrect and to re-enter the credentials.
                    Toast.makeText(UserProfile.this, "Password fields are empty. Please enter all credentials!", Toast.LENGTH_SHORT).show();

                } else {
                    //isValidPassword = validatePasswords(input_CurrentPassword, input_NewPassword); //stores the result of the validation function to determine if passwords valid


                    isValidPassword = databaseHelper.checkPassword(currentUsername, input_CurrentPassword, input_NewPassword);

                    if (!isValidPassword) { //if validation failed which makes isValidPassword false
                        //The below creates an error message saying that the input is incorrect and to re-enter the credentials.
                        Toast.makeText(UserProfile.this, "An incorrect current password was inserted or passwords are matching. Try again!", Toast.LENGTH_SHORT).show();
                        return; //returns back to the screen so the app does not crash

                    } else {//If validation did NOT fail and passwords are not matching and should be updated
                        Toast.makeText(UserProfile.this, "Passwords are valid and now updated to the New Password!", Toast.LENGTH_SHORT).show();

                        databaseHelper.updateUserpassword(currentUsername, input_NewPassword);

                        sharedPreferencesEditor.putString("LastSavedPassword", input_NewPassword); //Places the new password in the string
                        sharedPreferencesEditor.apply(); //Commits the change above

                        startActivity(new Intent(UserProfile.this, Inventory.class)); //Creates intent and command to refresh com.example.acme_project3.User Profile screen

                    }
                }
            }
        });
    }

    //Below is a validate function that will verify that the entered passwords are valid
    private boolean validatePasswords(String password, String newPassword){
        return credentials.verifyPasswords(password, newPassword); //going to take the passwords that you are passing it
    }

}