package com.example.myapplication.meal.toolbar;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

import java.util.ArrayList;

public class Spinner_Meal_Adapter extends ArrayAdapter<String> {

    /*Klasa Spinner_Meal_Adapter  populizira spinner koji ce izlistavati dvije opcije za nacin pretrazivanja
    korisnikovih obroka (2 opcije:BY DAY i ALL)
     * -ova klasa spaja podatke koje prima preko din.liste iz konstruktora( ArrayList<String> selectBy)
     * sa spinnerom koji ce prikazivati te podatke*/


    public Spinner_Meal_Adapter(@NonNull Context context, ArrayList<String> selectBy) {
        super(context,0,selectBy);
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int i=0;
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_meal, parent, false);

        }



        TextView select = convertView.findViewById(R.id.tvSelectBy);

        String selectedItem = getItem(position);
        if (selectedItem != null) {

            select.setText(selectedItem);
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_dropdown, parent, false);
        }

        TextView select = convertView.findViewById(R.id.tvSelectBy);


        String selectedItem = getItem(position);
        if (selectedItem != null) {

            select.setText(selectedItem);
        }


        return convertView;
    }




}
