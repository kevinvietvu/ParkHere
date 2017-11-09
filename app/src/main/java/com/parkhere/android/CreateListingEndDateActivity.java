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

public class CreateListingEndDateActivity extends AppCompatActivity {

    private Button nextStep;
    private Bundle bundle;
    private String end_date;

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
                String date = bundle.getString("start_date");
                int startMonth = Integer.parseInt(date.substring(0, 2));
                int startDay = Integer.parseInt(date.substring(3, 5));
                int startYear = Integer.parseInt(date.substring(6, 8));

                DatePicker datePicker = findViewById(R.id.datePicker);
                int m = datePicker.getMonth() + 1;
                int d = datePicker.getDayOfMonth();
                int y = datePicker.getYear() % 100;

                if (!endsOnOrAfterStartDate(startMonth, startDay, startYear, m, d, y)) {
                    Toast.makeText(CreateListingEndDateActivity.this, "Please select a valid date", Toast.LENGTH_LONG).show();
                } else if (!isNotLongerThan2Years(startYear, y)) {
                    Toast.makeText(CreateListingEndDateActivity.this, "Please do not exceed 2 years", Toast.LENGTH_LONG).show();
                } else {
                    String month = String.format("%02d",m);
                    String day = String.format("%02d", d);
                    String year = String.format("%02d", y);
                    end_date = month + "-" + day + "-" + year;

                    Intent intent = new Intent(CreateListingEndDateActivity.this, CreateListingEndTimeActivity.class);
                    intent.putExtras(bundle);
                    intent.putExtra("end_date", end_date);
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

    public static boolean endsOnOrAfterStartDate(int startMonth, int startDay, int startYear, int endMonth, int endDay, int endYear) {
        String formattedStartMonth = String.format("%02d", startMonth);
        String formattedStartDay = String.format("%02d", startDay);
        String formattedStartYear = String.format("%02d", startYear);
        String startDate = formattedStartMonth + "-" + formattedStartDay + "-" + formattedStartYear;

        String formattedEndMonth = String.format("%02d", endMonth);
        String formattedEndDay = String.format("%02d", endDay);
        String formattedEndYear = String.format("%02d", endYear);
        String endDate = formattedEndMonth + "-" + formattedEndDay + "-" + formattedEndYear;

        int endYear2 = Integer.parseInt(endDate.substring(6, 8));
        int startYear2 =  Integer.parseInt(startDate.substring(6, 8));

        if (endYear2 > startYear2) return true;
        else if (endYear2 < startYear2) return false;
        else {
            int endMonth2 = Integer.parseInt(endDate.substring(0, 2));
            int startMonth2 = Integer.parseInt(startDate.substring(0, 2));

            if (endMonth2 > startMonth2) return true;
            else if (endMonth2 < startMonth2) return false;
            else {
                int endDay2 = Integer.parseInt(endDate.substring(3, 5));
                int startDay2 = Integer.parseInt(startDate.substring(3, 5));

                if (endDay2 >= startDay2) return true;
                else return false;
            }
        }
    }

    public static boolean isNotLongerThan2Years(int startYear, int endYear) {
        String formattedStartYear = String.format("%02d", startYear);
        String formattedEndYear = String.format("%02d", endYear);

        int startYearNum = Integer.parseInt(formattedStartYear);
        int endYearNum = Integer.parseInt(formattedEndYear);

        int yearDifference = Math.abs(startYearNum - endYearNum);

        if (yearDifference <= 2) return true;
        return false;
    }
}