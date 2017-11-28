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
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewUserSpotsActivity extends AppCompatActivity {
    private Button add_spot;

    private DatabaseReference userSpotsRef;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private FirebaseAuth auth;
    private FirebaseUser user;
    private ArrayList<String> spots = new ArrayList<>();
    private ArrayList<Spot> spotObjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_spots);

        auth = FirebaseAuth.getInstance();

        user = auth.getCurrentUser();

        add_spot = findViewById(R.id.btn_add_spot);

        add_spot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewUserSpotsActivity.this, AddSpotMapsActivity.class);
                startActivity(intent);
            }
        });



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

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewUserSpotsActivity.this, android.R.layout.simple_list_item_1, spots);
                ListView listView = findViewById(R.id.list_view);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        showDialog(spotObjects.get(position));
                    }
                });

            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

    public void showDialog(Spot spot) {
        int mStackLevel = 0;
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = SpotDialogFragment.newInstance(mStackLevel,spot);
        newFragment.show(ft, "dialog");
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