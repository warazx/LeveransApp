package com.iths.grupp1.leveransapp.model;

/**
 * Created by christiankarlsson on 14/11/16.
 */

public class Customer {
    private int costumerNumber;
    private String phoneNumber;
    private String address;
    private long creatonDate;

    public Customer(int costumerNumber, String phoneNumber, String address) {
        this.costumerNumber = costumerNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.creatonDate = System.currentTimeMillis();
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

    public void setAddress(String address) {
        this.address = address;
    }
}
