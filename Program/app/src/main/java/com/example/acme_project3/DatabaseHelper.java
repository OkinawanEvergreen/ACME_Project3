package com.example.acme_project3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * User table in SQL Database
     */

    private static final int DATABASE_VERSION = 1; // Database Version
    private static final String DATABASE_NAME = "Project 3 Database.db"; // Database Name
    private static final String TABLE_USER = "user"; // User table name

    // Below are the User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    // Creates the SQL query for the table
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT" + ")";

    // Drops the table SQL query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;


    //For Adapter
    List<User> usersList = new ArrayList<>();

    /**
     * Inventory table in SQL Database
     */

    private static final String TABLE_INVENTORY = "inventory";

    // Below are the Inventory Table Columns names
    private static final String COLUMN_ITEM_LISTID = "item_listid";
    private static final String COLUMN_ITEM_ID = "item_id";
    private static final String COLUMN_ITEM_PRODUCTNAME = "item_productname";
    private static final String COLUMN_ITEM_DEPARTMENT = "item_department";
    private static final String COLUMN_ITEM_DETAILS = "item_details";
    private static final String COLUMN_ITEM_QUANTITY = "item_quantity";
    private static final String COLUMN_ITEM_COST = "item_cost";

    // Creates the SQL query for the table
    private String CREATE_INVENTORY_TABLE = "CREATE TABLE " + TABLE_INVENTORY + "("
            + COLUMN_ITEM_LISTID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_ITEM_ID + " TEXT,"
            + COLUMN_ITEM_PRODUCTNAME + " TEXT,"
            + COLUMN_ITEM_DEPARTMENT + " TEXT,"
            + COLUMN_ITEM_DETAILS + " TEXT,"
            + COLUMN_ITEM_QUANTITY + " TEXT,"
            + COLUMN_ITEM_COST + " REAL" + ")";

    // Drops the table SQL query
    private String DROP_INVENTORY_TABLE = "DROP TABLE IF EXISTS " + TABLE_INVENTORY;


    //For Adapter
    List<Item> itemsList = new ArrayList<>();


   /**Below are the Functions within this Database class**/

    public DatabaseHelper(Context context) { //Establishes the database name and version
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //Creates the database user table
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_INVENTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_INVENTORY_TABLE);
        // Create tables again for the new version
        onCreate(db);
    }


    public List<User> getAllUsers(){
        String columns[] = {COLUMN_USER_ID,COLUMN_USER_NAME, COLUMN_USER_PASSWORD};

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, //Tables the table_user query
                columns,         //columns to return
                null,       //selected columns
                null,   //selected arguments
                null,   //group the rows - does not need rows
                null,    //filter by row groups - does not need groups
                null);  //The sort order - does not need a sort order


        while(cursor.moveToNext()){
            int index1 = cursor.getColumnIndex(COLUMN_USER_ID);
            int rowid = cursor.getInt(index1);

            int index2 = cursor.getColumnIndex(COLUMN_USER_NAME);
            String personUsername = cursor.getString(index2);

            int index3 = cursor.getColumnIndex(COLUMN_USER_PASSWORD);
            String password = cursor.getString(index3);

            User user = new User(rowid, personUsername, password);
            usersList.add(user);
        }
        return usersList;
    }
    public void addUser(User user) { //Creates the user records
        SQLiteDatabase db = this.getWritableDatabase(); //Retrieves the database; references the readable and writable database into SQLiteDatabase
        ContentValues valuesUser = new ContentValues(); //Creates instance of ContentValues class that is used to store a set of values
        valuesUser.put(COLUMN_USER_ID, user.getId());
        valuesUser.put(COLUMN_USER_NAME, user.getUsername()); //Puts the user's name under the column user name
        valuesUser.put(COLUMN_USER_PASSWORD, user.getPassword()); //Puts the user's password under the column user password
        db.insert(TABLE_USER, null, valuesUser); //Inserts another row with empty values for another user
        db.close(); //Closes the database so it is not running on next instance
    }

    public boolean updateUserpassword(String username, String newPassword) { //Method is used to update a user's credentials
        SQLiteDatabase db = this.getWritableDatabase(); //Calls the database
        ContentValues valuesUser = new ContentValues(); //Creates new instance of content values
        valuesUser.put(COLUMN_USER_NAME, username); //Places the new user name into the new content values
        valuesUser.put(COLUMN_USER_PASSWORD, newPassword); //Places the new password into the new content values
        // Below is used to update the row
        db.update(TABLE_USER, valuesUser, "user_name = ?", new String[] {username});
        db.close(); //Closes the database
        return true;
    }

    public boolean updateUser(String username, String newPassword) { //Method is used to update a user's credentials
        SQLiteDatabase db = this.getWritableDatabase(); //Calls the database
        ContentValues valuesUser = new ContentValues(); //Creates new instance of content values
        valuesUser.put(COLUMN_USER_NAME, username); //Places the new user name into the new content values
        valuesUser.put(COLUMN_USER_PASSWORD, newPassword); //Places the new password into the new content values
        // Below is used to update the row
        db.update(TABLE_USER, valuesUser, "user_name = ?", new String[] {username});
        db.close(); //Closes the database
        return true;
    }

    public void deleteUser(int id) { //This method is to delete a user record
        SQLiteDatabase db = this.getWritableDatabase(); //Calls the database
        // The below commands the table to remove/delete user record by id provided
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close(); //Closes the database
    }


    public boolean checkUsername(String username) { //This checks if the user exists or not

        String[] columns = { //Retrieves the array column of the user ID
                COLUMN_USER_NAME
        };
        SQLiteDatabase db = this.getReadableDatabase(); //Calls the database

        //Below is the selection requirements
        String selection = COLUMN_USER_NAME + " = ?";

        String[] selectionArgs = {username}; //Selection arguments


        // The below retrieves the records and functions like a SQL query under the above conditions.
        // The SQL query would be: SELECT user_name where user_name '(insert username)'

        Cursor cursor = db.query(TABLE_USER, //Tables the table_user query
                columns,         //columns to return
                selection,       //columns for the WHERE clause
                selectionArgs,   //The values for the WHERE clause
                null,   //group the rows - does not need rows
                null,    //filter by row groups - does not need groups
                null);  //The sort order - does not need a sort order

        int cursorCount = cursor.getCount(); //Checks to see how many hits it gets from the query table
        cursor.close(); //Closes the cursor
        db.close(); //Closes the database

        if (cursorCount > 0) { //If there was at least one hit from the query table, then true
            return true;
        }
        return false; //Otherwise, there was no one with that username
    }


    public boolean checkPassword(String username, String currentPassword, String newPassword) { //This checks if the user exists or not

        String[] columns = { //Retrieves the array column of the user ID
                COLUMN_USER_NAME
        };
        SQLiteDatabase db = this.getReadableDatabase(); //Calls the database

        //Below is the selection requirements
        String selection = COLUMN_USER_NAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        String[] selectionArgs = {username, currentPassword}; //Selection arguments


        // The below retrieves the records and functions like a SQL query under the above conditions.
        // The SQL query would be: SELECT user_name where user_name '(insert username)'

        Cursor cursor = db.query(TABLE_USER, //Tables the table_user query
                columns,         //columns to return
                selection,       //columns for the WHERE clause
                selectionArgs,   //The values for the WHERE clause
                null,   //group the rows - does not need rows
                null,    //filter by row groups - does not need groups
                null);  //The sort order - does not need a sort order

        int cursorCount = cursor.getCount(); //Checks to see how many hits it gets from the query table
        cursor.close(); //Closes the cursor
        db.close(); //Closes the database

        if (cursorCount > 0 && (!currentPassword.equals(newPassword))) { //If there was at least one hit from the query table, then true
            return true;
        }
        return false; //Otherwise, current password was not associated with username or password,
                      // or current password matched the newPassword
    }

    public boolean checkCredentials(String username, String currentPassword) { //This checks if the user exists or not

        String[] columns = { //Retrieves the array column of the user ID
                COLUMN_USER_NAME
        };
        SQLiteDatabase db = this.getReadableDatabase(); //Calls the database

        //Below is the selection requirements
        String selection = COLUMN_USER_NAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        String[] selectionArgs = {username, currentPassword}; //Selection arguments


        // The below retrieves the records and functions like a SQL query under the above conditions.
        // The SQL query would be: SELECT user_name where user_name '(insert username)'

        Cursor cursor = db.query(TABLE_USER, //Tables the table_user query
                columns,         //columns to return
                selection,       //columns for the WHERE clause
                selectionArgs,   //The values for the WHERE clause
                null,   //group the rows - does not need rows
                null,    //filter by row groups - does not need groups
                null);  //The sort order - does not need a sort order

        int cursorCount = cursor.getCount(); //Checks to see how many hits it gets from the query table
        cursor.close(); //Closes the cursor
        db.close(); //Closes the database

        if (cursorCount > 0) { //If there was at least one hit from the query table, then true
            return true;
        }
        return false; //Otherwise, invalid credentials
    }


    public List<Item> getAllItems(){
        String columns[] = {COLUMN_ITEM_LISTID, COLUMN_ITEM_ID, COLUMN_ITEM_PRODUCTNAME, COLUMN_ITEM_DEPARTMENT,
                COLUMN_ITEM_DETAILS, COLUMN_ITEM_QUANTITY, COLUMN_ITEM_COST};

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_INVENTORY, //Tables the table_user query
                columns,         //columns to return
                null,       //selected columns
                null,   //selected arguments
                null,   //group the rows - does not need rows
                null,    //filter by row groups - does not need groups
                null);  //The sort order - does not need a sort order


        while(cursor.moveToNext()){
            int index0 = cursor.getColumnIndex(COLUMN_ITEM_LISTID);
            int item_listID = cursor.getInt(index0);

            int index1 = cursor.getColumnIndex(COLUMN_ITEM_ID);
            String item_ID = cursor.getString(index1);

            int index2 = cursor.getColumnIndex(COLUMN_ITEM_PRODUCTNAME);
            String item_productname = cursor.getString(index2);

            int index3 = cursor.getColumnIndex(COLUMN_ITEM_DEPARTMENT);
            String item_department = cursor.getString(index3);

            int index4 = cursor.getColumnIndex(COLUMN_ITEM_DETAILS);
            String item_details = cursor.getString(index4);

            int index5 = cursor.getColumnIndex(COLUMN_ITEM_QUANTITY);
            String item_quantity = cursor.getString(index5);

            int index6 = cursor.getColumnIndex(COLUMN_ITEM_COST);
            Double item_cost = cursor.getDouble(index6);

            Item item = new Item(item_listID, item_ID, item_productname, item_department,
                    item_details, item_quantity, item_cost);

            itemsList.add(item);
        }
        return itemsList;
    }

    public void addItem(Item item) { //Creates the item records
        SQLiteDatabase db = this.getWritableDatabase(); //Retrieves the database; references the readable and writable database into SQLiteDatabase
        ContentValues valuesItem = new ContentValues(); //Creates instance of ContentValues class that is used to store a set of values
        valuesItem.put(COLUMN_ITEM_LISTID, item.getListID()); //
        valuesItem.put(COLUMN_ITEM_ID, item.getID()); //
        valuesItem.put(COLUMN_ITEM_PRODUCTNAME, item.getProductName()); //
        valuesItem.put(COLUMN_ITEM_DEPARTMENT, item.getDepartment()); //
        valuesItem.put(COLUMN_ITEM_DETAILS, item.getDetails()); //
        valuesItem.put(COLUMN_ITEM_QUANTITY, item.getQuantity()); //
        valuesItem.put(COLUMN_ITEM_COST, item.getCost()); //
        db.insert(TABLE_INVENTORY, null, valuesItem); //Inserts another row with empty values for another user
        db.close(); //Closes the database so it is not running on next instance
    }

    public boolean updateItem(String itemID, String productname, String department, String details, String quantity, Double cost) { //Method is used to update a user's credentials
        SQLiteDatabase db = this.getWritableDatabase(); //Calls the database
        ContentValues valuesItem = new ContentValues(); //Creates new instance of content values
        valuesItem.put(COLUMN_ITEM_ID, itemID); //
        valuesItem.put(COLUMN_ITEM_PRODUCTNAME, productname); //
        valuesItem.put(COLUMN_ITEM_DEPARTMENT, department); //
        valuesItem.put(COLUMN_ITEM_DETAILS, details); //
        valuesItem.put(COLUMN_ITEM_QUANTITY, quantity); //
        valuesItem.put(COLUMN_ITEM_COST, cost); //
        // Below is used to update the row
        db.update(TABLE_INVENTORY, valuesItem, "item_id = ?", new String[] {itemID});
        db.close(); //Closes the database
        return true;
    }

    public void deleteItem(int listid) { //This method is to delete a user record
        SQLiteDatabase db = this.getWritableDatabase(); //Calls the database
        // The below commands the table to remove/delete user record by id provided
        db.delete(TABLE_INVENTORY, COLUMN_ITEM_LISTID + " = ?",
                new String[]{String.valueOf(listid)});
        db.close(); //Closes the database
    }

    public boolean checkSKU(String sku) { //This checks if the user exists or not

        String[] columns = { //Retrieves the array column of the user ID
                COLUMN_ITEM_ID
        };
        SQLiteDatabase db = this.getReadableDatabase(); //Calls the database

        //Below is the selection requirements
        String selection = COLUMN_ITEM_ID + " = ?";

        String[] selectionArgs = {sku}; //Selection arguments


        // The below retrieves the records and functions like a SQL query under the above conditions.
        // The SQL query would be: SELECT user_name where user_name '(insert username)'

        Cursor cursor = db.query(TABLE_INVENTORY, //Tables the table_user query
                columns,         //columns to return
                selection,       //columns for the WHERE clause
                selectionArgs,   //The values for the WHERE clause
                null,   //group the rows - does not need rows
                null,    //filter by row groups - does not need groups
                null);  //The sort order - does not need a sort order

        int cursorCount = cursor.getCount(); //Checks to see how many hits it gets from the query table
        cursor.close(); //Closes the cursor
        db.close(); //Closes the database

        if (cursorCount > 0) { //If there was at least one hit from the query table, then true
            return true;
        }
        return false; //Otherwise, there was no one with that username
    }

    public boolean checkProductName(String productname) { //This checks if the user exists or not

        String[] columns = { //Retrieves the array column of the user ID
                COLUMN_ITEM_PRODUCTNAME
        };
        SQLiteDatabase db = this.getReadableDatabase(); //Calls the database

        //Below is the selection requirements
        String selection = COLUMN_ITEM_PRODUCTNAME + " = ?";

        String[] selectionArgs = {productname}; //Selection arguments


        // The below retrieves the records and functions like a SQL query under the above conditions.
        // The SQL query would be: SELECT user_name where user_name '(insert username)'

        Cursor cursor = db.query(TABLE_INVENTORY, //Tables the table_user query
                columns,         //columns to return
                selection,       //columns for the WHERE clause
                selectionArgs,   //The values for the WHERE clause
                null,   //group the rows - does not need rows
                null,    //filter by row groups - does not need groups
                null);  //The sort order - does not need a sort order

        int cursorCount = cursor.getCount(); //Checks to see how many hits it gets from the query table
        cursor.close(); //Closes the cursor
        db.close(); //Closes the database

        if (cursorCount > 0) { //If there was at least one hit from the query table, then true
            return true;
        }
        return false; //Otherwise, there was no one with that username
    }

}
