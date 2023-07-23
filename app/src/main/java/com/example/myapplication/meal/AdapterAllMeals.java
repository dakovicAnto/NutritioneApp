package com.example.myapplication.meal;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.meal.detail.InfoMeal;

import java.util.ArrayList;

public class AdapterAllMeals  extends RecyclerView.Adapter<VH_AllMeals> {

        /*Klasa AdapterAllMeals koja populizira RecyclerView koji se nalazi u MealLibrary
         koja iylistava sve ili po zelji odabrane obroke(klasa MealLibrary)
         * preko konstruktora prima podatke u cursoru */
    Context context;
    Cursor cursor;

    public AdapterAllMeals(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;

    }

    @NonNull
    @Override
    public VH_AllMeals onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View row=layoutInflater.inflate(R.layout.meal_rv_singlerow,parent,false);
        VH_AllMeals vh_allMeals=new VH_AllMeals(row);
        return  vh_allMeals;

    }

    @Override
    public void onBindViewHolder(@NonNull VH_AllMeals holder, int position) {


        if(!cursor.moveToPosition(position)){
            return;
        }
        String name=cursor.getString(cursor.getColumnIndexOrThrow(DB_Meal.MealTable.COLUMN_NAME));

        String timeOfCreation=cursor.getString(cursor.getColumnIndexOrThrow(DB_Meal.MealTable.COLUMN_TIME));
        int  cal=cursor.getInt(cursor.getColumnIndexOrThrow(DB_Meal.MealTable.COLUMN_SUM_CALORIES));
        long id=cursor.getLong(cursor.getColumnIndexOrThrow(DB_Meal.MealTable._ID));

        InfoMeal infoMeal=new InfoMeal();
        String[] dateAndTime= infoMeal.vrijemeDatum(timeOfCreation);
        String date=dateAndTime[0];
        String time=dateAndTime[1];
        String cal1= String.valueOf(cal);


        holder.mealName.setText(name);

        holder.calories.setText(cal1);
        holder.time.setText(time);
        holder.date.setText(date);
        holder.itemView.setTag(id);
        holder.iconaInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(context, InfoMeal.class);
                i.putExtra("vrijemeStvaranjaMeala",timeOfCreation); // klikom na iconuInfo otvara se novi Activity sa detaljima o tom obroku
                context.startActivity(i);

            }
        });

        if(position%2==0){
            Resources res=context.getResources();
            int crvena=res.getColor(R.color.MojaCrvena);

        }

    }

    @Override
    public int getItemCount() {
      return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if(cursor!=null){
            cursor.close();
        }
        cursor=newCursor;

        if(newCursor!=null){
            notifyDataSetChanged();
        }

    }




}
