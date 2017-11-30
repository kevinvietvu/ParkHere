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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SplitBookingStartDateActivity extends AppCompatActivity {
    private TextView header;
    private Bundle bundle;
    private Button nextStep;
    private DatePicker datePicker;
    private String date;

    public static SplitBookingStartDateActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_booking);
        datePicker = findViewById(R.id.datePicker);
        header = findViewById(R.id.split_booking_choose_start_date);
        header.setText("Choose Start Date");
        instance = this;

        nextStep = findViewById(R.id.next_step);

        bundle = getIntent().getExtras();
        final Listing listing = bundle.getParcelable("listing");
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        final String startDateString = listing.getStartDate();
        final String endDateString = listing.getEndDate();
        final Date startDate;
        Date endDate;
        try {
            startDate = df.parse(startDateString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            cal.add(Calendar.YEAR, 2000);
            datePicker.setMinDate(cal.getTimeInMillis());
            endDate = df.parse(endDateString);
            cal.setTime(endDate);
            cal.add(Calendar.YEAR, 2000);
            datePicker.setMaxDate(cal.getTimeInMillis());

        } catch (Exception e) {
            e.printStackTrace();
        }

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int m = datePicker.getMonth() + 1;
                int d = datePicker.getDayOfMonth();
                int y = datePicker.getYear() % 100;

                    String month = String.format("%02d", m);
                    String day = String.format("%02d", d);
                    String year = String.format("%02d", y);
                    date = month + "-" + day + "-" + year;

                Intent intent = new Intent(SplitBookingStartDateActivity.this, SplitBookingEndDateActivity.class);
                listing.startDate = date;
                intent.putExtra("listing", listing);
                intent.putExtra("original_start_date", startDateString);
                intent.putExtra("original_end_date", endDateString);
                startActivity(intent);
                /**
                try {

                    System.out.println("BOOLEAN : " + SplitBookingUtility.checkDoubleSplitDate("11-27-17", "11-30-17", "11-27-17", "11-29-17"));
                    System.out.println("");
                    System.out.println("BOOLEAN : " + SplitBookingUtility.checkDoubleSplitDate("11-29-17", "12-02-17", "11-30-17", "12-02-17"));
                    System.out.println("");
                    System.out.println("BOOLEAN : " + SplitBookingUtility.checkDoubleSplitDate("11-27-17", "11-30-17", "11-28-17", "11-29-17"));
                    System.out.println("");
                    System.out.println("BOOLEAN : " + SplitBookingUtility.checkDoubleSplitDate("11-29-17", "12-02-17", "11-30-17", "12-01-17"));
                    System.out.println("");
                    System.out.println("BOOLEAN : " + SplitBookingUtility.checkDoubleSplitDate("11-29-17", "12-02-17", "11-29-17", "12-02-17"));


                    SplitBookingUtility utility = new SplitBookingUtility();

                    System.out.println("");
                    System.out.println("BOOLEAN : " + utility.checkSingleSplitDate("11-28-17", "11-30-17", "11-28-17", "11-29-17"));

                    ArrayList<Listing> test = utility.singleSplitDate("11-28-17", "11-30-17", "11-28-17", "11-29-17");

                    for (Listing l : test) {
                        System.out.println("TEST 1 " + l.getStartDate() + " : " + l.getEndDate());
                        System.out.println("FLAG TEST " + l.getRenterID());
                    }

                    SplitBookingUtility utility2 = new SplitBookingUtility();
                    System.out.println("");
                    ArrayList<Listing> test2 = utility2.singleSplitDate("11-27-17", "11-30-17", "11-28-17", "11-30-17");

                    for (Listing l : test2) {
                        System.out.println("TEST 2 " + l.getStartDate() + " : " + l.getEndDate());
                        System.out.println("FLAG TEST " + l.getRenterID());
                    }

                    SplitBookingUtility utility3 = new SplitBookingUtility();
                    System.out.println("");
                    ArrayList<Listing> test3 = utility3.singleSplitDate("11-30-17", "12-03-17", "11-30-17", "12-01-17");

                    for (Listing l : test3) {
                        System.out.println("TEST 3 " + l.getStartDate() + " : " + l.getEndDate());
                        System.out.println("FLAG TEST " + l.getRenterID());
                    }

                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
*               */
            }

        });

    }

    @Override
    public void finish() {
        super.finish();
        instance = null;
    }


}
