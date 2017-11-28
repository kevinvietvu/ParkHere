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
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userListingRef;
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
                System.out.println("TEST ADD " + listing.getAddress());
                System.out.println("TEST USERID " + listing.getUserID());
                System.out.println("PUSH KEY " + listing.getLocationPushKey());

                locationsRef.child(listing.getAddress()).child("Users").child(listing.getUserID()).child("Renters").child(listing.getLocationPushKey()).child("Details").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String renterID = snapshot.child("renterID").getValue().toString();
                            String reservationPushKey = snapshot.child("reservationPushKey").getValue().toString();

                            try {
                                Address addressToInsertInFirebase = ManageListingsActivity.getGeoLocationFromAddress(listing.getAddress(), getActivity());
                                locationsRef.child(listing.getAddress()).child("Users").child(listing.getUserID()).child("Renters").child(listing.getLocationPushKey()).child("Details").child("renterID").setValue("");
                                locationsRef.child(listing.getAddress()).child("Users").child(listing.getUserID()).child("Renters").child(listing.getLocationPushKey()).child("Details").child("reservationPushKey").setValue("");
                                geoFire.setLocation(listing.getAddress(), new GeoLocation(addressToInsertInFirebase.getLatitude(), addressToInsertInFirebase.getLongitude()));
                                userListingRef.child(renterID).child("Reservations").child(listing.getAddress()).child(reservationPushKey).child("Details").removeValue();
                                Intent refreshList = new Intent(getActivity(), ViewUserReservationsActivity.class);
                                getActivity().finish();
                                startActivity(refreshList);
                                getActivity().getFragmentManager().popBackStack();
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
            }
        });

        writeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Listing listing = getArguments().getParcelable("listing");

                Intent writeReview = new Intent(getActivity(), WriteReviewDetailsActivity.class);
                writeReview.putExtra("address", listing.getAddress());
                writeReview.putExtra("userID", listing.getUserID());
                getActivity().finish();
                startActivity(writeReview);
                getActivity().getFragmentManager().popBackStack();
            }
        });

        return v;
    }

}
