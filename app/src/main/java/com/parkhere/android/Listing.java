package com.parkhere.android;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Kevin on 10/29/2017.
 */

@IgnoreExtraProperties
public class Listing implements Parcelable {

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public Listing createFromParcel(Parcel in) {
            return new Listing(in);
        }

        public Listing[] newArray(int size) {
            return new Listing[size];
        }
    };
    public String address;
    public String endDate;
    public String endTime;
    public Double price;
    public String spotType;
    public String startDate;
    public String startTime;
    public String userID;
    public String vehicleMake;
    public String vehicleModel;
    public String vehicleColor;
    public String licensePlateNumber;
    public String renterID;
    public String locationPushKey;
    public String userListingPushKey;

    public Listing() {
        // Default constructor required for calls to DataSnapshot.getValue(Listing.class)
    }

    private Listing(Parcel in) {
        address = in.readString();
        endDate = in.readString();
        endTime = in.readString();
        price = in.readDouble();
        startDate = in.readString();
        startTime = in.readString();
        spotType = in.readString();
        userID = in.readString();
        locationPushKey = in.readString();
        userListingPushKey = in.readString();

    }

    public String getAddress() {
        return address;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public Double getPrice() { return price; }

    public String getSpotType() { return spotType; }

    public String getStartDate() {
        return startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getUserID() { return userID; }

    public String getLocationPushKey() { return locationPushKey; }

    public String getUserListingPushKey() { return userListingPushKey; }

    public String getRenterID() { return renterID; }

    public String getVehicleMake() { return vehicleMake; }

    public String getVehicleModel() { return vehicleModel; }

    public String getVehicleColor() { return vehicleColor; }

    public String getLicensePlateNumber() { return licensePlateNumber; }

    @Override
    public String toString() {
        return "Address: " + address + " \nPrice: " + price + "\nSpot Type: " + spotType + " \nStart Date: "
                + startDate + ", End Date: " + endDate + " \nStart Time: " + startTime + ", End Time: " + endTime;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(endDate);
        dest.writeString(endTime);
        dest.writeDouble(price);
        dest.writeString(startDate);
        dest.writeString(startTime);
        dest.writeString(spotType);
        dest.writeString(userID);
        /**
        dest.writeString(vehicleMake);
        dest.writeString(vehicleModel);
        dest.writeString(vehicleColor);
        dest.writeString(licensePlateNumber);dest.writeString(renterID); */
        dest.writeString(locationPushKey);
        dest.writeString(userListingPushKey);

    }



}
