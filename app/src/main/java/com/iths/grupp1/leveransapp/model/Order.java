package com.iths.grupp1.leveransapp.model;

/**
 * Created by christiankarlsson on 14/11/16.
 */

public class Order {
    private int orderNumber;
    private int orderSum;
    private Customer customer;
    private boolean isDelivered;
    private long orderPlacementDate;
    private long deliveryDate;
    private long deliveryLatitude;
    private long deliveryLongitude;

    private static int orderID = 1;

    public Order(Customer customer) {
        this.orderNumber = orderID;
        orderID++;
        this.customer = customer;
        this.isDelivered = false;
        //TODO: Get the current time from the helper class.
        this.orderPlacementDate = 1000; //Placeholder.
        this.deliveryDate = 0;
        this.deliveryLatitude = 0;
        this.deliveryLongitude = 0;
    }
}
