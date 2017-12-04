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

public class SplitBookingStartDateActivity extends AppCompatActivity {
    private TextView header;
    private Bundle bundle;
    private Button nextStep;
    private DatePicker datePicker;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_booking);
        datePicker = findViewById(R.id.datePicker);
        header = findViewById(R.id.split_booking_choose_start_date);
        header.setText("Choose Start Date");

        nextStep = findViewById(R.id.next_step);

        bundle = getIntent().getExtras();
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        String startDateString = bundle.getString("start_date");
        String endDateString = bundle.getString("end_date");
        System.out.println("TEST bundleDATE " + startDateString);
        System.out.println("TEST bundleDATE 2 " + endDateString);
        Date startDate;
        Date endDate;
        try {
            startDate = df.parse(startDateString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            endDate = df.parse(endDateString);
            System.out.println("TEST DATE " + cal.get(Calendar.YEAR));
            datePicker.setMinDate(cal.getTimeInMillis());
            cal.setTime(endDate);
            datePicker.setMaxDate(cal.getTimeInMillis());
        } catch (ParseException e) {
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
                intent.putExtras(bundle);
                intent.putExtra("start_date", date);
                startActivity(intent);
            }

        });

    }
}
