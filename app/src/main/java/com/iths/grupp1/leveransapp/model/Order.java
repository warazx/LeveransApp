package com.iths.grupp1.leveransapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;

/**
 * Created by christiankarlsson on 14/11/16.
 */

public class Order implements Parcelable {
    private int orderNumber; // PRIMARY KEY
    private int orderSum;
    private int customerNumber; // FOREIGN KEY
    private boolean isDelivered;
    private long orderPlacementDate;
    private long deliveryDate;
    private double deliveryLatitude;
    private double deliveryLongitude;

    /**
     * Default constructor for creating a new order.
     * @param customer The ID of the customer.
     */
    public Order(int customer) {
        this.orderNumber = 0;
        this.orderSum = 0;
        this.customerNumber = customer;
        this.isDelivered = false;
        this.orderPlacementDate = System.currentTimeMillis();
        this.deliveryDate = 0;
        this.deliveryLatitude = 0;
        this.deliveryLongitude = 0;
    }

    /**
     * Constructor for the database. Requires a value for every variable.
     */
    public Order(int orderNumber, int orderSum, int customerNumber, boolean isDelivered,
                 long orderPlacementDate, long deliveryDate, double deliveryLatitude,
                 double deliveryLongitude) {
        this.orderNumber = orderNumber;
        this.orderSum = orderSum;
        this.customerNumber = customerNumber;
        this.isDelivered = isDelivered;
        this.orderPlacementDate = orderPlacementDate;
        this.deliveryDate = deliveryDate;
        this.deliveryLatitude = deliveryLatitude;
        this.deliveryLongitude = deliveryLongitude;
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

    public int getCustomer() {
        return customerNumber;
    }

    public void setCustomer(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public long getOrderPlacementDate() {
        //TODO: Use the helper to convert the long to a String.
        return orderPlacementDate;
    }

    public long getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(long deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public double getDeliveryLatitude() {
        return deliveryLatitude;
    }

    public double getDeliveryLongitude() {
        return deliveryLongitude;
    }

    public void setDeliveryLongitude(double deliveryLongitude) {
        this.deliveryLongitude = deliveryLongitude;
    }

    public void setDeliveryLatitude(double deliveryLatitude) {
        this.deliveryLatitude = deliveryLatitude;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(orderNumber);
        dest.writeInt(orderSum);
        dest.writeInt(customerNumber);
        dest.writeByte((byte) (isDelivered ? 1 : 0));
        dest.writeLong(orderPlacementDate);
        dest.writeLong(deliveryDate);
        dest.writeDouble(deliveryLatitude);
        dest.writeDouble(deliveryLongitude);
    }

    private Order(Parcel in) {
        orderNumber = in.readInt();
        orderSum = in.readInt();
        customerNumber = in.readInt();
        isDelivered = in.readByte() != 0;
        orderPlacementDate = in.readLong();
        deliveryDate = in.readLong();
        deliveryLatitude = in.readDouble();
        deliveryLongitude = in.readDouble();
    }

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
