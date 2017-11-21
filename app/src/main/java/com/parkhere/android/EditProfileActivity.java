package com.parkhere.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity {

    private TextView editFirstName;
    private TextView editLastName;
    private Button saveButton;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userRef;

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        userRef = database.getReference().child("Users").child(user.getUid()).child("Profile");

        editFirstName = findViewById(R.id.edit_first_name);
        editLastName = findViewById(R.id.edit_last_name);

        saveButton = (Button) findViewById(R.id.save_changes);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editFirstName.getText().toString().isEmpty() && editLastName.getText().toString().isEmpty()) {
                    Toast.makeText(EditProfileActivity.this, "First or Last Name Can't Be Empty",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    if (editFirstName.getText().toString().isEmpty()) {
                        userRef.child("lastName").setValue(editLastName.getText().toString());
                    }
                    else if (editLastName.getText().toString().isEmpty()) {
                        userRef.child("firstName").setValue(editFirstName.getText().toString());
                    }
                    else {
                        userRef.child("lastName").setValue(editLastName.getText().toString());
                        userRef.child("firstName").setValue(editFirstName.getText().toString());
                    }
                    Toast.makeText(EditProfileActivity.this, "Profile has been changed successfully!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}