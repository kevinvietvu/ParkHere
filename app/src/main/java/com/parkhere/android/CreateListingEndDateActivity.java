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

    public static boolean endsOnOrAfterStartDate(String startMonth, String startDay, String startYear,
                                        String endMonth, String endDay, String endYear) {
        String formattedStartMonth = String.format("%02d", startMonth);
        String formattedStartDay = String.format("%02d", startDay);
        String formattedStartYear = String.format("%02d", startYear);
        String startDate = formattedStartMonth + "-" + formattedStartDay + "-" + formattedStartYear;

        String formattedEndMonth = String.format("%02d", endMonth);
        String formattedEndDay = String.format("%02d", endDay);
        String formattedEndYear = String.format("%02d", endYear);
        String endDate = formattedEndMonth + "-" + formattedEndDay + "-" + formattedEndYear;


        int startYear2 =  Integer.parseInt(startDate.substring(7, 9));
        int endYear2 = Integer.parseInt(endDate.substring(7, 9));

            if (startYear2 >= endYear2) {
                int startMonth2 = Integer.parseInt(startDate.substring(0, 2));
                int endMonth2 = Integer.parseInt(endDate.substring(0, 2));

                if (startMonth2 >= endMonth2) {
                    int startDay2 = Integer.parseInt(startDate.substring(4, 6));
                    int endDay2 = Integer.parseInt(endDate.substring(4, 6));
                    if (startDay2 >= endDay2) {
                        return true;
                    }
                }
            }
            return false;
        }

        public boolean isNotLongerThan2Years(String startYear, String endYear) {
        String formattedStartYear = String.format("%02d", startYear);
        String formattedEndYear = String.format("%02d", endYear);

        int startYearNum = Integer.parseInt(formattedStartYear);
        int endYearNum = Integer.parseInt(formattedEndYear);
        int yearDifference = Math.abs(startYearNum - endYearNum);

        if (yearDifference <= 2) return true;
        return false;
    }
}