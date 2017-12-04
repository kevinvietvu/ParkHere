package com.parkhere.android;

import android.app.DialogFragment;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Kevin on 11/19/2017.
 */

public class ReservationsDialogFragment extends DialogFragment {
    int mNum;
    Button deleteListing;
    Button writeReview;
    private int reservationCount;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userListingRef;
    private DatabaseReference userReservationRef;
    private DatabaseReference locationsRef;
    private DatabaseReference geoFireRef;
    private GeoFire geoFire;

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    static ReservationsDialogFragment newInstance(int num, Listing listing) {
        ReservationsDialogFragment f = new ReservationsDialogFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num",num);
        args.putParcelable("listing", listing);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments().getInt("num");

        // Pick a style based on the num.
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        switch ((mNum-1)%6) {
            case 1: style = DialogFragment.STYLE_NO_TITLE; break;
            case 2: style = DialogFragment.STYLE_NO_FRAME; break;
            case 3: style = DialogFragment.STYLE_NO_INPUT; break;
            case 4: style = DialogFragment.STYLE_NORMAL; break;
            case 5: style = DialogFragment.STYLE_NORMAL; break;
            case 6: style = DialogFragment.STYLE_NO_TITLE; break;
            case 7: style = DialogFragment.STYLE_NO_FRAME; break;
            case 8: style = DialogFragment.STYLE_NORMAL; break;
        }
        switch ((mNum-1)%6) {
            case 4: theme = android.R.style.Theme_Holo; break;
            case 5: theme = android.R.style.Theme_Holo_Light_Dialog; break;
            case 6: theme = android.R.style.Theme_Holo_Light; break;
            case 7: theme = android.R.style.Theme_Holo_Light_Panel; break;
            case 8: theme = android.R.style.Theme_Holo_Light; break;
        }
        setStyle(style, theme);

        userListingRef = database.getReference("Users");
        userReservationRef = database.getReference("Users");
        locationsRef = database.getReference("Locations");
        geoFireRef = database.getReference("geoFireListings");
        geoFire = new GeoFire(geoFireRef);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reservations_dialog, container, false);

        deleteListing = v.findViewById(R.id.reservation_delete_btn);

        writeReview = v.findViewById(R.id.reservation_write_review_btn);

        deleteListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Listing listing = getArguments().getParcelable("listing");
                final String creatorID = listing.getUserID();
                final String address = listing.getAddress();
                final String locationPushKey = listing.getLocationPushKey();
                final String listingPushKey = listing.getUserListingPushKey();
                final String renterID = listing.getRenterID();

                locationsRef.child(address).child("Users").child(creatorID).child("Renters").child(locationPushKey).child("Details").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String reservationPushKey = snapshot.child("reservationPushKey").getValue().toString();
                            try {
                                Address addressToInsertInFirebase = ManageListingsActivity.getGeoLocationFromAddress(address, getActivity());
                                locationsRef.child(address).child("Users").child(creatorID).child("Renters").child(locationPushKey).child("Details").child("renterID").setValue("");
                                locationsRef.child(address).child("Users").child(creatorID).child("Renters").child(locationPushKey).child("Details").child("reservationPushKey").setValue("");
                                geoFire.setLocation(address, new GeoLocation(addressToInsertInFirebase.getLatitude(), addressToInsertInFirebase.getLongitude()));
                                userReservationRef.child(renterID).child("Reservations").child(address).child(reservationPushKey).child("Details").removeValue();
                                userListingRef.child(creatorID).child("Listings").child(address).child(listingPushKey).child("Details").child("renterID").setValue("");
                                Intent refreshList = new Intent(getActivity(), ViewUserReservationsActivity.class);
                                startActivity(refreshList);
                                getActivity().getFragmentManager().popBackStack();
                                getActivity().finish();
                            }
                            catch (Exception e) {
                                Log.e("error", e.getMessage());
                            }
                        }
                        else {
                            System.out.println("userListing doesn't exist");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });

                userListingRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        reservationCount = Integer.parseInt(snapshot.child(listing.getUserID()).child("ParkingSpots").child(listing.getAddress()).child("Details").child("reservationCount").getValue().toString());
                        reservationCount =- 1;
                    }
                    @Override
                    public void onCancelled(DatabaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });

                userListingRef.child(listing.getUserID()).child("ParkingSpots").child(listing.getAddress()).child("Details").child("reservationCount").setValue(reservationCount);

            }
        });

        writeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Listing listing = getArguments().getParcelable("listing");

                Intent writeReview = new Intent(getActivity(), WriteReviewDetailsActivity.class);
                writeReview.putExtra("address", listing.getAddress());
                writeReview.putExtra("userID", listing.getUserID());
                startActivity(writeReview);
                getActivity().getFragmentManager().popBackStack();
                getActivity().finish();
            }
        });

        return v;
    }

}
