package com.example.acme_project3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class InventoryDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1; // Database Version
    private static final String DATABASE_NAME = "Project 3 Database.db"; // Database Name
    private static final String TABLE_INVENTORY = "inventory"; // User table name

    // Below are the User Table Columns names
    private static final String COLUMN_ITEM_SKU = "item_sku";
    private static final String COLUMN_ITEM_PRODUCTNAME = "item_productname";
    private static final String COLUMN_ITEM_DEPARTMENT = "item_department";
    private static final String COLUMN_ITEM_DETAILS = "item_details";
    private static final String COLUMN_ITEM_QUANTITY = "item_quantity";
    private static final String COLUMN_ITEM_COST = "item_cost";

    // Creates the SQL query for the table
    private String CREATE_INVENTORY_TABLE = "CREATE TABLE " + TABLE_INVENTORY + "("
            + COLUMN_ITEM_SKU + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_ITEM_PRODUCTNAME + " TEXT,"
            + COLUMN_ITEM_DEPARTMENT + " TEXT,"
            + COLUMN_ITEM_DETAILS + " TEXT,"
            + COLUMN_ITEM_QUANTITY + " INTEGER,"
            + COLUMN_ITEM_COST + " INTEGER" + ")";

    // Drops the table SQL query
    private String DROP_INVENTORY_TABLE = "DROP TABLE IF EXISTS " + TABLE_INVENTORY;


    //For Adapter
    List<Item> itemsList = new ArrayList<>();



    /**Below are the Functions within this Database class**/



    @Override
    public void onCreate(SQLiteDatabase db) { //Creates the database inventory table
        db.execSQL(CREATE_INVENTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop Inventory Table if exist
        db.execSQL(DROP_INVENTORY_TABLE);
        // Create tables again for the new version
        onCreate(db);
    }

    public InventoryDBHelper(Context context) { //Establishes the database name and version
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public List<Item> getAllItems(){
        String columns[] = {COLUMN_ITEM_SKU, COLUMN_ITEM_PRODUCTNAME, COLUMN_ITEM_DEPARTMENT,
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
            int index1 = cursor.getColumnIndex(COLUMN_ITEM_SKU);
            int item_sku = cursor.getInt(index1);

            int index2 = cursor.getColumnIndex(COLUMN_ITEM_PRODUCTNAME);
            String item_productname = cursor.getString(index2);

            int index3 = cursor.getColumnIndex(COLUMN_ITEM_DEPARTMENT);
            String item_department = cursor.getString(index3);

            int index4 = cursor.getColumnIndex(COLUMN_ITEM_DETAILS);
            String item_details = cursor.getString(index4);

            int index5 = cursor.getColumnIndex(COLUMN_ITEM_QUANTITY);
            int item_quantity = cursor.getInt(index5);

            int index6 = cursor.getColumnIndex(COLUMN_ITEM_COST);
            float item_cost = cursor.getFloat(index6);

            Item item = new Item(item_sku, item_productname, item_department,
                    item_details, item_quantity, (int) item_cost);

            itemsList.add(item);
        }
        return itemsList;
    }

    public void addItem(Item item) { //Creates the user records
        SQLiteDatabase db = this.getWritableDatabase(); //Retrieves the database; references the readable and writable database into SQLiteDatabase
        ContentValues values = new ContentValues(); //Creates instance of ContentValues class that is used to store a set of values
        values.put(COLUMN_ITEM_SKU, item.getID()); //
        values.put(COLUMN_ITEM_PRODUCTNAME, item.getProductName()); //
        values.put(COLUMN_ITEM_DEPARTMENT, item.getDepartment()); //
        values.put(COLUMN_ITEM_DETAILS, item.getDetails()); //
        values.put(COLUMN_ITEM_QUANTITY, item.getQuantity()); //
        values.put(COLUMN_ITEM_COST, item.getCost()); //
        db.insert(TABLE_INVENTORY, null, values); //Inserts another row with empty values for another user
        db.close(); //Closes the database so it is not running on next instance
    }

    public boolean updateItem(int sku, String productname, String department, String details, int quantity, Float cost) { //Method is used to update a user's credentials
        SQLiteDatabase db = this.getWritableDatabase(); //Calls the database
        ContentValues values = new ContentValues(); //Creates new instance of content values
        values.put(COLUMN_ITEM_SKU, sku); //
        values.put(COLUMN_ITEM_PRODUCTNAME, productname); //
        values.put(COLUMN_ITEM_DEPARTMENT, department); //
        values.put(COLUMN_ITEM_DETAILS, details); //
        values.put(COLUMN_ITEM_QUANTITY, quantity); //
        values.put(COLUMN_ITEM_COST, cost); //

        // Below is used to update the row
        db.update(TABLE_INVENTORY, values, "item_productname = ?", new String[] {productname});
        db.close(); //Closes the database
        return true;
    }

    public void deleteItem(int id) { //This method is to delete a user record
        SQLiteDatabase db = this.getWritableDatabase(); //Calls the database
        // The below commands the table to remove/delete user record by id provided
        db.delete(TABLE_INVENTORY, COLUMN_ITEM_SKU + " = ?",
                new String[]{String.valueOf(id)});
        db.close(); //Closes the database
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
