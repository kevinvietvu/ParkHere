package com.parkhere.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PhoneNumberActivity extends AppCompatActivity {

    private EditText phoneNumberInput;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private Button saveButton;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        saveButton = (Button) findViewById(R.id.save_changes);
        phoneNumberInput = findViewById(R.id.edit_phone_number);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        userRef = database.getReference("Users").child(user.getUid()).child("Information").child("phoneNumber");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Add phone number verification before uncommenting the database call
                //database call
                //userRef.setValue(phoneNumberInput.getText().toString());
                Toast.makeText(PhoneNumberActivity.this, "Phone number has been changed successfully!", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}