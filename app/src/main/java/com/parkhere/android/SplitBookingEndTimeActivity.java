package com.parkhere.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import java.util.ArrayList;

public class SplitBookingEndTimeActivity extends AppCompatActivity {
    String[] times = {"12AM", "1AM", "2AM", "3AM", "4AM", "5AM", "6AM", "7AM", "8AM", "9AM", "10AM", "11AM", "12PM", "1PM", "2PM",
            "3PM", "4PM", "5PM", "6PM", "7PM", "8PM", "9PM", "10PM", "11PM" };
    NumberPicker startTimePicker;
    Button nextStep;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_booking_end_time);

        bundle = getIntent().getExtras();
        final Listing listing = bundle.getParcelable("listing");

        nextStep = findViewById(R.id.pick_start_time_btn);
        startTimePicker = findViewById(R.id.numberPicker);

        String chosenStartTime = listing.getStartTime();
        String endTime = bundle.get("original_end_time").toString();

        ArrayList<String> list = new ArrayList<>();
        int indexOfStart = 0;
        for (int i = 0; i < times.length; i++) {
            if(times[i].equals(chosenStartTime))
                indexOfStart = (i + 1) % 24;
        }
        for (int i = indexOfStart; i < times.length; i++) {
            if  (times[i].equals("11PM")) {
                list.add(times[i]);
                i = -1;
            }
            else if(times[i].equals(endTime)) {
                list.add(times[i]);
                break;
            }
            else list.add(times[i]);
        }

        final String[] pickerTimes = list.toArray(new String[list.size()]);

        startTimePicker.setMinValue(0);
        startTimePicker.setMaxValue(pickerTimes.length - 1);
        startTimePicker.setDisplayedValues(pickerTimes);

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pickEndTime = new Intent(SplitBookingEndTimeActivity.this, BrowseListingPaymentActivity.class);
                listing.endTime = pickerTimes[startTimePicker.getValue()];
                pickEndTime.putExtras(bundle);
                pickEndTime.putExtra("listing", listing);
                pickEndTime.putExtra("original_start_date",listing.getStartDate());
                pickEndTime.putExtra("original_end_date", listing.getEndDate());
                System.out.println(listing.endTime);
                startActivity(pickEndTime);
            }
        });


    }
}
