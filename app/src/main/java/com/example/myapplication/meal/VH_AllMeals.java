package com.example.myapplication.meal;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class VH_AllMeals extends RecyclerView.ViewHolder {

    /*ViewHolder za Recycler view sto se izlistava obroke(inicijaliziran u MealLibrary) */

    TextView mealName;
    TextView calories;
    TextView time;

    View iconaInfo;

    TextView date;



    public VH_AllMeals(@NonNull View itemView) {
        super(itemView);
        this.mealName=itemView.findViewById(R.id.tvMealName);
        this.calories=itemView.findViewById(R.id.tvcalValueMeal);
        this.time=itemView.findViewById(R.id.tvTime);
        this.date=itemView.findViewById(R.id.tvTime);
        this.iconaInfo=itemView.findViewById(R.id.vIconaInfo);
    }
}
