package com.parkhere.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class CreateListingDetailsActivity extends AppCompatActivity {

    private Bundle bundle;
    private Button nextStep;
    private String price;
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
                price = ((EditText) findViewById(R.id.enter_price)).getText().toString();
                description = ((EditText) findViewById(R.id.enter_spot_description)).getText().toString();
                Spinner spinner = findViewById(R.id.choose_spot_type);
                spot_type = spinner.getSelectedItem().toString();

                Intent intent = new Intent(CreateListingDetailsActivity.this, CreateListingStartDateActivity.class);
                intent.putExtra("price", price);
                intent.putExtra("description", description);
                intent.putExtra("spot_type", spot_type);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        instance = null;
    }
}