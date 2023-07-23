package com.example.myapplication.meal.detail;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.meal.VH_AllMeals;

import java.util.ArrayList;

public class AdapterInfo extends RecyclerView.Adapter<VH_Info> {

        /*Klasa AdapterInfo koja populizira RecyclerView koji se nalazi u activity-u gdje se vidi detalji obroka(InfoMeal)
         * preko konstruktora prima podatke u dvije din.liste */
    Context context;
    ArrayList<String> foodNamesArray;
    ArrayList<Integer> massArray;

    Drawable constraintCacaBackground;
    GradientDrawable gradientDrawable;

    public AdapterInfo(Context context, ArrayList<String> foodNamesArray, ArrayList<Integer> massArray) {
        this.context = context;
        this.foodNamesArray = foodNamesArray;
        this.massArray = massArray;
    }

    @NonNull
    @Override
    public VH_Info onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View row=layoutInflater.inflate(R.layout.singlerow_meal_info,parent,false);
        VH_Info vh_info=new VH_Info(row);
        return  vh_info;
    }

    @Override
    public void onBindViewHolder(@NonNull VH_Info holder, int position) {
        constraintCacaBackground=holder.lijevoPlavo.getBackground();
        gradientDrawable= (GradientDrawable) constraintCacaBackground;
        Resources res =context.getResources();
        int svijetloPlava=res.getColor(R.color.MojaZelana);
        int Plava=res.getColor(R.color.MojaPlava);

        double posicion=position;

        if(posicion %2==0){
            gradientDrawable.setTint(Plava);
        }
        else {
            gradientDrawable.setTint(svijetloPlava);
        }

        String name=foodNamesArray.get(position);
        String mass= String.valueOf(massArray.get(position));

        holder.name.setText(name);
        holder.mass.setText(mass+"g");

        holder.itemView.setTag(position);


    }

    @Override
    public int getItemCount() {
        return foodNamesArray.size();
    }
}
