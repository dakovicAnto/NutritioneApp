package com.example.myapplication.meal;

import android.provider.BaseColumns;

public class DB_Meal {
    private  DB_Meal(){};
    public static final class MealTable implements BaseColumns {
        /*klasa koja sadrzi String konstante koje su potrebne za bazu podataka*/
        public static final String TABLE_NAME = "TableOfMeal";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_FOOD_NAMES = "FoodNames";
        public static final String COLUMN_MASSES = "Masses";
        public static final String COLUMN_TIME = "Time";

        public static final String COLUMN_SUM_CALORIES = "Calories";
        public static final String COLUMN_SUM_FAT = "Fat";
        public static final String COLUMN_SUM_PROTEIN = "Protein";
        public static final String COLUMN_SUM_CARB = "Carbs";
    }
}
