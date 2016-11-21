package com.iths.grupp1.leveransapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iths.grupp1.leveransapp.model.Customer;
import com.iths.grupp1.leveransapp.model.Order;
import com.iths.grupp1.leveransapp.model.User;

import java.util.ArrayList;
import android.util.Log;


public class OrderSQLiteOpenHelper extends SQLiteOpenHelper implements IOrderSQLiteOpenHelper {

    public static final String LOG = "sqlDB";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "deliveryDB.db";

    private static final String TABLE_ORDERS = "orders";
    private static final String ORDER_ID = "_id";
    private static final String ORDER_CUSTOMER = "customer";
    private static final String ORDER_SUM = "sum";
    private static final String ORDER_DELIVERED = "isDelivered";
    private static final String ORDER_PLACEMENT_DATE = "placementDate";
    private static final String ORDER_DELIVERY_DATE = "deliveryDate";
    private static final String ORDER_DELIVERY_LONG = "deliveryLongitude";
    private static final String ORDER_DELIVERY_LAT = "deliveryLatitude";

    private static final String TABLE_CUSTOMERS = "customers";
    private static final String CUSTOMER_ID = "_id";
    private static final String CUSTOMER_ADDRESS = "address";
    private static final String CUSTOMER_PHONE = "phoneNumber";
    private static final String CUSTOMER_CREATION_DATE = "creationDate";

    private static final String TABLE_USERS = "users";
    private static final String USER_ID = "_id";
    private static final String USER_USERNAME = "username";
    private static final String USER_PASSWORD = "password";

    public OrderSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String command;
        Log.d(LOG,"Trying to create new database.");

        command = "CREATE TABLE" + " " + TABLE_ORDERS
                + "(" + ORDER_ID + " " + "INTEGER PRIMARY KEY AUTOINCREMENT"
                + "," + ORDER_CUSTOMER + " " + "INTEGER"
                + "," + ORDER_SUM + " " + "INTEGER"
                + "," + ORDER_DELIVERED + " " + "TINYINT"
                + "," + ORDER_PLACEMENT_DATE + " " + "TEXT"
                + "," + ORDER_DELIVERY_DATE + " " + "TEXT"
                + "," + ORDER_DELIVERY_LONG + " " + "DOUBLE"
                + "," + ORDER_DELIVERY_LAT + " " + "DOUBLE" + ")";
        db.execSQL(command);

        command = "CREATE TABLE" + " " + TABLE_CUSTOMERS
                + "(" + CUSTOMER_ID + " " + "INTEGER PRIMARY KEY AUTOINCREMENT"
                + "," + CUSTOMER_ADDRESS + " " + "TEXT"
                + "," + CUSTOMER_PHONE + " " + "TEXT"
                + "," + CUSTOMER_CREATION_DATE + " " + "TEXT" + ")";
        db.execSQL(command);

        command = "CREATE TABLE" + " " + TABLE_USERS
                + "(" + USER_ID + " " + "INTEGER PRIMARY KEY AUTOINCREMENT"
                + "," + USER_USERNAME + " " + "TEXT"
                + "," + USER_PASSWORD + " " + "TEXT" + ")";
        db.execSQL(command);

        Log.d(LOG,"Created new tables.");

        generateCustomers(db);
        generateUsers(db);

        Log.d(LOG,"Finished creating new database.");

    }

    // If Database Version has changed it will re-create the database.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + " " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS" + " " + TABLE_CUSTOMERS);
        db.execSQL("DROP TABLE IF EXISTS" + " " + TABLE_USERS);
        Log.d(LOG,"Database version upgrade. Removing old content.");
        onCreate(db);
    }

    // If Database version has changed it will re-create the database
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + " " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS" + " " + TABLE_CUSTOMERS);
        db.execSQL("DROP TABLE IF EXISTS" + " " + TABLE_USERS);
        Log.d(LOG,"Database version downgrade. Removing old content.");
        onCreate(db);
    }

    @Override
    public boolean addOrder(Order order) {
        return false;
    }

    @Override
    public boolean updateOrder(Order order) {
        return false;
    }

    @Override
    public ArrayList<Order> getUndeliveredOrders() {
        return null;
    }

    @Override
    public ArrayList<Order> getDeliveredOrders() {
        return null;
    }

    @Override
    public Customer getCustomer(int id) {
        return null;
    }

    @Override
    public User getUser(String username, String password) {

        String command = "SELECT" + " * " + "FROM" + " " + TABLE_USERS
                        + " WHERE " + USER_USERNAME + " = " + "'" + username + "'"
                        + " AND " + USER_PASSWORD + " = " + "'" + password + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(command,null);

        User user;

        if (cursor.moveToFirst()) {
            Log.d(LOG,"Found User matching " + username + " / " + password);
            String retrievedUsername = cursor.getString(1);
            String retrievedPassword = cursor.getString(2);
            user = new User(retrievedUsername,retrievedPassword);
        } else {
            Log.d(LOG,"No matching user found.");
            user = null;
        }

        cursor.close();

        return user;

    }

    /* Adds generated new customers to the database */
    private void generateCustomers(SQLiteDatabase db) {

        ContentValues values;

        String address = "Ebbe Lieberathsgatan 18C, 412 65 Göteborg";
        String phoneNumber = "0705169513";
        String creationDate = "1234567890";

        values = new ContentValues();
        values.put(CUSTOMER_ADDRESS, address);
        values.put(CUSTOMER_PHONE, phoneNumber);
        values.put(CUSTOMER_CREATION_DATE, creationDate);

        db.insert(TABLE_CUSTOMERS, null, values);

        Log.d(LOG,"Generated new customers.");

    }

    /* Adds generated new users to the database. */
    private void generateUsers(SQLiteDatabase db) {

        ContentValues values;

        String username = "erikalinde";
        String password = "123";

        values = new ContentValues();
        values.put(USER_USERNAME, username);
        values.put(USER_PASSWORD, password);
        db.insert(TABLE_USERS, null, values);

        username = "fabianmikaelsson";
        password = "321";

        values = new ContentValues();
        values.put(USER_USERNAME, username);
        values.put(USER_PASSWORD, password);
        db.insert(TABLE_USERS, null, values);

        username = "christiankarlsson";
        password = "456";

        values = new ContentValues();
        values.put(USER_USERNAME, username);
        values.put(USER_PASSWORD, password);
        db.insert(TABLE_USERS, null, values);

        username = "christianblomqvist";
        password = "urk";

        values = new ContentValues();
        values.put(USER_USERNAME, username);
        values.put(USER_PASSWORD, password);
        db.insert(TABLE_USERS, null, values);

        username = "kivancözmen";
        password = "963";

        values = new ContentValues();
        values.put(USER_USERNAME, username);
        values.put(USER_PASSWORD, password);
        db.insert(TABLE_USERS, null, values);

        Log.d(LOG,"Generated new users.");

    }



}
