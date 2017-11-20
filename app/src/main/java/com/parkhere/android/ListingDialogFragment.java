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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        deleteListing = v.findViewById(R.id.delete_btn);
        editListing = v.findViewById(R.id.edit_btn);
        editListingInput = v.findViewById(R.id.edit_listing_description_input);
        deleteListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = (String) getArguments().get("userID");
                String address = (String) getArguments().get("address");
                userListingRef.child(userID).child("Listings").child(address).removeValue();
                locationsRef.child(address).child("Users").child(userID).removeValue();
                geoFireRef.child(address).removeValue();
                Intent refreshList = new Intent(getActivity(), ViewUserListingsActivity.class);
                startActivity(refreshList);
                getActivity().getFragmentManager().popBackStack();
            }
        });

        editListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = (String) getArguments().get("userID");
                String address = (String) getArguments().get("address");
                if (editListingInput.getText() == null || editListingInput.getText().toString().isEmpty())
                    userListingRef.child(userID).child("Listings").child(address).child("Details").child("description").setValue("");
                else
                    userListingRef.child(userID).child("Listings").child(address).child("Details").child("description").setValue(editListingInput.getText().toString());
                Intent refreshList = new Intent(getActivity(), ViewUserListingsActivity.class);
                startActivity(refreshList);
                getActivity().getFragmentManager().popBackStack();
            }
        });

        return v;
    }

}
