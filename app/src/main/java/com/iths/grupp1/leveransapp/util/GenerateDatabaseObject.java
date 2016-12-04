package com.iths.grupp1.leveransapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;

import com.iths.grupp1.leveransapp.database.OrderSQLiteOpenHelper;
import com.iths.grupp1.leveransapp.model.Customer;
import com.iths.grupp1.leveransapp.model.Order;

import java.util.ArrayList;

/**
 * Helper class to help generate objects for the database.
 */
public final class GenerateDatabaseObject {

    private static Random rnd = new Random();

    /**
     * Adds amount of orders to the database. Gives all orders an random Customer linked to them.
     * @param activity context.
     * @param amount number of orders to be generated.
     */
    public static void addOrders(Activity activity, int amount) {
        OrderSQLiteOpenHelper db = new OrderSQLiteOpenHelper(activity);

        ArrayList<Customer> customers = db.getCustomers(0);
        ArrayList<Order> orders = new ArrayList<>();
        for(int i = 0; i < amount; i++) {
            int customerID = customers.get(rnd.nextInt(customers.size())).getCostumerNumber();
            // Standard fee of 200, + (1-100 * 25)
            int orderSum = 200 + ((rnd.nextInt(100) + 1) * 25);
            Order order = new Order(customerID);
            order.setOrderSum(orderSum);
            orders.add(order);
        }
        db.addOrders(orders);
    }
}