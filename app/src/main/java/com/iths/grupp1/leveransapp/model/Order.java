package com.iths.grupp1.leveransapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by christiankarlsson on 14/11/16.
 */

public class Order implements Parcelable {
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

    public void deliver() {
        //TODO: Get the current time from the helper class.
        deliveryDate = 0; //Placeholder
        setDeliveryLocation();
    }

    private void setDeliveryLocation() {
        //TODO: Get location for the delivered position.
        deliveryLatitude = 0;
        deliveryLongitude = 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(orderNumber);
    }

    private Order(Parcel in) {
        orderNumber = in.readInt();
    }

    public int getOrderNumber() {
        return orderNumber;
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
}
