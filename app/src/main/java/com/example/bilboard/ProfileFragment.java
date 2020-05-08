package com.example.bilboard;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private EditText editTextName, editTextLocation,editTextAge;
    private Button buttonToFirebase,buttonForEdit;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextName = view.findViewById(R.id.name_et);
        editTextLocation = view.findViewById(R.id.location_et);
        editTextAge = view.findViewById(R.id.age_et);

        buttonToFirebase = view.findViewById(R.id.profile_bt);
        buttonForEdit = view.findViewById(R.id.profile_edit_bt);


        firebaseAuth = FirebaseAuth.getInstance();                                       //
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Information"); //  for own
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("Country","Bangladesh");
//        hashMap.put("Country","dhaka");
//        hashMap.put("Country","India");
//        hashMap.put("Country","Chittagong");
//        hashMap.put("Country","Feni");
//        hashMap.put("Country","Pakistan");
//        hashMap.put("Country","america");
//        hashMap.put("Country","China");
//        hashMap.put("Country","Barmuda");
//        hashMap.put("Country","Italy");
//        hashMap.put("Country","France");
//        hashMap.put("Country","Nepal");
//        hashMap.put("Country","Canada");
//        hashMap.put("Country","Africa");
//        hashMap.put("Country","iraq");
//        hashMap.put("Country","vietnam");
//        hashMap.put("Country","namibia");
//        hashMap.put("Country","germany");
//        hashMap.put("Country","coxsbazar");
//        hashMap.put("Country","saintmartin");
//        hashMap.put("Country","russia");
//        hashMap.put("Country","chili");
//        hashMap.put("Country","kerala");
//        hashMap.put("Country","kolkata");
//        hashMap.put("Country","earth");
//        hashMap.put("Country","moon");
//         databaseReference.push().setValue(hashMap);

        buttonToFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processProfile();   //

                editTextName.setEnabled(false);
                editTextAge.setEnabled(false);
                editTextLocation.setEnabled(false);
//               String name = editTextName.getText().toString().trim();
//               String location = editTextLocation.getText().toString().trim();
//               String age = editTextAge.getText().toString().trim();
//
//               HashMap<String, String> hashMap = new HashMap<>();
//               hashMap.put("name",name);
//               hashMap.put("location",location);
//               hashMap.put("age",age);
//               databaseReference.setValue(hashMap);
            }
        });

        buttonForEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextName.setEnabled(true);
                editTextAge.setEnabled(true);
                editTextLocation.setEnabled(true);
            }
        });
    }


    public void processProfile(){
        String name = editTextName.getText().toString().trim();          //
        String location = editTextLocation.getText().toString().trim();  // get data from xml
        String age = editTextAge.getText().toString().trim();            //
        registerUser(name,location,age);
    }

    private void registerUser(final String name, final String location, final String age) {
        firebaseAuth.createUserWithEmailAndPassword(name,location).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                String uid = currentUser.getUid();
                UserData userData = new UserData(name,location,age);

                databaseReference.child(uid).setValue(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}
