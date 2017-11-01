package com.parkhere.android;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Kevin on 10/29/2017.
 */

@IgnoreExtraProperties
public class Vehicle {

    public String manufacturer;
    public String model;
    public String color;
    public String licensePlate;

    public Vehicle() {
        // Default constructor required for calls to DataSnapshot.getValue(Vehicle.class)
    }

    public Vehicle(String manufacturer, String model, String color, String licensePlate) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.color = color;
        this.licensePlate = licensePlate;
    }

}
