package com.parkhere.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateListingDetailsActivity extends AppCompatActivity {

    private Bundle bundle;
    private Button nextStep;
    private Double price;
    private String description;
    private String spot_type;

    public static CreateListingDetailsActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_create_listing_details);
        nextStep = findViewById(R.id.next_step);

        bundle = getIntent().getExtras();

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String test = ((EditText) findViewById(R.id.enter_price)).getText().toString();
                if (test.isEmpty()) {
                    Toast.makeText(CreateListingDetailsActivity.this, "Please enter a value for price", Toast.LENGTH_LONG).show();
                }
                else {
                    price = Double.parseDouble(((EditText) findViewById(R.id.enter_price)).getText().toString());
                    description = ((EditText) findViewById(R.id.enter_spot_description)).getText().toString();
                    Spinner spinner = findViewById(R.id.choose_spot_type);
                    spot_type = spinner.getSelectedItem().toString();
                    if (!priceIsNotNull(price)) {
                        Toast.makeText(CreateListingDetailsActivity.this, "Please enter a value for price", Toast.LENGTH_LONG).show();
                    } else if (!priceMustBeBetween1And999(price)) {
                        Toast.makeText(CreateListingDetailsActivity.this, "Please enter a price between 1 and 999", Toast.LENGTH_LONG).show();
                    } else if (!descriptionMustBeLessThan140Characters(description)) {
                        Toast.makeText(CreateListingDetailsActivity.this, "Please enter less than 140 characters for description", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(CreateListingDetailsActivity.this, CreateListingStartDateActivity.class);
                        intent.putExtra("price", price);
                        intent.putExtra("description", description);
                        intent.putExtra("spot_type", spot_type);
                        intent.putExtras(bundle);

                        startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        instance = null;
    }

    public static boolean priceIsNotNull(Object o) {
        if (o != null) return true;
        else return false;
    }

    public static boolean priceMustBeBetween1And999(Double price) {
        if (price >= 1.0 && price <= 999.0) return true;
        return false;
    }

    public static boolean descriptionMustBeLessThan140Characters(String description) {
        if (description.length() <= 140) return true;
        return false;
    }

}