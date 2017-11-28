package com.parkhere.android;

/**
 * Created by Kevin on 10/29/2017.
 */

public class Spot {

    public String address;
    public String description;
    public String spotType;
    public String userID;

    public Spot() {
        // Default constructor required for calls to DataSnapshot.getValue(Spot.class)
    }

    public String getAddress() { return address; }

    public String getDescription() { return description; }

    public String getSpotType() { return spotType; }

    public String getUserID() { return userID; }

    @Override
    public String toString() {
        return "Address: " + address + "\nSpot Type: " + spotType + " \nDescription: " + description;
    }
}
