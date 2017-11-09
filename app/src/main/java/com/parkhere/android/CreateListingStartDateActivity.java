package com.parkhere.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateListingStartDateActivity extends AppCompatActivity {

    private Button nextStep;
    private Bundle bundle;
    private String date;

    public static CreateListingStartDateActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_create_listing_start_date);
        nextStep = findViewById(R.id.next_step);

        bundle = getIntent().getExtras();

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = findViewById(R.id.datePicker);
                int m = datePicker.getMonth() + 1;
                int d = datePicker.getDayOfMonth();
                int y = datePicker.getYear() % 100;

                if (!startsOnOrAfterCurrentDate(m, d, y)) {
                    Toast.makeText(CreateListingStartDateActivity.this, "Please select a valid date", Toast.LENGTH_LONG).show();
                } else {
                    String month = String.format("%02d", m);
                    String day = String.format("%02d", d);
                    String year = String.format("%02d", y);
                    date = month + "-" + day + "-" + year;

                    Intent intent = new Intent(CreateListingStartDateActivity.this, CreateListingStartTimeActivity.class);
                    intent.putExtras(bundle);
                    intent.putExtra("start_date", date);
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

    public static boolean startsOnOrAfterCurrentDate(int month, int day, int year) {
        String formattedMonth = String.format("%02d", month);
        String formattedDay = String.format("%02d", day);
        String formattedYear = String.format("%02d", year);
        String start_date = formattedMonth + "-" + formattedDay + "-" + formattedYear;

        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yy");
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

                if (startDay >= currentDay) return true;
                else return false;
            }
        }
    }
}