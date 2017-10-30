package com.parkhere.android;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Kevin on 10/29/2017.
 */

@IgnoreExtraProperties
public class User {

    public String userName;
    public String userEmail;
    public Vehicle userVehicle;
    public Profile userProfile;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String userName, String userEmail, Vehicle userVehicle, Profile userProfile ) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userVehicle = userVehicle;
        this.userProfile = userProfile;

    }
}
