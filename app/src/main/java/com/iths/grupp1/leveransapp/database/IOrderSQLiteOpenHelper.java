package com.iths.grupp1.leveransapp.database;

import com.iths.grupp1.leveransapp.model.Customer;
import com.iths.grupp1.leveransapp.model.Order;
import com.iths.grupp1.leveransapp.model.User;

import java.util.ArrayList;

/**
 * Created by gaspop on 2016-11-16.
 */

public interface IOrderSQLiteOpenHelper {

    /**
     * Adds a new order to the Orders database.
     * @param order the object containing the order information to add.
     * @return true or false if operation was successful or not.
     */
    public boolean addOrder(Order order);

    /**
     * Updates an existing order's information in the database. Intended for
     * updating delivery status, time of delivery and longitude/latitude.
     * @param order the object containing the information to update.
     * @return true or false if operation was successful or not.
     */
    public boolean updateOrder(Order order);

    /**
     * Retrieves an ArrayList of undelivered orders from the Orders database.
     * @return an ArrayList of Order objects.
     */
    public ArrayList<Order> getUndeliveredOrders();

    /**
     * Retrieves an ArrayList of delivered orders from the Orders database.
     * @return an ArrayList of Order objects.
     */
    public ArrayList<Order> getDeliveredOrders();

    /**
     * Retrieves a Customer object from the Customers database.
     * @param id the id of the Customer to retrieve.
     * @return a Customer object.
     */
    public Customer getCustomer(int id);

    /**
     * Checks the Users database for an entry matching a given username and password and returns
     * a User object if successful.
     * @param username the username to look for.
     * @param password the password to look for.
     * @return a User object if the a match was found, and null if not.
     */
    public User getUser(String username, String password);

}
