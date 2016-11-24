package com.iths.grupp1.leveransapp.util;

import android.app.Activity;

import com.iths.grupp1.leveransapp.database.OrderSQLiteOpenHelper;
import com.iths.grupp1.leveransapp.model.Order;

public final class GenerateDatabaseObject {

    public static void addOrders(Activity activity) {
        OrderSQLiteOpenHelper db = new OrderSQLiteOpenHelper(activity);
        //TODO: Get the number from settings / sharedprefs.
        int amount = 10;

        for(int i = 0; i < amount; i++) {
            db.addOrder(new Order(1));
        }
    }
}
