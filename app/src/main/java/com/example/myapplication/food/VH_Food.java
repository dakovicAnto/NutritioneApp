package com.example.myapplication.food;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class VH_Food  extends RecyclerView.ViewHolder {
        /*ViewHolder za Recycler view sto se kod FoodLibrary*/


        TextView name;
        TextView cal;
        TextView fat;
        TextView pro;
        TextView carb;

        public VH_Food(@NonNull View itemView) {
            super(itemView);

            this.name=itemView.findViewById(R.id.tvName);
            this.cal=itemView.findViewById(R.id.tvcalValue);
            this.fat=itemView.findViewById(R.id.tvFatValue);
            this.pro=itemView.findViewById(R.id.tvProValue);
            this.carb=itemView.findViewById(R.id.tvCarbValue);
        }
}
