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

public class CreateListingEndTimeActivity extends AppCompatActivity {

    private Button nextStep;
    private Bundle bundle;
    private String time;


    public static CreateListingEndTimeActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_create_listing_end_time);
        nextStep = findViewById(R.id.next_step);

        bundle = getIntent().getExtras();

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start_date = bundle.getString("start_date");
                int startMonth = Integer.parseInt(start_date.substring(0, 2));
                int startDay = Integer.parseInt(start_date.substring(3, 5));
                int startYear = Integer.parseInt(start_date.substring(6, 8));

                String start_time = bundle.getString("start_time");
                int startHour = Integer.parseInt(start_time.substring(0, 2));
                int startMinute = Integer.parseInt(start_time.substring(3, 5));

                String end_date = bundle.getString("end_date");
                int endMonth = Integer.parseInt(end_date.substring(0, 2));
                int endDay = Integer.parseInt(end_date.substring(3, 5));
                int endYear = Integer.parseInt(end_date.substring(6, 8));

                TimePicker timePicker = findViewById(R.id.timePicker);

                if (!endsAfterStartDateAndTime(startMonth, startDay, startYear, startHour, startMinute,
                        endMonth, endDay, endYear, timePicker.getCurrentHour(), timePicker.getCurrentMinute())) {
                    Toast.makeText(CreateListingEndTimeActivity.this, "Please select a valid time", Toast.LENGTH_LONG).show();
                } else if (!isLongerThan1Hour(startHour, startMinute, timePicker.getCurrentHour(), timePicker.getCurrentMinute())) {
                    Toast.makeText(CreateListingEndTimeActivity.this, "Please make sure your listing is at least one hour", Toast.LENGTH_LONG).show();
                } else {
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

                    Intent intent = new Intent(CreateListingEndTimeActivity.this, CreateListingConfirmActivity.class);
                    intent.putExtras(bundle);
                    intent.putExtra("end_time", time);
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

    public static boolean endsAfterStartDateAndTime(int startMonth, int startDay, int startYear, int startHour, int startMinute,
                                               int endMonth, int endDay, int endYear, int endHour, int endMinute) {
        String formattedStartMonth = String.format("%02d", startMonth);
        String formattedStartDay = String.format("%02d", startDay);
        String formattedStartYear = String.format("%02d", startYear);
        String formattedStartHour = String.format("%02d", startHour);
        String formattedStartMinute = String.format("%02d", startMinute);
        String startDate = formattedStartMonth + "-" + formattedStartDay + "-" + formattedStartYear + " " + formattedStartHour + ":" + formattedStartMinute;

        String formattedEndMonth = String.format("%02d", endMonth);
        String formattedEndDay = String.format("%02d", endDay);
        String formattedEndYear = String.format("%02d", endYear);
        String formattedEndHour = String.format("%02d", endHour);
        String formattedEndMinute = String.format("%02d", endMinute);
        String endDate = formattedEndMonth + "-" + formattedEndDay + "-" + formattedEndYear + " " + formattedEndHour + ":" + formattedEndMinute;

        int endYear2 = Integer.parseInt(endDate.substring(6, 8));
        int startYear2 =  Integer.parseInt(startDate.substring(6, 8));

        if (endYear2 > startYear2) return true;
        else if (endYear2 < startYear2) return false;
        else {
            int startMonth2 = Integer.parseInt(startDate.substring(0, 2));
            int endMonth2 = Integer.parseInt(endDate.substring(0, 2));

            if (endMonth2 > startMonth2) return true;
            else if (endMonth2 < startMonth2) return false;
            else {
                int startDay2 = Integer.parseInt(startDate.substring(3, 5));
                int endDay2 = Integer.parseInt(endDate.substring(3, 5));

                if (endDay2 > startDay2) return true;
                else if (endDay2 < startDay2) return false;
                else {
                    int startHour2 =  Integer.parseInt(startDate.substring(9, 11));
                    int endHour2 = Integer.parseInt(endDate.substring(9, 11));

                    if (endHour2 > startHour2) return true;
                    else if (endHour2 < startHour2) return false;
                    else {
                        int startMinute2 =  Integer.parseInt(startDate.substring(12, 14));
                        int endMinute2 = Integer.parseInt(endDate.substring(12, 14));

                        if (endMinute2 >= startMinute2) return true;
                        else return false;
                    }
                }
            }
        }
    }

    public static boolean isLongerThan1Hour(int startHour, int startMinute, int endHour, int endMinute) {
        String formattedStartHour = String.format("%02d", startHour);
        String formattedStartMinute = String.format("%02d", startMinute);
        String startDate = formattedStartHour + ":" + formattedStartMinute;

        String formattedEndHour = String.format("%02d", endHour);
        String formattedEndMinute = String.format("%02d", endMinute);
        String endDate = formattedEndHour + ":" + formattedEndMinute;

        int startHour2 = Integer.parseInt(startDate.substring(0, 2));
        int startMinute2 = Integer.parseInt(startDate.substring(3, 5));

        int endHour2 = Integer.parseInt(endDate.substring(0, 2));
        int endMinute2 = Integer.parseInt(endDate.substring(3, 5));

        int hourDifference = Math.abs(startHour2 - endHour2);
        int minuteDifference = Math.abs(startMinute2 - endMinute2);
        int timeLengthInMinutes = (hourDifference * 60) + minuteDifference;

        if (timeLengthInMinutes >= 60) {
            return true;
        }
        return false;
    }
}