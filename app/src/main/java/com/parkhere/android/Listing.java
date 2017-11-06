package com.parkhere.android;

import android.location.Address;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

/**
 * Created by Kevin on 10/29/2017.
 */

@IgnoreExtraProperties
public class Listing {

    public String address;
    public String description;
    public String endDate;
    public String endTime;
    public String price;
    public String spotType;
    public String startDate;
    public String startTime;

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

    public String getPrice() {
        return price;
    }

    public String getSpotType() {
        return spotType;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getStartTime() {
        return startTime;
    }

}
