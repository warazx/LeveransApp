package com.iths.grupp1.leveransapp.util;

import com.iths.grupp1.leveransapp.model.Customer;
import com.iths.grupp1.leveransapp.model.Order;

/**
 * Created by christiankarlsson on 16/11/16.
 */

public final class GenerateOrders {

    public static Order[] get(int amount) {
        Order[] orders = new Order[amount];

        for(int i = 0; i < orders.length; i++) {
            orders[i] = new Order(new Customer(1, null, null));
        }

        return orders;
    }
}
