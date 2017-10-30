package com.parkhere.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserActivity extends AppCompatActivity {

    private Button  signOutButton;
    private Button  createListingButton;
    private Button  deleteListingButton;
    private TextView helloUserText;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        signOutButton = (Button) findViewById(R.id.sign_out);
        createListingButton = (Button) findViewById(R.id.create_listing);
        deleteListingButton = (Button) findViewById(R.id.delete_listing);

        helloUserText = (TextView) findViewById(R.id.text_user);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // if user is null launch login activity
                    startActivity(new Intent(UserActivity.this, LoginActivity.class));
                    finish();
                }else{
                    helloUserText.setText("Hello  " + user.getEmail() +"");
                }
            }
        };

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutButton();
            }
        });

        createListingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.setValue(auth.getCurrentUser().getEmail());
            }
        });

        deleteListingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.removeValue();
            }
        });

    }

    //sign out method
    public void signOutButton() {
        auth.signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}