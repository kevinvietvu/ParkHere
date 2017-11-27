package com.parkhere.android;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.geofire.GeoFire;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Stanley on 11/26/2017.
 */

public class SpotDialogFragment extends DialogFragment {
    int mNum;
    TextView descriptionTextView;
    EditText editSpotInput;
    Button editSpot;
    Button deleteSpot;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userSpotRef;
    private DatabaseReference locationsRef;

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    static SpotDialogFragment newInstance(int num, Spot spot) {
        SpotDialogFragment f = new SpotDialogFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num",num);
        args.putString("userID", spot.getUserID());
        args.putString("address", spot.getAddress());
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

        userSpotRef = database.getReference("Users");
        //locationsRef = database.getReference("Locations");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listing_dialog, container, false);
        descriptionTextView = v.findViewById(R.id.edit_listing_description);
        deleteSpot = v.findViewById(R.id.listing_delete_btn);
        editSpot = v.findViewById(R.id.listing_edit_btn);
        editSpotInput = v.findViewById(R.id.edit_listing_description_input);
        deleteSpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = (String) getArguments().get("userID");
                String address = (String) getArguments().get("address");
                userSpotRef.child(userID).child("ParkingSpots").child(address).removeValue();
                //locationsRef.child(address).child("Users").child(userID).removeValue();
                //geoFireRef.child(address).removeValue();
                Intent refreshList = new Intent(getActivity(), ManageSpotsActivity.class);
                startActivity(refreshList);
                getActivity().getFragmentManager().popBackStack();
                getActivity().finish();
            }
        });
        editSpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = (String) getArguments().get("userID");
                String address = (String) getArguments().get("address");
                if (editSpotInput.getText() == null || editSpotInput.getText().toString().isEmpty())
                    userSpotRef.child(userID).child("ParkingSpots").child(address).child("Details").child("description").setValue("");
                else
                    userSpotRef.child(userID).child("ParkingSpots").child(address).child("Details").child("description").setValue(editSpotInput.getText().toString());
                Intent refreshList = new Intent(getActivity(), ManageSpotsActivity.class);
                startActivity(refreshList);
                getActivity().getFragmentManager().popBackStack();
                getActivity().finish();
            }
        });

        return v;
    }

}
