package com.parkhere.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

public class CreateListingEndDateActivity extends AppCompatActivity {

    private Button nextStep;
    private Bundle bundle;
    private String date;

    public static CreateListingEndDateActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_create_listing_end_date);
        nextStep = findViewById(R.id.next_step);

        bundle = getIntent().getExtras();

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = findViewById(R.id.datePicker);
                String month = String.format("%02d", datePicker.getMonth() + 1);
                String day = String.format("%02d", datePicker.getDayOfMonth());
                String year = String.format("%02d", datePicker.getYear());
                date = month + "-" + day + "-" + year;

                Intent intent = new Intent(CreateListingEndDateActivity.this, CreateListingEndTimeActivity.class);
                intent.putExtras(bundle);
                intent.putExtra("end_date", date);
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