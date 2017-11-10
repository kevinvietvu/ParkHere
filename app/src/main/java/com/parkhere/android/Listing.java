package com.parkhere.android;


import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Kevin on 10/29/2017.
 */

@IgnoreExtraProperties
public class Listing {

    public String address;
    public String description;
    public String endDate;
    public String endTime;
    public Double price;
    public String spotType;
    public String startDate;
    public String startTime;
    public String userID;

    public Listing() {
        // Default constructor required for calls to DataSnapshot.getValue(Listing.class)
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() { return description; }

    public String getEndDate() {
        return endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public Double getPrice() {
        return price;
    }

    public String getSpotType() { return spotType; }

    public String getStartDate() {
        return startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getUserID() { return userID; }

    @Override
    public String toString() {
        return "Address: " + address + " \nPrice: " + price + "\nSpot Type: " + spotType + " \nDescription: " + description + " \nStart Date: "
                + startDate + ", End Date: " + endDate + " \nStart Time: " + startTime + ", End Time: " + endTime;
    }

}
