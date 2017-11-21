package com.parkhere.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText passwordInput;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        saveButton = (Button) findViewById(R.id.save_changes);
        passwordInput = findViewById(R.id.edit_confirm_password);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Add password verification before uncommenting  the database call
                //database call
                //user.updateEmail(passwordInput.getText().toString());
                Intent mapIntent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                startActivity(mapIntent);
            }
        });
    }
}