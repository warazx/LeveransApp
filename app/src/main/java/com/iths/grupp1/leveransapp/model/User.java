package com.iths.grupp1.leveransapp.model;

/**
 * Created by christiankarlsson on 16/11/16.
 */

public class User {
    private String username;
    private String password;

    /**
     * Default constructor for the a user.
     * @param username name of the account.
     * @param password password to the account.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
}
