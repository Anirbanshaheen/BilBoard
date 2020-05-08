package com.example.bilboard;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<UserData> arrayList;

    private FirebaseRecyclerOptions<UserData> options;
    private FirebaseRecyclerAdapter<UserData,FirebaseViewHolder> adapter;
    private DatabaseReference databaseReference;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        recyclerView = view.findViewById(R.id.itemRecyclerView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        arrayList = new ArrayList<UserData>();
//
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("Info");
//        databaseReference.keepSynced(true);
//
//        options = new FirebaseRecyclerOptions.Builder<UserData>().setQuery(databaseReference,UserData.class).build();
//
//        adapter = new FirebaseRecyclerAdapter<UserData, FirebaseViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull FirebaseViewHolder holder, int position, @NonNull UserData model) {
//
//                holder.country.setText(model.getCountry());
//                holder.capital.setText(model.getCapital());
//
//            }
//
//            @NonNull
//            @Override
//            public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                return new FirebaseViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.sample_row,parent,false));
//            }
//        };
//
//
//
//
//        recyclerView.setAdapter(adapter);
//
//    }
}}
