package com.example.acme_project3;

public class User {
    private int id;
    private String username;
    private String password;

    /*Functions below are what will retrieve user credentials*/

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() { //Retrieves user ID
        return id;
    }

    public void setId(int id) { //Sets the user ID
        this.id = id;
    }

    public String getUsername() { //Retrieves the name of the user
        return username;
    }

    public void setUsername(String name) { //Sets the name of the user
        this.username = name;
    }


    public String getPassword() { //Retrieves the password of the user
        return password;
    }

    public void setPassword(String password) { //Sets the password of the user
        this.password = password;
    }
}
