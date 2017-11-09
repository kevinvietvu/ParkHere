package com.parkhere.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BrowseListingPaymentActivity extends AppCompatActivity {

    private Button next_step;
    private Bundle bundle;
    public String card_number;
    public String cvv;

    public static BrowseListingPaymentActivity instance = null;

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

                Intent intent = new Intent(BrowseListingPaymentActivity.this, BrowseListingConfirmActivity.class);

                intent.putExtras(bundle);
                intent.putExtra("card_number", card_number);
                intent.putExtra("cvv", cvv);
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