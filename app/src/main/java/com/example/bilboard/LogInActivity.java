package com.example.bilboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LogInActivity extends AppCompatActivity {

    private EditText editTextMobileNumber;
    private Button buttonLogIn;
    private ProgressBar progressBarLogIn;
    private TextView textViewFeedBack;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        editTextMobileNumber = findViewById(R.id.mobileNumber_editText);
        buttonLogIn = findViewById(R.id.logIn_Button);
        progressBarLogIn = findViewById(R.id.logIn_progressBar);
        textViewFeedBack = findViewById(R.id.feedBack_textView);

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileNumber = "+88" + editTextMobileNumber.getText().toString();

                if (mobileNumber.isEmpty()) {
                    textViewFeedBack.setText("Please fill in the form");
                    textViewFeedBack.setVisibility(View.VISIBLE);
                } else {
                    progressBarLogIn.setVisibility(View.VISIBLE);
                    buttonLogIn.setEnabled(false);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            mobileNumber,
                            60,
                            TimeUnit.SECONDS,
                            LogInActivity.this,
                            mCallbacks
                    );

                }
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                textViewFeedBack.setText("Verification Failed Please try again");
                textViewFeedBack.setVisibility(View.VISIBLE);

                progressBarLogIn.setVisibility(View.INVISIBLE);
                buttonLogIn.setEnabled(true);
            }

            @Override
            public void onCodeSent(final String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                Intent otpIntent = new Intent(LogInActivity.this,OtpActivity.class);
                                otpIntent.putExtra("AuthCredentials",s);
                                startActivity(otpIntent);
                            }
                        },
                        5000);
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        // lojdkjfkdjfkdkfdjfjfkjkffjdfjkdjfkdjfkdjfdkfj
        if (mCurrentUser != null) {
//            startActivity(new Intent(LogInActivity.this, MainActivity.class));
//            finish();
            sendUserToHome();
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendUserToHome();
//                            FirebaseUser user = task.getResult().getUser();
                        } else {
                            // Sign in failed, display a message and update the UI

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                textViewFeedBack.setVisibility(View.VISIBLE);
                                textViewFeedBack.setText("There was an error verifying OTP");
                            }
                        }

                        progressBarLogIn.setVisibility(View.INVISIBLE);
                        buttonLogIn.setEnabled(true);
                    }
                });
    }

    private void sendUserToHome() {
        startActivity(new Intent(LogInActivity.this,MainActivity.class));
        finish();
    }
}
