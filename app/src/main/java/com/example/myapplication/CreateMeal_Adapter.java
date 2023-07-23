package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CreateMeal_Adapter extends RecyclerView.Adapter<VH_Create_Meal> {

    /*Klasa CreateMeal_Adapter koja populizira RecyclerView koji se pojavi u insert dialogu
     koji se otvori pri dodavanju novog obroka(klasa MainActivity)
     * preko konstruktora prima podatke u dvije listete ih spaja sa ciljanim Viewima */
    Context context;
    ArrayList<String> foodNamesArray;
    ArrayList<Integer> massArray;



    public CreateMeal_Adapter(Context context,ArrayList<String> foodNames,ArrayList<Integer> mass) {
        this.context=context;
        this.foodNamesArray=foodNames;
        this.massArray=mass;
    }

    @NonNull
    @Override
    public VH_Create_Meal onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View row=layoutInflater.inflate(R.layout.rv_meal_single_r,parent,false);
        VH_Create_Meal viewHolderFood= new VH_Create_Meal(row);
        return viewHolderFood;
    }

    @Override
    public void onBindViewHolder(@NonNull VH_Create_Meal holder, int position) {

        String name=foodNamesArray.get(position);
        String mass= String.valueOf(massArray.get(position));

        holder.name.setText(name);
        holder.mass.setText(mass);

        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return foodNamesArray.size();
    }
}
