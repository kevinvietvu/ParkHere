package com.parkhere.android;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

public class CreateListingMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private Button submitAddressButton;
    public static String placeToSend;

    public static CreateListingMapsActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_create_listing_maps);

        submitAddressButton = (Button) findViewById(R.id.address_submit_btn);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("", "Place: " + place.getName());
                LatLng point = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
                MarkerOptions marker = new MarkerOptions().position(new LatLng(point.latitude, point.longitude)).title("New Marker");
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(point));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,11));
                placeToSend = place.getAddress().toString();
                Log.d("testAddressInput", placeToSend);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("", "An error occurred: " + status);
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        submitAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addressIntent = new Intent(CreateListingMapsActivity.this, CreateListingDetailsActivity.class);
                if (addressIsNull(placeToSend)) {
                    Toast.makeText(CreateListingMapsActivity.this, "Please Enter an Address",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    addressIntent.putExtra("address", placeToSend);
                    startActivity(addressIntent);
                }
            }
        });

    }

    public static boolean addressIsNull(String address) {
        if (address == null || address.isEmpty())
            return true;
        else
            return false;
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
        mMap.addMarker(new MarkerOptions().position(SanJose).title("Marker in San Jose"));
        //higher the float value, the more zoomed in
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SanJose,11));


        /**
         * might need to implement later
         */
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point)  {

            }
        });
    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    @Override
    public void finish() {
        super.finish();
        instance = null;
    }

}
