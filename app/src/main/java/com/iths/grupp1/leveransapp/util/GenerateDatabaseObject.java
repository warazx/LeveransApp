package com.iths.grupp1.leveransapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;

import com.iths.grupp1.leveransapp.database.OrderSQLiteOpenHelper;
import com.iths.grupp1.leveransapp.model.Customer;
import com.iths.grupp1.leveransapp.model.Order;

import java.util.ArrayList;

public final class GenerateDatabaseObject {

    private static Random rnd = new Random();

    public static void addOrders(Activity activity, int amount) {
        OrderSQLiteOpenHelper db = new OrderSQLiteOpenHelper(activity);

        ArrayList<Customer> customers = db.getCustomers(0);
        for(int i = 0; i < amount; i++) {
            int customerID = customers.get(rnd.nextInt(customers.size())).getCostumerNumber();
            int orderSum = 500 * (rnd.nextInt(100) + 1);
            Order order = new Order(customerID);
            order.setOrderSum(orderSum);

            db.addOrder(order);
        }
    }
}