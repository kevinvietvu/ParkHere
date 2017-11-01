package com.parkhere.android;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Kevin on 10/29/2017.
 */

@IgnoreExtraProperties
public class Review {

    public int rating;
    public String content;

    public Review() {
        // Default constructor required for calls to DataSnapshot.getValue(Review.class)
    }

    public Review(int rating, String content) {
        this.rating = rating;
        this.content = content;
    }

}
