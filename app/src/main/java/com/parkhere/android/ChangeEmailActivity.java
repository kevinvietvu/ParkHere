
package com.parkhere.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeEmailActivity extends AppCompatActivity {

    private EditText emailInput;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        emailInput = findViewById(R.id.edit_email);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        saveButton = (Button) findViewById(R.id.save_changes);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Add email verification before uncommenting the database call
                //database call
                //user.updateEmail(emailInput.getText().toString());
                Intent mapIntent = new Intent(ChangeEmailActivity.this, MainActivity.class);
                startActivity(mapIntent);
            }
        });
    }
}