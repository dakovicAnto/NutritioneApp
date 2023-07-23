package com.example.myapplication.meal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.meal.DB_Meal;
import com.example.myapplication.meal.detail.InfoMeal;

import java.util.ArrayList;
import java.util.Collections;

public class DB_helper_Meal extends SQLiteOpenHelper {
    /* klasa DB_helper predstavlja klasu koja zabravo kreira bazu podataka obroka */
    public  static final String DATABASE_NAME = "meal.db";
    public static final  int DATABASE_VERSION = 1;

    Context context1;

    public DB_helper_Meal(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context1=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MEAL_TABLE = "CREATE TABLE " +
                DB_Meal.MealTable.TABLE_NAME + " (" +
                DB_Meal.MealTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DB_Meal.MealTable.COLUMN_NAME + " TEXT NOT NULL, " +
                DB_Meal.MealTable.COLUMN_FOOD_NAMES + " TEXT NOT NULL, " +
                DB_Meal.MealTable.COLUMN_MASSES + " TEXT NOT NULL, " +
                DB_Meal.MealTable.COLUMN_TIME + " TEXT NOT NULL, " +
                DB_Meal.MealTable.COLUMN_SUM_CALORIES + " INTEGER NOT NULL, " +
                DB_Meal.MealTable.COLUMN_SUM_FAT + " FLOAT NOT NULL, " +
                DB_Meal.MealTable.COLUMN_SUM_PROTEIN + " FLOAT NOT NULL, " +
                DB_Meal.MealTable.COLUMN_SUM_CARB + " FLOAT NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_MEAL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_Meal.MealTable.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    /*Kada u calendaru(poziva se u MealLibrary) kliknemo tj.odaberemo datume za izlistavanje obroka koji oni sadrze
        koristimo ovo metodu koja prima zeljene datume i radi query po njima*/
    public Cursor getCursorWithSpecificDated(ArrayList<String> dates){

        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor;
        String[] columns={DB_Meal.MealTable.COLUMN_NAME,DB_Meal.MealTable.COLUMN_TIME,DB_Meal.MealTable.COLUMN_SUM_CALORIES,DB_Meal.MealTable._ID};
        String[] selArgs= dates.toArray(new String[0]);
        for(int i=0;i<dates.size();i++){
            selArgs[i]=dates.get(i);

        }

        cursor=sqLiteDatabase.query(DB_Meal.MealTable.TABLE_NAME, columns,
                DB_Meal.MealTable.COLUMN_TIME+" IN (" +
                        TextUtils.join(",", Collections.nCopies(selArgs.length, "?")) +
                        ")",

               selArgs, null, null, DB_Meal.MealTable._ID);

        return cursor;
    }

    /*Ova metoda sluzi da bi calendar adapteru dostavili sve datume koji sadrze neki obrok*/
    public ArrayList<String> getAllDatesWithMeals(){

        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor;
        String[] columns={DB_Meal.MealTable.COLUMN_TIME};
        String timeOfCreation1="";
        ArrayList<String> datesFromMealDB=new ArrayList<>();
        InfoMeal infoMeal=new InfoMeal();
        String[] dateAndTime=new String[2];
        String date="";

        cursor=sqLiteDatabase.query(DB_Meal.MealTable.TABLE_NAME, columns,null,null,null,null,DB_Meal.MealTable._ID);

        while (cursor.moveToNext()) {

            timeOfCreation1 = cursor.getString(cursor.getColumnIndexOrThrow(DB_Meal.MealTable.COLUMN_TIME));
            dateAndTime=infoMeal.vrijemeDatum(timeOfCreation1);
            date=dateAndTime[0];
            datesFromMealDB.add(date);
        }
        cursor.close();


        return datesFromMealDB;
    }
    
    
}
