package com.parkhere.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateListingStartTimeActivity extends AppCompatActivity {

    private Button nextStep;
    private Bundle bundle;
    private String start_time;

    public static CreateListingStartTimeActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_create_start_time);
        nextStep = findViewById(R.id.next_step);

        bundle = getIntent().getExtras();

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = bundle.getString("start_date");
                int month = Integer.parseInt(date.substring(0, 2));
                int day = Integer.parseInt(date.substring(3, 5));
                int year = Integer.parseInt(date.substring(6, 8));

                TimePicker timePicker = findViewById(R.id.timePicker);
                int h = timePicker.getCurrentHour();
                int m = timePicker.getCurrentMinute();

                if (!startsOnOrAfterCurrentDateAndTime(month, day, year, h, m)) {
                    Toast.makeText(CreateListingStartTimeActivity.this, "Please select a valid time", Toast.LENGTH_LONG).show();
                } else {
                    String hour = String.format("%02d", h);
                    String minute = String.format("%02d", m);
                    start_time = hour + ":" + minute;

                    Intent intent = new Intent(CreateListingStartTimeActivity.this, CreateListingEndDateActivity.class);
                    intent.putExtras(bundle);
                    intent.putExtra("start_time", start_time);
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

    public static boolean startsOnOrAfterCurrentDateAndTime(int month, int day, int year, int hour, int minute) {
        String formattedMonth = String.format("%02d", month);
        String formattedDay = String.format("%02d", day);
        String formattedYear = String.format("%02d", year);
        String formattedHour = String.format("%02d", hour);
        String formattedMinute = String.format("%02d", minute);
        String start_date = formattedMonth + "-" + formattedDay + "-" + formattedYear + " " + formattedHour + ":" + formattedMinute;

        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yy HH:mm");
        String current_date = dateFormat.format(Calendar.getInstance().getTime());

        int startYear =  Integer.parseInt(start_date.substring(6, 8));
        int currentYear = Integer.parseInt(current_date.substring(6, 8));

        if (startYear > currentYear) return true;
        else if (startYear < currentYear) return false;
        else {
            int startMonth = Integer.parseInt(start_date.substring(0, 2));
            int currentMonth = Integer.parseInt(current_date.substring(0, 2));

            if (startMonth > currentMonth) return true;
            else if (startMonth < currentMonth) return false;
            else {
                int startDay = Integer.parseInt(start_date.substring(3, 5));
                int currentDay = Integer.parseInt(current_date.substring(3, 5));

                if (startDay > currentDay) return true;
                else if (startDay < currentDay) return false;
                else {
                    int startHour =  Integer.parseInt(start_date.substring(9, 11));
                    int currentHour = Integer.parseInt(current_date.substring(9, 11));

                    if (startHour > currentHour) return true;
                    else if (startHour < currentHour) return false;
                    else {
                        int startMinute =  Integer.parseInt(start_date.substring(12, 14));
                        int currentMinute = Integer.parseInt(current_date.substring(12, 14));

                        if (startMinute >= currentMinute) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
    }
}