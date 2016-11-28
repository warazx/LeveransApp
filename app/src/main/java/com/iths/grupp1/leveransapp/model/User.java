package com.iths.grupp1.leveransapp.model;

/**
 * Created by christiankarlsson on 16/11/16.
 */

public class User {
    private static String username;
    private static String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static String getUsername(){
        return username;
    }
    public static String getPassword(){
        return password;
    }
}
