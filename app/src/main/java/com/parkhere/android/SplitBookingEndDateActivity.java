package com.parkhere.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SplitBookingEndDateActivity extends AppCompatActivity {
    private TextView header;
    private Bundle bundle;
    private DatePicker datePicker;
    private Button nextStep;
    private String date;

    public static SplitBookingEndDateActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_split_booking);
        nextStep = findViewById(R.id.next_step);
        datePicker = findViewById(R.id.datePicker);
        header = findViewById(R.id.split_booking_choose_start_date);
        header.setText("Choose End Date");
        bundle = getIntent().getExtras();
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");

        final Listing listing = bundle.getParcelable("listing");
        String startDateString = listing.getStartDate();
        String endDateString = listing.getEndDate();

        Date startDate;
        Date endDate;
        try {
            startDate = df.parse(startDateString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            cal.add(Calendar.YEAR, 2000);
            System.out.println("YEAR : " + cal.getTime());
            datePicker.setMinDate(cal.getTimeInMillis());
            endDate = df.parse(endDateString);
            cal.setTime(endDate);
            cal.add(Calendar.YEAR, 2000);
            System.out.println("YEAR 2  : " + cal.getTime());
            datePicker.setMaxDate(cal.getTimeInMillis());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = findViewById(R.id.datePicker);
                int m = datePicker.getMonth() + 1;
                int d = datePicker.getDayOfMonth();
                int y = datePicker.getYear() % 100;

                if (!startsOnOrAfterCurrentDate(m, d, y)) {
                    Toast.makeText(SplitBookingEndDateActivity.this, "Please select a valid date", Toast.LENGTH_LONG).show();
                } else {
                    String month = String.format("%02d", m);
                    String day = String.format("%02d", d);
                    String year = String.format("%02d", y);
                    date = month + "-" + day + "-" + year;

                    Intent intent = new Intent(SplitBookingEndDateActivity.this, BrowseListingPaymentActivity.class);
                    listing.endDate = date;
                    intent.putExtras(bundle);
                    intent.putExtra("listing", listing);
                    startActivity(intent);
                }
            }
        });
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

    @Override
    public void finish() {
        super.finish();
        instance = null;
    }


}
