package com.parkhere.android;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Parcelable;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Kevin on 11/19/2017.
 */

public class BrowseListingsDialogFragment extends DialogFragment {
    int mNum;
    Button viewUserProfileBtn;
    Button rentListingBtn;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userListingRef;
    private DatabaseReference locationsRef;
    private DatabaseReference geoFireRef;
    private GeoFire geoFire;

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    static BrowseListingsDialogFragment newInstance(int num, Listing listing) {
        BrowseListingsDialogFragment f = new BrowseListingsDialogFragment();
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

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        userListingRef = database.getReference("Users");
        locationsRef = database.getReference("Locations");
        geoFireRef = database.getReference("geoFireListings");
        geoFire = new GeoFire(geoFireRef);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_browse_listings_dialog, container, false);

        viewUserProfileBtn = v.findViewById(R.id.view_user_profile_btn);
        rentListingBtn = v.findViewById(R.id.rent_from_listings_btn);

        viewUserProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listing listing = getArguments().getParcelable("listing");

                Intent viewSelectedUserProfile = new Intent(getActivity(), ProfileActivity.class);
                viewSelectedUserProfile.putExtra("userID", listing.getUserID());
                viewSelectedUserProfile.putExtra("address", listing.getAddress());
                startActivity(viewSelectedUserProfile);
                getActivity().getFragmentManager().popBackStack();
            }
        });
        rentListingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Listing listing = getArguments().getParcelable("listing");
                if (listing.getUserID().equals(user.getUid())) {
                    Toast.makeText(getActivity(), "Cannot Reserve Own Listing",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    Intent browseListingPayment = new Intent(getActivity(), SplitBookingStartDateActivity.class);
                    browseListingPayment.putExtra("listing", listing);
                    startActivity(browseListingPayment);
                    getActivity().getFragmentManager().popBackStack();
                    getActivity().finish();
                }
            }
        });

        return v;
    }

}
