package com.iths.grupp1.leveransapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by christiankarlsson on 14/11/16.
 */

public class Customer implements Parcelable {
    private int costumerNumber;
    private String name;
    private String phoneNumber;
    private String address;
    private long created;

    public Customer(String name, String phoneNumber, String address) {
        this.costumerNumber = 0;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.created = System.currentTimeMillis();
    }

    public int getCostumerNumber() {
        return costumerNumber;
    }

    public void setCostumerNumber(int costumerNumber) {
        this.costumerNumber = costumerNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(costumerNumber);
        dest.writeString(name);
        dest.writeString(phoneNumber);
        dest.writeString(address);
        dest.writeLong(created);
    }

    private Customer(Parcel in) {
        costumerNumber = in.readInt();
        name = in.readString();
        phoneNumber = in.readString();
        address = in.readString();
        created = in.readLong();
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {

        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };
}
