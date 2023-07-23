package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Spinner_Adapter extends ArrayAdapter<String> {

    /*Klasa Spinner_Adapter  populizira spinner koji ce izlistavati imena svih nutritienata
    * -ova klasa spaja podatke koje prima preko din.liste iz konstruktora( ArrayList<String> foodName)
    * sa spinnerom koji ce prikazivati te podatke*/

    public Spinner_Adapter(@NonNull Context context, ArrayList<String> foodName) {
        super(context, 0,foodName);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_single, parent, false);
        }

        TextView nazivHrane = convertView.findViewById(R.id.tvFoodName);

        String currentFood = getItem(position);
        if (currentFood != null) {

            nazivHrane.setText(currentFood);
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_dropdown, parent, false);
        }

        TextView nazivHrane = convertView.findViewById(R.id.tvSelectBy);

        String currentFood = getItem(position);
        if (currentFood != null) {

            nazivHrane.setText(currentFood);
        }


        return convertView;
    }



}
