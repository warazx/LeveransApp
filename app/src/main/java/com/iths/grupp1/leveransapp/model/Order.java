package com.iths.grupp1.leveransapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;

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
        this.orderPlacementDate = System.currentTimeMillis();
        this.deliveryDate = 0;
        this.deliveryLatitude = 0;
        this.deliveryLongitude = 0;
    }

    public void deliver() {
        deliveryDate = System.currentTimeMillis();
        setDeliveryLocation();
    }

    private void setDeliveryLocation() {
        //TODO: Get location for the delivered position.
        deliveryLatitude = 0;
        deliveryLongitude = 0;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getOrderSum() {
        return orderSum;
    }

    public void setOrderSum(int orderSum) {
        this.orderSum = orderSum;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public long getOrderPlacementDate() {
        return orderPlacementDate;
    }

    public void setOrderPlacementDate(long orderPlacementDate) {
        this.orderPlacementDate = orderPlacementDate;
    }

    public long getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(long deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public long getDeliveryLatitude() {
        return deliveryLatitude;
    }

    public void setDeliveryLatitude(long deliveryLatitude) {
        this.deliveryLatitude = deliveryLatitude;
    }

    public long getDeliveryLongitude() {
        return deliveryLongitude;
    }

    public void setDeliveryLongitude(long deliveryLongitude) {
        this.deliveryLongitude = deliveryLongitude;
    }


    private Order(Parcel in) {
        orderNumber = in.readInt();
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    /* ------ IMPLEMENT PARCE
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(orderNumber);
    }

    //Creator for the recyclerview.
    public static final Creator<Order> CREATOR = new Creator<Order>() {

        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
    */
}
