package com.parkhere.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class CreateActivity extends AppCompatActivity {

    private Button nextStep;
    private String price;
    private String description;
    private String spot_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        nextStep = findViewById(R.id.next_step);

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                price = ((EditText) findViewById(R.id.enter_price)).getText().toString();
                description = ((EditText) findViewById(R.id.enter_spot_description)).getText().toString();
                Spinner spinner = findViewById(R.id.choose_spot_type);
                spot_type = spinner.getSelectedItem().toString();

                Intent intent = new Intent(CreateActivity.this, ChooseStartDateActivity.class);
                intent.putExtra("price", price);
                intent.putExtra("description", description);
                intent.putExtra("spot_type", spot_type);

                startActivity(intent);
            }
        });
    }
}
