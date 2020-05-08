package com.example.bilboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OtpActivity extends AppCompatActivity {

    private EditText editTextOtp;
    private Button buttonOtp;
    private ProgressBar progressBarOtp;
    private TextView textViewOtp;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private String mAuthVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        // get data from LogIn Activity
        mAuthVerificationId = getIntent().getStringExtra("AuthCredentials");

        editTextOtp = findViewById(R.id.otp_editText);
        buttonOtp = findViewById(R.id.verifyOtp_Button);
        progressBarOtp = findViewById(R.id.otp_progressBar);
        textViewOtp = findViewById(R.id.feedBackOtp_textView);

        buttonOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = editTextOtp.getText().toString();

                if(otp.isEmpty()){
                    textViewOtp.setVisibility(View.VISIBLE);
                    textViewOtp.setText("Please fill the OTP");
                }else {

                    progressBarOtp.setVisibility(View.VISIBLE);
                    buttonOtp.setEnabled(false);

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mAuthVerificationId,otp);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OtpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendUserToHome();
//                            FirebaseUser user = task.getResult().getUser();
                        } else {
                            // Sign in failed, display a message and update the UI

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                textViewOtp.setVisibility(View.VISIBLE);
                                textViewOtp.setText("There was an error verifying OTP");
                            }
                        }

                        progressBarOtp.setVisibility(View.INVISIBLE);
                        buttonOtp.setEnabled(true);
                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();
        // lojdkjfkdjfkdkfdjfjfkjkffjdfjkdjfkdjfkdjfdkfj
        if (mCurrentUser != null) {
            sendUserToHome();
        }
    }

    public void sendUserToHome(){
        startActivity(new Intent(OtpActivity.this, MainActivity.class));
        finish();
    }
}
