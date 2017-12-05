package com.parkhere.android;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText passwordInput;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private Button saveButton;

    private EditText editInputPassword;
    private EditText editInputConfirmPassword;
    private TextInputLayout  editInputLayoutPassword;
    private TextInputLayout  editInputLayoutConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        editInputLayoutPassword = (TextInputLayout) findViewById(R.id.edit_input_layout_new_password);
        editInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.edit_input_layout_confirm_password);


        editInputPassword = (EditText) findViewById(R.id.edit_input_new_password);
        editInputConfirmPassword = (EditText) findViewById(R.id.edit_input_confirm_password);

        saveButton = (Button) findViewById(R.id.save_changes);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = editInputPassword.getText().toString().trim();

                if(!confirmPassword()) {
                    return;
                }
                editInputLayoutPassword.setErrorEnabled(false);
                //Add password verification before uncommenting  the database call
                //database call
                //user.updateEmail(passwordInput.getText().toString());
                Toast.makeText(ChangePasswordActivity.this, "Password has been changed successfully!", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }
    private boolean confirmPassword() {
        String password = editInputPassword.getText().toString().trim();
        String confirmPassword = editInputConfirmPassword.getText().toString().trim();
        if (confirmPassword.isEmpty() || !password.equals(confirmPassword)) {
            editInputLayoutConfirmPassword.setError(getString(R.string.err_msg_confirm_password));
            editInputConfirmPassword.setError(getString(R.string.err_msg_required));
            requestFocus(editInputConfirmPassword);
            return false;
        }
        editInputLayoutConfirmPassword.setErrorEnabled(false);
        return true;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}