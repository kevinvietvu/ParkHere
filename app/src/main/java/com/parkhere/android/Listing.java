package com.parkhere.android;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

/**
 * Created by Kevin on 10/29/2017.
 */

@IgnoreExtraProperties
public class Listing {

    public Spot spot;
    public double price;
    public Date date;

    public Listing() {
        // Default constructor required for calls to DataSnapshot.getValue(Listing.class)
    }

    public Listing(Spot spot, double price, Date date) {
        this.spot = spot;
        this.price = price;
        this.date = date;
    }
}
