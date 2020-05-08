package com.example.bilboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private DatabaseReference databaseReference; //

    private Button buttonLogOut;

    ChipNavigationBar bottomNav;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users"); //
//        UserData userData = new UserData("mr","26"); //
//        databaseReference.setValue(userData); // pass by object

        HashMap<String, String> hashMap = new HashMap<>();  //
        hashMap.put("groot","23");                          //
        hashMap.put("google","2020");                       // pass by hashing way
        hashMap.put("oneplus","tesla");                     //
        databaseReference.setValue(hashMap);                //

        bottomNav = findViewById(R.id.chipNavigationBar);

        if(savedInstanceState==null){
            bottomNav.setItemSelected(R.id.home_bottom,true);
            fragmentManager = getSupportFragmentManager();
            HomeFragment homeFragment = new HomeFragment();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer,homeFragment).commit();
        }

        bottomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                Fragment fragment = null;
                switch (id) {
                    case R.id.home_bottom:
                        fragment = new HomeFragment();
                        break;
                    case R.id.post_bottom:
                        fragment = new PostFragment();
                        break;
                    case R.id.Profile_bottom:
                        fragment = new ProfileFragment();
                        break;
                }

                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
                }

            }
        });

//        buttonLogOut = findViewById(R.id.logout_Button);

//        buttonLogOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mAuth.signOut();
//                sendUserToLogIn();
//            }
//        });
    }

    private void sendUserToLogIn() {
        startActivity(new Intent(MainActivity.this, LogInActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mCurrentUser == null) {
            sendUserToLogIn();
        }
    }
}
