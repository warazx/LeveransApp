package com.iths.grupp1.leveransapp.database;

import android.content.ContentValues;
import android.content.Context;
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
        Log.d(LOG,"New Database created.");

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

        generateCustomers(this.getWritableDatabase());

    }

    // If Database Version has changed it will re-create the database.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + " " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS" + " " + TABLE_CUSTOMERS);
        db.execSQL("DROP TABLE IF EXISTS" + " " + TABLE_USERS);
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
        return null;
    }

    private void generateCustomers(SQLiteDatabase db) {

        Log.d(LOG,"Generating new customers.");

        ContentValues values;

        String address = "Ebbe Lieberathsgatan 18C, 412 65 Göteborg";
        String phoneNumber = "0705169513";
        String creationDate = "1234567890";

        values = new ContentValues();
        values.put(CUSTOMER_ADDRESS, address);
        values.put(CUSTOMER_PHONE, phoneNumber);
        values.put(CUSTOMER_CREATION_DATE, creationDate);

        db.insert(TABLE_CUSTOMERS, null, values);
        db.close();

    }

}
