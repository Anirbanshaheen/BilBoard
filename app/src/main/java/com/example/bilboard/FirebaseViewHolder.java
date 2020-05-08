package com.example.bilboard;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FirebaseViewHolder extends RecyclerView.ViewHolder{

    public TextView country,capital;

    public FirebaseViewHolder(@NonNull View itemView) {
        super(itemView);

        country = itemView.findViewById(R.id.country);
        capital = itemView.findViewById(R.id.capital);

    }
}
