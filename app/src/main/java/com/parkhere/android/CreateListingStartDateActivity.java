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
import java.util.Date;

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
                if (!startsOnOrAfterCurrentDate(datePicker.getMonth() + 1, datePicker.getDayOfMonth(), datePicker.getYear())) {
                    Toast.makeText(CreateListingStartDateActivity.this, "Please select a valid date", Toast.LENGTH_LONG).show();
                } else {
                    String month = String.format("%02d", datePicker.getMonth() + 1);
                    String day = String.format("%02d", datePicker.getDayOfMonth());
                    String year = String.format("%02d", datePicker.getYear());
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
        String chosenDate = formattedMonth + "-" + formattedDay + "-" + formattedYear;

        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yy");
        String currentDate = dateFormat.format(Calendar.getInstance().getTime());

        int chosenYear =  Integer.parseInt(chosenDate.substring(6, 8));
        int currentYear = Integer.parseInt(currentDate.substring(6, 8));

        if (chosenYear > currentYear) return true;
        else if (chosenYear < currentYear) return false;
        else {
            int chosenMonth = Integer.parseInt(chosenDate.substring(0, 2));
            int currentMonth = Integer.parseInt(currentDate.substring(0, 2));

            if (chosenMonth > currentMonth) return true;
            else if (chosenMonth < currentMonth) return false;
            else {
                int chosenDay = Integer.parseInt(chosenDate.substring(3, 5));
                int currentDay = Integer.parseInt(currentDate.substring(3, 5));

                if (chosenDay >= currentDay) return true;
                else return false;
            }
        }
    }
}