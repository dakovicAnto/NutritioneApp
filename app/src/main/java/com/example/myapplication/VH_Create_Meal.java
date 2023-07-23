package com.example.myapplication;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VH_Create_Meal extends RecyclerView.ViewHolder {

    /*ViewHolder za Recycler view sto se pojavi kod insert dialoga */

    TextView name;
    TextView mass;

    public VH_Create_Meal(@NonNull View itemView) {
        super(itemView);
        this.name=itemView.findViewById(R.id.tvItemName);
        this.mass=itemView.findViewById(R.id.tvItemMass);
    }
}
