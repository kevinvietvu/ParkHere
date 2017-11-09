package com.parkhere.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class ViewUserListingsActivity extends AppCompatActivity {


    private DatabaseReference userListingRef;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private FirebaseAuth auth;
    private FirebaseUser user;
    private ArrayList<String> listings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_listings);

        auth = FirebaseAuth.getInstance();

        user = auth.getCurrentUser();

        userListingRef = database.getReference("Users");

        userListingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.child(user.getUid()).child("Listings").getChildren()) {
                    Listing post = snapshot.child(user.getUid()).child("Listings").child(d.getKey()).child("Details").getValue(Listing.class);
                    System.out.println(post.toString());
                    listings.add(post.toString());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewUserListingsActivity.this, android.R.layout.simple_list_item_1, listings);
                ListView listView = findViewById(R.id.list_view);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}