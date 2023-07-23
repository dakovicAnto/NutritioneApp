package com.example.myapplication.food;

import android.provider.BaseColumns;

public class DB_Blueprint {

             /*klasa koja sadrzi String konstante koje su potrebne za bazu podataka*/

    private  DB_Blueprint(){};
    public static final class FoodTable implements BaseColumns {
        public static final String TABLE_NAME = "TableOfFood";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_CALORIES = "Calories";
        public static final String COLUMN_FAT = "Fat";
        public static final String COLUMN_PROTEIN = "Protein";
        public static final String COLUMN_CARB = "Carbs";
    }

}
