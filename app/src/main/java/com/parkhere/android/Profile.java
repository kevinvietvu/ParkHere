package com.parkhere.android;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Kevin on 10/29/2017.
 */

//edit later to include image fetches for car and user
@IgnoreExtraProperties
public class Profile {

    public Review reviews;

    public Profile() {
        // Default constructor required for calls to DataSnapshot.getValue(Profile.class)
    }

    public Profile(Review reviews) {
        this.reviews = reviews;
    }

}
