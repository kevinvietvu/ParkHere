package com.parkhere.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateListingStartTimeActivity extends AppCompatActivity {

    private Button nextStep;
    private Bundle bundle;
    private String time;

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

    @Override
    public void finish() {
        super.finish();
        instance = null;
    }

    public static boolean startsOnOrAfterCurrentDateAndTime(String month, String day, String year,
                                                 String hour, String minute) {
        String formattedMonth = String.format("%02d", month);
        String formattedDay = String.format("%02d", day);
        String formattedYear = String.format("%02d", year);
        String formattedHour = String.format("%02d", hour);
        String formattedMinute = String.format("%02d", minute);
        String chosenDate = formattedMonth + "-" + formattedDay + "-" + formattedYear + " " + formattedHour + ":" + formattedMinute;

        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yy HH:mm");
        String currentDate = dateFormat.format(Calendar.getInstance().getTime());

        int chosenYear =  Integer.parseInt(chosenDate.substring(7, 9));
        int currentYear = Integer.parseInt(currentDate.substring(7, 9));

        if (chosenYear >= currentYear) {
            int chosenMonth = Integer.parseInt(chosenDate.substring(0, 2));
            int currentMonth = Integer.parseInt(currentDate.substring(0, 2));

            if (chosenMonth >= currentMonth) {
                int chosenDay = Integer.parseInt(chosenDate.substring(4, 6));
                int currentDay = Integer.parseInt(currentDate.substring(4, 6));

                if (chosenDay >= currentDay) {
                    int chosenHour =  Integer.parseInt(chosenDate.substring(9, 11));
                    int currentHour = Integer.parseInt(currentDate.substring(9, 11));

                    if (chosenHour >= currentHour) {
                        int chosenMinute =  Integer.parseInt(chosenDate.substring(12, 14));
                        int currentMinute = Integer.parseInt(currentDate.substring(12, 14));

                        if (chosenMinute >= currentMinute) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}