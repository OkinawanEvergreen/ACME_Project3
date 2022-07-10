package com.example.acme_project3;

import java.util.HashMap;
import java.util.Map;

public class Credentials {

    HashMap<String, String> credentialsMapper = new HashMap<String, String>(); //Creates a HashMap with 2 strings for username and password

    public void addCredentials(String username, String password){ //Function that adds credentials to the HashMap created above
        credentialsMapper.put(username, password); //Command to put the credentials of username and password into the HashMap
    }

    public boolean checkUsername(String username){ //Function that checks to make sure the username is not already in use
        return credentialsMapper.containsKey(username); //Looks through the HashMap and detects if the username already exists
    }

    public boolean verifyCredentials(String username, String password){ //Function to verify the credentials

        if(credentialsMapper.containsKey(username)){ //Checks if the username in the HashMap exists
            if(password.equals(credentialsMapper.get(username))){ //Checks to see if password matches
                return true; //If the password matches, then return true
            }
        }
        return false; //If credentials do not match, return false
    }

    public void loadCredentials(Map<String, ?> preferencesMap){
        for(Map.Entry<String, ?> entries : preferencesMap.entrySet()){ //loops through each of the entries in the preferences map
            if(!entries.getKey().equals("RememberMeCheckBox")){ //If the entries are not entered in the Remember Me Check Box
                credentialsMapper.put(entries.getKey(), entries.getValue().toString());
            }
        }
    }

    public boolean verifyPasswords(String password, String newPassword) { //Function to verify passwords

        if(credentialsMapper.containsKey(password)) {
            if (newPassword.equals(credentialsMapper.get(password))) {
                return false;
            }
        }

        return true;
    }


        /*if (credentialsMapper.containsKey(password)) { //Checks if the currentPassword in the HashMap exists
            if (newPassword.equals(credentialsMapper.get(password))) { //Checks to see if new Password matches current password
                return false; //returns false if  matching since it should not be updated
            }
        }
        return true; //Passwords should  be updated
    }*/

    public void setCredentialsMapper (String newPassword){ //Function set new password

    }



} //End of class

