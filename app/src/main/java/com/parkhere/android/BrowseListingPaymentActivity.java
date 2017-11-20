package com.parkhere.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BrowseListingPaymentActivity extends AppCompatActivity {

    public static BrowseListingPaymentActivity instance = null;
    public String card_number;
    public String cvv;
    public String vehicle_make;
    public String vehicle_model;
    public String vehicle_color;
    public String license_plate_number;
    private Button next_step;
    private Bundle bundle;

    public static boolean checkCardLengthBetween12And19(String card_number) {
        if (card_number.length() >= 12 && card_number.length() <= 19)
            return true;
        else
            return false;
    }

    public static boolean checkCVVLengthIs3(String cvv) {
        if (cvv.length() == 3)
            return true;
        else
            return false;
    }

    public static boolean vehicleInfoIsNotNull(Object o) {
        if (o != null) return true;
        else return false;
    }

    public static boolean checkIfLicensePlateNumberIsBetween1And7(String license_plate_number) {
        if (license_plate_number.length() >= 1 && license_plate_number.length() <= 7) return true;
        else return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_browse_listing_payment);
        next_step = findViewById(R.id.next_step);

        bundle = getIntent().getExtras();

        next_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_number = ((EditText) findViewById(R.id.enter_card_number)).getText().toString();
                cvv = ((EditText) findViewById(R.id.enter_cvv)).getText().toString();
                vehicle_make = ((EditText) findViewById(R.id.enter_vehicle_make)).getText().toString();
                vehicle_model = ((EditText) findViewById(R.id.enter_vehicle_model)).getText().toString();
                vehicle_color = ((EditText) findViewById(R.id.enter_vehicle_color)).getText().toString();
                license_plate_number = ((EditText) findViewById(R.id.enter_license_plate_number)).getText().toString();

                if (!checkCardLengthBetween12And19(card_number)) {
                    Toast.makeText(BrowseListingPaymentActivity.this, "Card Length must be between 12 and 19 digits",
                            Toast.LENGTH_LONG).show();
                }
                else if (!checkCVVLengthIs3(cvv)) {
                    Toast.makeText(BrowseListingPaymentActivity.this, "CVV must be 3 digits",
                            Toast.LENGTH_LONG).show();
                }
                else if (!vehicleInfoIsNotNull(vehicle_make)
                        || !vehicleInfoIsNotNull(vehicle_model)
                        || !vehicleInfoIsNotNull(vehicle_color)) {
                    Toast.makeText(BrowseListingPaymentActivity.this, "Please enter vehicle information",
                            Toast.LENGTH_LONG).show();
                }
                else if (!checkIfLicensePlateNumberIsBetween1And7(license_plate_number)) {
                    Toast.makeText(BrowseListingPaymentActivity.this, "Please enter a valid license plate number",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(BrowseListingPaymentActivity.this, BrowseListingConfirmActivity.class);

                    intent.putExtras(bundle);
                    intent.putExtra("card_number", card_number);
                    intent.putExtra("cvv", cvv);
                    intent.putExtra("car_make", vehicle_make);
                    intent.putExtra("car_model", vehicle_model);
                    intent.putExtra("car_color", vehicle_color);
                    intent.putExtra("license_plate_number", license_plate_number);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        instance = null;
    }
}