package com.parkhere.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import java.util.ArrayList;

public class SplitBookingStartTimeActivity extends AppCompatActivity {
    String[] times = {"12AM", "1AM", "2AM", "3AM", "4AM", "5AM", "6AM", "7AM", "8AM", "9AM", "10AM", "11AM", "12PM", "1PM", "2PM",
            "3PM", "4PM", "5PM", "6PM", "7PM", "8PM", "9PM", "10PM", "11PM" };
    NumberPicker startTimePicker;
    Button nextStep;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_booking_start_time);

        bundle = getIntent().getExtras();
        final Listing listing = bundle.getParcelable("listing");

        nextStep = findViewById(R.id.pick_start_time_btn);
        startTimePicker = findViewById(R.id.numberPicker);

        String startTimeString = listing.getStartTime();
        String endTimeString = listing.getEndTime();
        int startTimeInt;
        int endTimeInt;
        if (startTimeString.substring(0,2).equals("10") || startTimeString.substring(0,2).equals("11") || startTimeString.substring(0,2).equals("12"))
            startTimeInt = Integer.parseInt(startTimeString.substring(0,2));
        else startTimeInt = Integer.parseInt(startTimeString.substring(0,1));

        if (endTimeString.substring(0,2).equals("10") || endTimeString.substring(0,2).equals("11") || endTimeString.substring(0,2).equals("12"))
            endTimeInt = Integer.parseInt(endTimeString.substring(0,2));
        else endTimeInt = Integer.parseInt(endTimeString.substring(0,1));

        String amPmStart = startTimeString.substring(startTimeString.length() - 2, startTimeString.length());
        String amPmEnd = endTimeString.substring(endTimeString.length() - 2, endTimeString.length());
        System.out.println("TEST TIME START : " + startTimeInt + " | AM OR PM TEST : " + amPmStart);
        System.out.println("TEST TIME END : " + endTimeInt + " | AM OR PM TEST : " + amPmEnd);

        final String startTime = startTimeInt + amPmStart;
        final String endTime = endTimeInt + amPmEnd;
        ArrayList<String> list = new ArrayList<>();
        int indexOfStart = 0;
        for (int i = 0; i < times.length; i++) {
            if(times[i].equals(startTime))
                indexOfStart = i;
        }
        for (int i = indexOfStart; i < times.length; i++) {
            if (times[i].equals("11PM")) {
                list.add(times[i]);
                i = -1;
            }
            else if(times[i].equals(endTime)) {
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
                Intent pickEndTime = new Intent(SplitBookingStartTimeActivity.this, SplitBookingEndTimeActivity.class);
                listing.startTime = pickerTimes[startTimePicker.getValue()];
                pickEndTime.putExtra("listing", listing);
                pickEndTime.putExtra("original_start_time", startTime);
                pickEndTime.putExtra("original_end_time", endTime);
                System.out.println(listing.startTime);
                startActivity(pickEndTime);
            }
        });

    }
}
