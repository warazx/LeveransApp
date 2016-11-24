package com.iths.grupp1.leveransapp.util;

import com.iths.grupp1.leveransapp.model.Order;

/**
 * Created by christiankarlsson on 16/11/16.
 */

public final class GenerateDatabaseObject {

    public static Order[] addOrders(int amount) {
        Order[] orders = new Order[amount];

        for(int i = 0; i < orders.length; i++) {
            //TODO: get a random customer.
            orders[i] = new Order(1);
        }

        return orders;
    }
}
