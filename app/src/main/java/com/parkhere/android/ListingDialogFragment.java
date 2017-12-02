package com.parkhere.android;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.geofire.GeoFire;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Kevin on 11/19/2017.
 */

public class ListingDialogFragment extends DialogFragment {
    int mNum;
    TextView descriptionTextView;
    EditText editListingInput;
    Button editListing;
    Button deleteListing;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userListingRef;
    private DatabaseReference locationsRef;
    private DatabaseReference geoFireRef;
    private GeoFire geoFire;

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    static ListingDialogFragment newInstance(int num, Listing listing) {
        ListingDialogFragment f = new ListingDialogFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num",num);
        args.putString("userID", listing.getUserID());
        args.putString("address", listing.getAddress());
        args.putString("locationPushKey", listing.getLocationPushKey());
        args.putString("userListingPushKey", listing.getUserListingPushKey());
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
        View v = inflater.inflate(R.layout.fragment_listing_dialog, container, false);
            descriptionTextView = v.findViewById(R.id.edit_listing_description);
            deleteListing = v.findViewById(R.id.listing_delete_btn);
            editListing = v.findViewById(R.id.listing_edit_btn);
            editListingInput = v.findViewById(R.id.edit_listing_description_input);
            deleteListing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //probably have to delete from reservations..
                    final String userID = (String) getArguments().get("userID");
                    final String address = (String) getArguments().get("address");
                    String userListingPushKey = (String) getArguments().get("userListingPushKey");
                    String locationPushKey = (String) getArguments().get("locationPushKey");
                    userListingRef.child(userID).child("Listings").child(address).child(userListingPushKey).child("Details").removeValue();
                    locationsRef.child(address).child("Users").child(userID).child("Renters").child(locationPushKey).child("Details").removeValue();
                    ValueEventListener listener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int userCount = 0;
                            int listingCount = 0;
                            for (DataSnapshot d : dataSnapshot.getChildren())
                                userCount++;
                            for (DataSnapshot d : dataSnapshot.child(userID).child("Renters").getChildren())
                                listingCount++;

                            if (userCount < 2 && listingCount < 2) {
                                System.out.println("LISTING DIALOG TEST GEO FIRE REMOVE");
                                geoFireRef.child(address).removeValue();
                            }
                            else
                                System.out.println("LISTING DIALOG GEO FIRE TEST NO REMOVE");
                            locationsRef.child(address).child("Users").removeEventListener(this);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    };
                    locationsRef.child(address).child("Users").addValueEventListener(listener);


                    Intent refreshList = new Intent(getActivity(), ViewUserListingsActivity.class);
                    startActivity(refreshList);
                    getActivity().getFragmentManager().popBackStack();
                    getActivity().finish();
                }
            });
            editListing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userID = (String) getArguments().get("userID");
                    String address = (String) getArguments().get("address");
                    String userListingPushKey = (String) getArguments().get("userListingPushKey");
                    String locationPushKey = (String) getArguments().get("locationPushKey");
                    if (editListingInput.getText() == null || editListingInput.getText().toString().isEmpty())
                        userListingRef.child(userID).child("Listings").child(address).child(userListingPushKey).child("Details").child("description").setValue("");
                    else
                        userListingRef.child(userID).child("Listings").child(address).child(userListingPushKey).child("Details").child("description").setValue(editListingInput.getText().toString());
                    Intent refreshList = new Intent(getActivity(), ViewUserListingsActivity.class);
                    startActivity(refreshList);
                    getActivity().getFragmentManager().popBackStack();
                    getActivity().finish();
                }
            });

        return v;
    }

}
