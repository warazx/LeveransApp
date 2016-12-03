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

/**
 * Application SQLite database helper class.
 */
public class OrderSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String LOG = "sqlDB";

    private static final int DATABASE_VERSION = 6;
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
    private static final String CUSTOMER_NAME = "name";
    private static final String CUSTOMER_ADDRESS = "address";
    private static final String CUSTOMER_PHONE = "phoneNumber";
    private static final String CUSTOMER_CREATION_DATE = "creationDate";

    private static final String TABLE_USERS = "users";
    private static final String USER_ID = "_id";
    private static final String USER_USERNAME = "username";
    private static final String USER_PASSWORD = "password";

    /**
     * Class constructor.
     * @param context context.
     */
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
                + "," + ORDER_PLACEMENT_DATE + " " + "INTEGER"
                + "," + ORDER_DELIVERY_DATE + " " + "INTEGER"
                + "," + ORDER_DELIVERY_LONG + " " + "DOUBLE"
                + "," + ORDER_DELIVERY_LAT + " " + "DOUBLE" + ")";
        db.execSQL(command);

        command = "CREATE TABLE" + " " + TABLE_CUSTOMERS
                + "(" + CUSTOMER_ID + " " + "INTEGER PRIMARY KEY AUTOINCREMENT"
                + "," + CUSTOMER_NAME + " " + "TEXT"
                + "," + CUSTOMER_ADDRESS + " " + "TEXT"
                + "," + CUSTOMER_PHONE + " " + "TEXT"
                + "," + CUSTOMER_CREATION_DATE + " " + "INTEGER" + ")";
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

    /**
     * Adds the order data of an ArrayList to the orders database. Intended for generating random new orders.
     * @param orders the ArrayList containing Order objects for adding to the database.
     */
    public void addOrders(ArrayList<Order>orders) {

        if (!(orders == null || orders.isEmpty())) {
            SQLiteDatabase db = this.getWritableDatabase();
            for (int i = 0; i < orders.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(ORDER_CUSTOMER, orders.get(i).getCustomer());
                values.put(ORDER_SUM, orders.get(i).getOrderSum());
                values.put(ORDER_PLACEMENT_DATE, orders.get(i).getOrderPlacementDate());
                values.put(ORDER_DELIVERED, 0);
                values.put(ORDER_DELIVERY_DATE, 0);
                values.put(ORDER_DELIVERY_LONG, 0);
                values.put(ORDER_DELIVERY_LAT, 0);

                db.insert(TABLE_ORDERS, null, values);
            }
            db.close();
            Log.d(LOG,"Added " + orders.size() + " new orders.");
        } else {
            Log.d(LOG,"No orders to add.");
        }

    }

    /**
     * Updates an existing order's information in the database. Intended for
     * updating delivery status, time of delivery and longitude/latitude.
     * @param order the object containing the information to update.
     */
    public void updateOrder(Order order) {

        ContentValues values = new ContentValues();
        values.put(ORDER_DELIVERED,1);
        values.put(ORDER_DELIVERY_DATE,order.getDeliveryDate());
        values.put(ORDER_DELIVERY_LONG,order.getDeliveryLongitude());
        values.put(ORDER_DELIVERY_LAT,order.getDeliveryLatitude());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_ORDERS,values,ORDER_ID + " = " + order.getOrderNumber(), null);
        db.close();

        Log.d(LOG,"Updated order #" + order.getOrderNumber());

    }

    /**
     * Retrieves an ArrayList of delivered orders from the Orders database.
     * @return an ArrayList of Order objects.
     */
    public ArrayList<Order> getDeliveredOrders() {
        return getOrders(true,0,0);
    }

    /**
     * Retrieves an ArrayList of all undelivered orders from the Orders database.
     * @return an ArrayList of Order objects.
     */
    public ArrayList<Order> getUndeliveredOrders() {
        return getOrders(false,0,0);
    }

    /**
     * Retrieves an ArrayList of undelivered orders within a given range of order numbers.
     * @param fromID number of the first order to retrieve. If zero will start from the first order in the database.
     * @param orderCount the amount of orders to retrieve.
     * @return an ArrayList of Order objects.
     */
    public ArrayList<Order> getUndeliveredOrders(int fromID, int orderCount) {
        return getOrders(false, fromID, orderCount);
    }

    /* Parent function for retrieving Orders from database. */
    private ArrayList<Order> getOrders(boolean delivered, int fromID, int orderCount) {

        String command = "SELECT" + " * " + "FROM" + " " + TABLE_ORDERS
                       + " WHERE " + ORDER_DELIVERED + " = " + (delivered ? 1 : 0); // (Omvandlar boolean till int)
        if (fromID > 0) {
               command += " AND " + ORDER_ID + " >= " + fromID;
        }
        if (orderCount > 0) {
               command += " LIMIT " + orderCount;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(command,null);
        ArrayList<Order> orderList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int retrievedId = cursor.getInt(0);
                int retrievedCustomer = cursor.getInt(1);
                int retrievedSum = cursor.getInt(2);
                boolean retrievedDelivered = !(cursor.getInt(3) == 0);  // Omvandlar int till boolean true / false
                long retrievedPlaceDate = cursor.getLong(4);
                long retrievedDeliveryDate = cursor.getLong(5);
                double retrievedLongitude = cursor.getDouble(6);
                double retrievedLatitude = cursor.getDouble(7);

                Order order = new Order(retrievedId, retrievedSum, retrievedCustomer,
                                        retrievedDelivered, retrievedPlaceDate, retrievedDeliveryDate,
                                        retrievedLongitude, retrievedLatitude);
                orderList.add(order);
            } while (cursor.moveToNext());
        } else {
            orderList = null;
        }

        cursor.close();
        db.close();
        return orderList;
    }

    /**
     * Retrieves a Customer object from the Customers database.
     * @param id the id of the Customer to retrieve.
     * @return a Customer object.
     */
    public Customer getCustomer(int id) {
        ArrayList<Customer> customer = getCustomers(id);

        if (! (customer == null)) {
            Log.d(LOG,"Found Customer with ID: " + id);
            return customer.get(0);
        } else {
            return null;
        }
    }

    /**
     * Retrieves an ArrayList of Customer objects from the Customers database.
     * @param id the id of the Customer to retrieve. If zero retrieves all Customers in database.
     * @return an ArrayList of Customer objects.
     */
    public ArrayList<Customer> getCustomers(int id) {

        String command = "SELECT" + " * " + "FROM" + " " + TABLE_CUSTOMERS;
        if (id > 0) {
            command += " WHERE " + CUSTOMER_ID + " = " + id;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(command,null);
        ArrayList<Customer> customers = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int retrievedId = cursor.getInt(0);
                String retrievedName = cursor.getString(1);
                String retrievedAddress = cursor.getString(2);
                String retrievedPhone = cursor.getString(3);
                long retrievedDate = cursor.getLong(4);
                Customer customer = new Customer(retrievedName, retrievedPhone, retrievedAddress);
                customer.setCostumerNumber(retrievedId);
                customer.setCreatedDate(retrievedDate);
                customers.add(customer);
            } while (cursor.moveToNext());
        } else {
            Log.d(LOG,"No customers found in database.");
            customers = null;
        }

        cursor.close();
        db.close();
        return customers;

    }

    /**
     * Checks the Users database for an entry matching a given username and returns
     * a User object if successful.
     * @param username the username to look for.
     * @return a User object if the a match was found, and null if not.
     */
    public User getUser(String username) {

        String command = "SELECT" + " * " + "FROM" + " " + TABLE_USERS
                       + " WHERE " + USER_USERNAME + " = " + "'" + username + "'";
                       //+ " AND " + USER_PASSWORD + " = " + "'" + password + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(command,null);
        User user;

        if (cursor.moveToFirst()) {
            Log.d(LOG,"Found User matching " + username);
            String retrievedUsername = cursor.getString(1);
            String retrievedPassword = cursor.getString(2);
            user = new User(retrievedUsername,retrievedPassword);
        } else {
            Log.d(LOG,"No matching user found.");
            user = null;
        }

        cursor.close();
        db.close();
        return user;

    }

    /* Adds a new customer to the database */
    private void addCustomer(SQLiteDatabase db, Customer customer) {
        ContentValues values = new ContentValues();
        values.put(CUSTOMER_NAME, customer.getName());
        values.put(CUSTOMER_ADDRESS, customer.getAddress());
        values.put(CUSTOMER_PHONE, customer.getPhoneNumber());
        values.put(CUSTOMER_CREATION_DATE, customer.getCreatedDate());
        db.insert(TABLE_CUSTOMERS, null, values);
    }

    /* Adds generated new customers to the database */
    private void generateCustomers(SQLiteDatabase db) {

        String name, address, phoneNumber;

        name = "IT-Högskolan";
        address = "Ebbe Lieberathsgatan 18C, 412 65 Göteborg";
        phoneNumber = "0705169513";
        addCustomer(db, new Customer(name, phoneNumber, address));

        name = "Truckstop Alaska";
        address = "Karlavagnsgatan 13, 417 56 Göteborg";
        phoneNumber = "0737879499";
        addCustomer(db, new Customer(name, phoneNumber, address));

        name = "Liseberg";
        address = "Örgrytevägen 5, 402 22 Göteborg";
        phoneNumber = "031400100";
        addCustomer(db, new Customer(name, phoneNumber, address));

        name = "Legoland";
        address = "Nordmarksvej 9, 7190 Billund";
        phoneNumber = "+4575331333";
        addCustomer(db, new Customer(name, phoneNumber, address));

        name = "Bengans Skivbutik";
        address = "Stigbergstorget 1, 414 63 Göteborg";
        phoneNumber = "031143300";
        addCustomer(db, new Customer(name, phoneNumber, address));

        name = "ICA Supermarket Lindome";
        address = "Almåsgången 1, 437 30 Lindome";
        phoneNumber = "031997170";
        addCustomer(db, new Customer(name, phoneNumber, address));

        name = "Kråkans Krog";
        address = "Götaforsliden 1, 431 34 Mölndal";
        phoneNumber = "031270458";
        addCustomer(db, new Customer(name, phoneNumber, address));

        name = "Coop Konsum Mölnlycke";
        address = "Biblioteksgatan 7, 435 30 Mölnlycke";
        phoneNumber = "0107415340";
        addCustomer(db, new Customer(name, phoneNumber, address));

        name = "Kafé Höga Nord";
        address = "Kyrkogatan 13, 411 15 Göteborg";
        phoneNumber = "123456789";
        addCustomer(db, new Customer(name, phoneNumber, address));

        name = "Skumrask AB";
        address = "Anders Carlssons Gata 30, 417 55 Göteborg";
        phoneNumber = "123456789";
        addCustomer(db, new Customer(name, phoneNumber, address));

        // TODO: Add more customers!

        Log.d(LOG,"Generated new customers.");

    }

    /* Adds generated new users to the database. */
    private void generateUsers(SQLiteDatabase db) {

        ContentValues values;

        String username = "erilin";
        String password = "123";

        values = new ContentValues();
        values.put(USER_USERNAME, username);
        values.put(USER_PASSWORD, password);
        db.insert(TABLE_USERS, null, values);

        username = "fabmik";
        password = "321";

        values = new ContentValues();
        values.put(USER_USERNAME, username);
        values.put(USER_PASSWORD, password);
        db.insert(TABLE_USERS, null, values);

        username = "chrkar";
        password = "456";

        values = new ContentValues();
        values.put(USER_USERNAME, username);
        values.put(USER_PASSWORD, password);
        db.insert(TABLE_USERS, null, values);

        username = "chrblo";
        password = "urk";

        values = new ContentValues();
        values.put(USER_USERNAME, username);
        values.put(USER_PASSWORD, password);
        db.insert(TABLE_USERS, null, values);

        username = "kivözm";
        password = "963";

        values = new ContentValues();
        values.put(USER_USERNAME, username);
        values.put(USER_PASSWORD, password);
        db.insert(TABLE_USERS, null, values);

        Log.d(LOG,"Generated new users.");

    }

}
