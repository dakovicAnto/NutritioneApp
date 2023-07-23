package com.example.myapplication.meal.toolbar;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class VH_Calendar extends RecyclerView.ViewHolder implements View.OnClickListener {
    /*ViewHolder za grid Recyclerview sto se pojavljiva kao calendar(inicijaliziran u MealLibrary) */
    TextView dayNumber;
    View icon;

    //sadrzi  onItemListener koji ce handlat klikanja na dane u kalendaru tj.(1 view od gridRV)
    private final CalendarAdapter.OnItemListner onItemListner;


    public VH_Calendar(@NonNull View itemView, CalendarAdapter.OnItemListner onItemListner) {
        super(itemView);
        this.dayNumber=itemView.findViewById(R.id.cellDayText);
        this.onItemListner = onItemListner;
        this.icon=itemView.findViewById(R.id.calendarIcon);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        onItemListner.onItemClick(getAdapterPosition(),itemView,String.valueOf(dayNumber.getText()) );
    }

}
