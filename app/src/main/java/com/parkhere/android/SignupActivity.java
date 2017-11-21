package com.parkhere.android;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity" ;
    public static SignupActivity instance = null;
    private Button btnSignUp, btnLinkToLogIn;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private EditText signupInputEmail, signupInputPassword;
    private EditText signupInputConfirmPassword, signupInputDriversId, signupInputPhoneNumber;
    private TextInputLayout  signupInputLayoutEmail, signupInputLayoutPassword;
    private TextInputLayout  signupInputLayoutConfirmPassword, signupInputLayoutDriversId, signupInputLayoutPhoneNumber;


    public static boolean isEmailValid(String email) {
        //String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        //        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        //return !TextUtils.isEmpty(email) && email.matches(emailPattern);
    }

    public static boolean isPasswordValid(String password){
        if(password == null) return false;
        if(password.length() < 8) return false;

        boolean checkUpperCase = false;
        boolean checkLowerCase = false;
        boolean checkDigit = false;

        for(char ch: password.toCharArray()){
            if(Character.isUpperCase(ch)) checkUpperCase = true;
            if(Character.isLowerCase(ch)) checkLowerCase = true;
            if(Character.isDigit(ch)) checkDigit = true;
        }
        return (checkUpperCase && checkLowerCase && checkDigit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        instance = this;

        auth = FirebaseAuth.getInstance();

        signupInputLayoutEmail = (TextInputLayout) findViewById(R.id.signup_input_layout_email);
        signupInputLayoutPassword = (TextInputLayout) findViewById(R.id.signup_input_layout_password);
        signupInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.signup_input__layout_confirm_password);
        signupInputLayoutDriversId = (TextInputLayout) findViewById(R.id.signup_input_layout_drivers_id);
        signupInputLayoutPhoneNumber = (TextInputLayout) findViewById(R.id.signup_input_layout_phone_number);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        signupInputEmail = (EditText) findViewById(R.id.signup_input_email);
        signupInputPassword = (EditText) findViewById(R.id.signup_input_password);
        signupInputConfirmPassword = (EditText) findViewById(R.id.signup_input_confirm_password);
        signupInputDriversId = (EditText) findViewById(R.id.signup_input_drivers_id);
        signupInputPhoneNumber = (EditText) findViewById(R.id.signup_input_phone_number);

        btnSignUp = (Button) findViewById(R.id.btn_signup);
        btnLinkToLogIn = (Button) findViewById(R.id.btn_link_login);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();

            }
        });

        btnLinkToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Validating form
     */
    private void submitForm() {
        String email = signupInputEmail.getText().toString().trim();
        String password = signupInputPassword.getText().toString().trim();

        if(!checkEmail()) {
            return;
        }
        if(!checkPassword()) {
            return;
        }
        if(!confirmPassword()) {
            return;
        }
        if(!driversId()) {
            return;
        }
        if(!phoneNumber()) {
            return;
        }

        findViewById(R.id.btn_signup).setEnabled(false);

        signupInputLayoutEmail.setErrorEnabled(false);
        signupInputLayoutPassword.setErrorEnabled(false);

        progressBar.setVisibility(View.VISIBLE);
        //create user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d(TAG,"createUserWithEmail:onComplete:" + task.isSuccessful());
                        progressBar.setVisibility(View.GONE);
                        // If sign in fails, Log the message to the LogCat. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.


                        if (!task.isSuccessful()) {
                            Log.d(TAG,"Authentication failed." + task.getException());

                        } else {
                            sendEmailVerification();
                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                            if(LoginActivity.instance != null) {
                                try {
                                    LoginActivity.instance.finish();
                                } catch (Exception e) {}
                            }
                        }
                    }
                });
        Toast.makeText(getApplicationContext(), "You are successfully Registered !!", Toast.LENGTH_SHORT).show();
    }


    private boolean checkEmail() {
        String email = signupInputEmail.getText().toString().trim();
        if (email.isEmpty() || !isEmailValid(email)) {

            signupInputLayoutEmail.setErrorEnabled(true);
            signupInputLayoutEmail.setError(getString(R.string.err_msg_email));
            signupInputLayoutEmail.setError(getString(R.string.err_msg_email));
            signupInputEmail.setError(getString(R.string.err_msg_required));
            requestFocus(signupInputEmail);
            return false;
        }

        signupInputLayoutEmail.setErrorEnabled(false);
        return true;
    }


    //Send Email Verification
    public void sendEmailVerification() {
        final FirebaseUser user = auth.getCurrentUser();

        // [START send_email_verification]
            user.sendEmailVerification()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // [START_EXCLUDE]
                            // Re-enable button
                            findViewById(R.id.btn_signup).setEnabled(true);

                            if (task.isSuccessful()) {
                                Toast.makeText(SignupActivity.this,
                                        "Verification email sent to " + user.getEmail(),
                                        Toast.LENGTH_SHORT).show();

                            } else {
                                Log.e(TAG, "sendEmailVerification", task.getException());
                                Toast.makeText(SignupActivity.this,
                                        "Failed to send verification email.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            // [END_EXCLUDE]
                        }
                    });


        // [END send_email_verification]
    }


    private boolean checkPassword() {

        String password = signupInputPassword.getText().toString().trim();
        if (password.isEmpty() || !isPasswordValid(password)) {

            signupInputLayoutPassword.setError(getString(R.string.err_msg_password));
            signupInputPassword.setError(getString(R.string.err_msg_required));
            requestFocus(signupInputPassword);
            return false;
        }
        signupInputLayoutPassword.setErrorEnabled(false);
        return true;
    }

    private boolean confirmPassword() {

        String password = signupInputPassword.getText().toString().trim();
        String confirmPassword = signupInputConfirmPassword.getText().toString().trim();
        if (confirmPassword.isEmpty() || !password.equals(confirmPassword)) {
            signupInputLayoutConfirmPassword.setError(getString(R.string.err_msg_confirm_password));
            signupInputConfirmPassword.setError(getString(R.string.err_msg_required));
            requestFocus(signupInputConfirmPassword);
            return false;
        }
        signupInputLayoutConfirmPassword.setErrorEnabled(false);
        return true;
    }

    private boolean driversId() {

        //California Drivers Licence

        String driversId = signupInputDriversId.getText().toString().trim();
        String valid_id = "^[A-Z]{1}[0-9]{7}$";
        if (driversId.isEmpty() || !driversId.matches(valid_id)) {

            signupInputLayoutDriversId.setErrorEnabled(true);
            signupInputLayoutDriversId.setError(getString(R.string.err_msg_drivers_id));
            signupInputDriversId.setError(getString(R.string.err_msg_required));
            requestFocus(signupInputDriversId);
            return false;
        }

        signupInputLayoutEmail.setErrorEnabled(false);
        return true;
    }

    private boolean phoneNumber() {
        // \d{10} matches 1234567890
        // (?:\d{3}-){2}\d{4} matches 123-456-7890
        // \(\d{3}\)\d{3}-?\d{4} matches (123)456-7890 or (123)4567890

        String phone = signupInputPhoneNumber.getText().toString().trim();
        String valid_phone = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
        if (phone.isEmpty() || !phone.matches(valid_phone)) {

            signupInputLayoutPhoneNumber.setErrorEnabled(true);
            signupInputLayoutPhoneNumber.setError(getString(R.string.err_msg_phone_number));
            signupInputPhoneNumber.setError(getString(R.string.err_msg_required));
            requestFocus(signupInputPhoneNumber);
            return false;
        }

        signupInputLayoutPhoneNumber.setErrorEnabled(false);
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void finish() {
        super.finish();
        instance = null;
    }
}