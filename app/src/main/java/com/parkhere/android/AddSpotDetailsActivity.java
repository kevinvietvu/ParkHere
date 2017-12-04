package com.parkhere.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddSpotDetailsActivity extends AppCompatActivity {
    private String address;
    private String description;
    private String spotType;
    private Button confirm;
    private Bundle bundle;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userRef;

    private FirebaseAuth auth;
    private FirebaseUser user;

    private Map<String, Object> spotData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spot_details);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        bundle = getIntent().getExtras();

        confirm = findViewById(R.id.confirm_button);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address = bundle.getString("address");
                description = ((EditText) findViewById(R.id.enter_spot_description)).getText().toString();
                Spinner spinner = findViewById(R.id.choose_spot_type);
                spotType = spinner.getSelectedItem().toString();

                if (!descriptionMustBeLessThan140Characters(description)) {
                    Toast.makeText(AddSpotDetailsActivity.this, "Please enter less than 140 characters for description", Toast.LENGTH_LONG).show();
                }
                else {
                    userRef = database.getReference("Users").child(user.getUid()).child("ParkingSpots").child(address).child("Details");
                    spotData.put("address", address);
                    spotData.put("description", description);
                    spotData.put("spotType", spotType);
                    spotData.put("userID", user.getUid());
                    spotData.put("reservationCount", 0);
                    userRef.setValue(spotData);

                    if(AddSpotMapsActivity.instance != null) {
                        try {
                            AddSpotMapsActivity.instance.finish();
                        } catch (Exception e) {}
                    }

                    Toast.makeText(AddSpotDetailsActivity.this, "Spot has been added!", Toast.LENGTH_LONG).show();
                    finish();
            }
            }
        });
    }

    public static boolean descriptionMustBeLessThan140Characters(String description) {
        if (description.length() <= 140) return true;
        return false;
    }
}
