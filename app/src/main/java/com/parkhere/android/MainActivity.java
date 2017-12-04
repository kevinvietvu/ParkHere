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
import android.widget.Button;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Object;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GeoQueryEventListener, GoogleMap.OnMarkerClickListener
{

    public Bundle bundle;
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
    private int reservationCount;
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
                if (selectedMarkerIsNull(selectedMarker))  {
                    Toast.makeText(MainActivity.this, "Please Select a Listing",
                            Toast.LENGTH_LONG).show();
                }
                else if (selectedMarker.getTag() == null ) {
                    Toast.makeText(MainActivity.this, "Please Select a Listing",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    List<Listing> listings = (List<Listing>) selectedMarker.getTag();
                    if (listings.size() == 1) {
                        if (listings.get(0).getUserID().equals(user.getUid())) {
                            Toast.makeText(MainActivity.this, "Cannot Reserve Own Listing",
                                    Toast.LENGTH_LONG).show();
                        }
                        else {
                            Listing selectedListing = listings.get(0);
                            Intent paymentIntent = new Intent(MainActivity.this, BrowseListingPaymentActivity.class);
                            paymentIntent.putExtra("listing", selectedListing);
                            startActivity(paymentIntent);
                        }
                    }
                    else {
                        Intent browseMultipleListingsIntent = new Intent(MainActivity.this, BrowseMultipleListingsActivity.class);
                        browseMultipleListingsIntent.putParcelableArrayListExtra("listings", (ArrayList<Listing>) listings);
                        startActivity(browseMultipleListingsIntent);
                    }
                }
            }
        });

        //To Sign Out MIGHT NEED TO ADD AUTH LISTENER FROM PROFILE ACTIVITY
        auth = FirebaseAuth.getInstance();

        user = auth.getCurrentUser();

        //Setting up database references
        userListingRef = database.getReference("Users");
        locationsRef = database.getReference("Locations");

        geoFireRef = database.getReference("/geoFireListings");
        geoFire = new GeoFire(geoFireRef);

        bundle = getIntent().getExtras();
        if (bundle == null) {
            geoQuery = geoFire.queryAtLocation(new GeoLocation(37.6786935, -122.1538643), 300);
            markers = new HashMap<>();
        }
        else {
            geoQuery = geoFire.queryAtLocation(new GeoLocation((double) bundle.get("lat"), (double) bundle.get("lng")), 300);
            markers = new HashMap<>();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_map);
        mapFragment.getMapAsync(this);

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

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setHint("Search Area For Listings");

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("", "Place: " + place.getName());
                LatLng point = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
                if (geoQuery != null) {
                    geoQuery.removeAllListeners();
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,11));
                Intent intent = getIntent();
                intent.putExtra("lat", place.getLatLng().latitude);
                intent.putExtra("lng", place.getLatLng().longitude);
                finish();
                startActivity(intent);

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("", "An error occurred: " + status);
            }
        });
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
        bundle = getIntent().getExtras();
        if (bundle == null) {
            LatLng SanJose = new LatLng(37.3382, -121.8863);
            //mMap.addMarker(new MarkerOptions().position(SanJose).title("Test"));
            //higher the float value, the more zoomed in
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SanJose,9));
        }
        else {
            LatLng newLatLng = new LatLng( (double) bundle.get("lat"),(double) bundle.get("lng"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng,8));
        }

        /**
         * might need to implement later
         */
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point)  {

            }
        });

        mMap.setOnMarkerClickListener(this);

        mMap.setInfoWindowAdapter(new InfoWindowAdapter(this));

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                List<Listing> listings = (List<Listing>) marker.getTag();
                if (listings.size() == 1) {
                    Intent userProfile = new Intent(MainActivity.this, ProfileActivity.class);
                    userProfile.putExtra("userID", listings.get(0).getUserID());
                    userProfile.putExtra("address", listings.get(0).getAddress());
                    startActivity(userProfile);
                }

            }
        });
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
        final Marker marker = this.mMap.addMarker(new MarkerOptions()
                .position(new LatLng(location.latitude, location.longitude))
                .title(key)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        final String address = key;

        locationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final List<Listing> posts = new ArrayList<>();
                for (DataSnapshot d : snapshot.child(address).child("Users").getChildren()) {
                    final String userKey = d.getKey();
                    if (userKey != null) {
                        userListingRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot s : snapshot.child(userKey).child("Listings").child(address).getChildren()) {
                                        Listing post = s.child("Details").getValue(Listing.class);
                                        if (post != null) {
                                            if (posts.size() > 0) {
                                                markerDetails = "There are more than one listing for this parking spot \n Click on Rent Listing to view them all";
                                            }
                                            else {
                                                markerDetails = post.toString();
                                            }
                                            marker.setSnippet(markerDetails);
                                            //Tag is an object associated with the marker
                                            posts.add(post);
                                        }
                                    }
                                    reservationCount = Integer.parseInt(snapshot.child(userKey).child("ParkingSpots").child(address).child("Details").child("reservationCount").getValue().toString());
                                    if (reservationCount >= 10){
                                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                    }
                                    else if(reservationCount >= 5 && reservationCount < 10){
                                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                                    }
                                    marker.setTag(posts);
                                } else {
                                    System.out.println("userListing doesn't exist");
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError firebaseError) {
                                System.out.println("The read failed: " + firebaseError.getMessage());
                            }
                        });
                    }
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
        }
        else if (id == R.id.nav_logout) {
            signOutButton();
            finish();
        }
        else if (id == R.id.nav_edit_profile) {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_manage_listings) {
            Intent intent = new Intent(MainActivity.this, ManageListingsActivity.class);
            startActivity(intent);
            //finish();
        }
        else if (id == R.id.nav_change_password) {
            Intent intent = new Intent(MainActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_edit_email) {
            Intent intent = new Intent(MainActivity.this, ChangeEmailActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_edit_phone) {
            Intent intent = new Intent(MainActivity.this, PhoneNumberActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_payment_method) {
            Intent intent = new Intent(MainActivity.this, PaymentMethodActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_legal) {
            Intent intent = new Intent(MainActivity.this, LegalActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
