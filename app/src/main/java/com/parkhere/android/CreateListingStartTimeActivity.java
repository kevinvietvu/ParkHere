package com.parkhere.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class CreateListingStartTimeActivity extends AppCompatActivity {

    private Button next_step;
    private Bundle bundle;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing_start_time);
        next_step = findViewById(R.id.next_step);

        bundle = getIntent().getExtras();

        next_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePicker timePicker = findViewById(R.id.timePicker);

                int h = timePicker.getCurrentHour();
                int m = timePicker.getCurrentMinute();
                String meridiem = "";

                /* checks if time should be AM or PM */
                if (h < 12) {
                    /* if hour is before 12, set time to AM */
                    meridiem = "AM";
                } else {
                    /* if hour is after 12, set time to PM and convert to 12-hour clock */
                    h = h % 12;
                    meridiem = "PM";
                }

                /* changes 0:00 to 12:00 */
                if (h == 0) { h = 12; }

                String hour = String.format("%d", h);
                String minute = String.format("%02d", m);
                time = hour + ":" + minute + " " + meridiem;

                Intent intent = new Intent(CreateListingStartTimeActivity.this, CreateListingEndDateActivity.class);
                intent.putExtras(bundle);
                intent.putExtra("start_time", time);
                startActivity(intent);
            }
        });
    }
}