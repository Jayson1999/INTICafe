package com.example.androidassignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
//SQLite Database Helper class
public class OrderDatabaseHelper extends SQLiteOpenHelper {
    //Database table info with its Table and Column names
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "orderDB.db";
    public static final String TABLE_NAME = "OrderTable";
    public static final String COLUMN_ID = "OrderID";
    public static final String COLUMN_PRODUCTCODE = "ProductCode";
    public static final String COLUMN_ORDER = "OrderItem";
    public static final String COLUMN_PRICE = "Price";
    public static final String COLUMN_REVIEW = "Review";
    public static final String COLUMN_NAME = "CustomerName";
    public static final String COLUMN_PASSWORD = "CustomerPassword";
    public static final String COLUMN_CONTACT = "CustomerContact";
    public static final String COLUMN_POSTCODE = "CustomerPostcode";
    public static final String COLUMN_AREA = "CustomerArea";

    //Database initialization
    public OrderDatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    //Create Table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
                + COLUMN_ID + " Integer Primary Key, "
                + COLUMN_PRODUCTCODE + " TEXT, "
                + COLUMN_ORDER+ " TEXT, "
                + COLUMN_PRICE + " REAL, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_CONTACT + " TEXT, "
                + COLUMN_POSTCODE + " TEXT, "
                + COLUMN_AREA + " TEXT, "
                + COLUMN_REVIEW + " TEXT" + ");";
        db.execSQL(CREATE_TABLE);

    }

    //Upgrade method if old database already exist
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table if exists "+TABLE_NAME);
        onCreate(db);
    }


    /**
     * Method to load all data from database table and store them into and String ArrayList
     * @param custName Logged in username that is stored in Shared Preference to display only his order history
     * @return a String ArrayList containing the data gained in String format
     */
    public ArrayList<String> loadOrder(String custName) {
        String query = "Select * FROM " + TABLE_NAME + " WHERE "+ COLUMN_NAME + " = "+ "'" +custName+ "'";
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> orderList = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            orderList.add(String.format("%s\n%s\n%s\n%s%.2f\n%s%s\n","Order ID: " + cursor.getInt(0), "\nProduct Code:\n" + cursor.getString(1), "Order Item:\n" + cursor.getString(2), "Total Price: RM ", cursor.getDouble(3), "Review: ",cursor.getString(9)));
        }
        cursor.close();
        db.close();
        return orderList;
    }


    /**
     * Method to add Order into Database Table
     * @param order Order class received
     */
    public void addOrder(Order order) {
        ContentValues values = new ContentValues();
        String productCodes = "";
        String productNames = "";
        for(int i = 0;i<order.getFoods().size();i++){
            productCodes = productCodes+order.getFoods().get(i).getCode()+"\n";
            productNames = productNames+order.getFoods().get(i).getName()+"\n";
        }
        values.put(COLUMN_PRODUCTCODE, productCodes);
        values.put(COLUMN_ORDER, productNames);
        values.put(COLUMN_PRICE, order.getPrice());
        values.put(COLUMN_NAME, order.getCust().getName());
        values.put(COLUMN_PASSWORD, order.getCust().getPassword());
        values.put(COLUMN_CONTACT, order.getCust().getContact());
        values.put(COLUMN_POSTCODE, order.getCust().getPostcode());
        values.put(COLUMN_AREA, order.getCust().getArea());
        values.put(COLUMN_REVIEW, order.getReview());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Method to allow user to add or update review on an Order
     * @param review entered by user
     * @param orderID for which Order
     */
    public void addReview(String review, int orderID){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_REVIEW,review);
        boolean ret1 = db.update(TABLE_NAME, values, COLUMN_ID + "=? ", new String[]{orderID+""}) > 0;
        db.close();
    }

    /**
     * Method to search for an Order Class Containing the info of both Customer and Order based on logged in Customer's name
     * @param custName Logged in Customer's name
     * @return the Order found
     */
    public Order findOrder(String custName) {
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = " + "'" + custName + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Order order = new Order();
        Customer cust = new Customer();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            order.setPrice(cursor.getDouble(3));
            cust.setName(cursor.getString(4));
            cust.setPassword(cursor.getString(5));
            cust.setContact(cursor.getString(6));
            cust.setPostcode(cursor.getString(7));
            cust.setArea(cursor.getString(8));
            order.setCust(cust);
            order.setReview(cursor.getString(9));
            cursor.close();
        }
        db.close();
        return order;
    }

    /**
     * Method to allow User to make changes to their Profile and update them onto database
     * @param custname Logged in user
     * @param newChange New change that wants to be applied
     * @param editChoice The choice of selection of which field to be edited
     * @return True if the task succeed and false otherwise.
     */
    public boolean updateCustomer(String custname, String newChange, int editChoice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();

        switch(editChoice){
            case 1:
                args.put(COLUMN_NAME, newChange);
                boolean ret1 = db.update(TABLE_NAME, args, COLUMN_NAME + "=? ", new String[]{custname}) > 0;
                break;
            case 2:
                args.put(COLUMN_CONTACT, newChange);
                boolean ret2 = db.update(TABLE_NAME, args, COLUMN_NAME + "=? ", new String[]{custname}) > 0;
                break;
            case 3:
                args.put(COLUMN_POSTCODE, newChange);
                boolean ret3 = db.update(TABLE_NAME, args, COLUMN_NAME + "=? ", new String[]{custname}) > 0;
                break;
            case 4:
                args.put(COLUMN_AREA, newChange);
                boolean ret4 = db.update(TABLE_NAME, args, COLUMN_NAME + "=? ", new String[]{custname}) > 0;
                break;
        }
        db.close();
        return true;
    }
}

