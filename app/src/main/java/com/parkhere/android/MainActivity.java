package com.parkhere.android;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GeoQueryEventListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private FirebaseAuth auth;
    private DatabaseReference geoFireRef;
    private DatabaseReference userListingRef;
    private DatabaseReference locationsRef;
    private GeoFire geoFire;
    private GeoQuery geoQuery;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Map<String,Marker> markers;
    private FirebaseUser user;

    private String userKey;
    private String markerDetails;
    //IMPLEMENT THIS NEXT TIME
    private Marker selectedMarker;
    private Button browseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        //getActionBar().hide();
        setContentView(R.layout.activity_main);
        //getActionBar().show();

        browseButton = findViewById(R.id.btn_browse_listing);

        browseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedMarkerIsNull(selectedMarker) || selectedMarker.getTag() == null )  {
                    Toast.makeText(MainActivity.this, "Please Select a Listing",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    Intent paymentIntent = new Intent(MainActivity.this, BrowseListingPaymentActivity.class);
                    Listing selectedListing = (Listing) selectedMarker.getTag();
                    paymentIntent.putExtra("address", selectedListing.getAddress()); //title so far has address only, will cause problems later on with info window with extra details
                    paymentIntent.putExtra("price", selectedListing.getPrice());
                    paymentIntent.putExtra("description", selectedListing.getDescription());
                    paymentIntent.putExtra("spot_type", selectedListing.getSpotType());
                    paymentIntent.putExtra("start_date", selectedListing.getStartDate());
                    paymentIntent.putExtra("start_time", selectedListing.getStartTime());
                    paymentIntent.putExtra("end_date", selectedListing.getEndDate());
                    paymentIntent.putExtra("end_time", selectedListing.getEndTime());
                    paymentIntent.putExtra("creator_id", selectedListing.getUserID());
                    startActivity(paymentIntent);
                }
            }
        });

        //To Sign Out MIGHT NEED TO ADD AUTH LISTENER FROM PROFILE ACTIVITY
        auth = FirebaseAuth.getInstance();

        user = auth.getCurrentUser();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_map);
        mapFragment.getMapAsync(this);


        //Setting up database references
        userListingRef = database.getReference("Users");
        locationsRef = database.getReference("Locations");

        /**
         * Nav Menu
         */

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        geoFireRef = database.getReference("/geoFireListings");
        geoFire = new GeoFire(geoFireRef);
        geoQuery = geoFire.queryAtLocation(new GeoLocation(37.6786935, -122.1538643), 300);

        this.markers = new HashMap<>();

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near San Jose, California.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in California, San Jose and move/zoom the camera on create
        LatLng SanJose = new LatLng(37.3382, -121.8863);
        //mMap.addMarker(new MarkerOptions().position(SanJose).title("Test"));
        //higher the float value, the more zoomed in
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SanJose,8));


        /**
         * might need to implement later
         */
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point)  {

            }
        });

        mMap.setOnMarkerClickListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // remove all event listeners to stop updating in the background
        this.geoQuery.removeAllListeners();
        for (Marker marker: this.markers.values()) {
            marker.remove();
        }
        this.markers.clear();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // add an event listener to start updating locations again
        this.geoQuery.addGeoQueryEventListener(this);
    }

    //try nesting https://stackoverflow.com/questions/42176718/when-i-nest-two-value-event-listeners-do-they-both-run-asynchronously-or-the-th
    @Override
    public void onKeyEntered(String key, GeoLocation location) {
        // Add a new marker to the map
        final Marker marker = this.mMap.addMarker(new MarkerOptions().position(new LatLng(location.latitude, location.longitude)).title(key));
        final String address = key;
        locationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.child(address).child("Users").getChildren()) {
                    userKey = d.getKey();
                }
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        userListingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Listing post = snapshot.child(userKey).child("Listings").child(address).child("Details").getValue(Listing.class);
                    //CREATE INFO WINDOW, ADDED POST != null
                    if (post != null) {
                        markerDetails = post.toString();
                        marker.setSnippet(markerDetails);
                        //Tag is an object associated with the marker
                        marker.setTag(post);
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
        this.markers.put(key, marker);
    }

    @Override
    public void onKeyExited(String key) {
        System.out.println(String.format("Key %s is no longer in the search area", key));
        // Remove any old marker
        Marker marker = this.markers.get(key);
        if (marker != null) {
            marker.remove();
            this.markers.remove(key);
        }
    }

    @Override
    public void onKeyMoved(String key, GeoLocation location) {
        // Move the marker
    }

    @Override
    public void onGeoQueryReady() {
        System.out.println("All initial data has been loaded and events have been fired!");
    }

    @Override
    public void onGeoQueryError(DatabaseError error) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("There was an unexpected error querying GeoFire: " + error.getMessage())
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        //Add code to deal with a null marker later on
        // Retrieve the data from the marker.
        selectedMarker = marker;

        System.out.println("Test Marker: " + selectedMarker);
        /** Check if a click count was set, then display the click count.
         Integer clickCount = (Integer) marker.getTag();
         if (clickCount != null) {
         marker.setTag(clickCount);
         clickCount = clickCount + 1;
         Toast.makeText(this,
         marker.getTitle() +
         " has been clicked " + clickCount + " times.",
         Toast.LENGTH_SHORT).show();
         } */

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    /**
     * Nav Menu
     */

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //sign out method
    public void signOutButton() {

        auth.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public boolean selectedMarkerIsNull(Marker marker) {
        if (marker == null) return true;
        else return false;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
            //finish();

        } else if (id == R.id.nav_notifications) {

        } else if (id == R.id.nav_logout) {
            signOutButton();
            finish();

        } else if (id == R.id.nav_edit_profile) {

        } else if (id == R.id.nav_change_password) {

        } else if (id == R.id.nav_edit_email) {

        } else if (id == R.id.nav_edit_phone) {

        } else if (id == R.id.nav_payment_method) {

        } else if (id == R.id.nav_legal) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
