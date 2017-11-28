package com.parkhere.android;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
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

public class ViewUserReservationsActivity extends AppCompatActivity {

    private DatabaseReference userReservationRef;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private FirebaseAuth auth;
    private FirebaseUser user;
    private ArrayList<String> listings = new ArrayList<>();
    private ArrayList<Listing> listingObjects = new ArrayList<>();

    public static ViewUserSpotsActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_reservations);

        auth = FirebaseAuth.getInstance();

        user = auth.getCurrentUser();

        userReservationRef = database.getReference("Users");

        /**
         Map<String, Object> listingData = new HashMap<String, Object>();

         listingData.put("price", "1");
         listingData.put("description", "boi");
         listingData.put("spotType", "single");
         listingData.put("startDate", "1");
         listingData.put("startTime", "2");
         listingData.put("endDate", "3");
         listingData.put("endTime", "4");
         listingData.put("address" , "Union City, CA, USA");

         userReservationRef.child(user.getUid()).child("Reservations").child("Union City, CA, USA").child("Details").setValue(listingData); */

        userReservationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.child(user.getUid()).child("Reservations").getChildren()) {
                    Listing post = snapshot.child(user.getUid()).child("Reservations").child(d.getKey()).child("Details").getValue(Listing.class);
                    System.out.println(post.toString());
                    listings.add(post.toString());
                    listingObjects.add(post);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewUserReservationsActivity.this, android.R.layout.simple_list_item_1, listings);
                ListView listView = findViewById(R.id.list_view);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        showDialog(listingObjects.get(position));
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public void showDialog(Listing listing) {
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
        DialogFragment newFragment = ReservationsDialogFragment.newInstance(mStackLevel, listing);
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

    @Override
    public void finish() {
        super.finish();
        instance = null;
    }
}