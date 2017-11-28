package com.parkhere.android;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateListingChooseSpotActivity extends AppCompatActivity {
    private DatabaseReference userSpotsRef;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private FirebaseAuth auth;
    private FirebaseUser user;
    private ArrayList<String> spots = new ArrayList<>();
    private ArrayList<Spot> spotObjects = new ArrayList<>();

    public static CreateListingChooseSpotActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing_choose_spot);

        auth = FirebaseAuth.getInstance();

        user = auth.getCurrentUser();

        userSpotsRef = database.getReference("Users");

        userSpotsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.child(user.getUid()).child("ParkingSpots").getChildren()) {
                    Spot post = snapshot.child(user.getUid()).child("ParkingSpots").child(d.getKey()).child("Details").getValue(Spot.class);
                    System.out.println(post.toString());
                    spots.add(post.toString());
                    spotObjects.add(post);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateListingChooseSpotActivity.this, android.R.layout.simple_list_item_1, spots);
                ListView listView = findViewById(R.id.list_view);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(CreateListingChooseSpotActivity.this, CreateListingStartDateActivity.class);
                        Spot spot = spotObjects.get(position);
                        intent.putExtra("address", spot.getAddress());
                        intent.putExtra("description", spot.getDescription());
                        intent.putExtra("spotType", spot.getSpotType());
                        startActivity(intent);
                    }
                });

            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        instance = null;
    }

}
