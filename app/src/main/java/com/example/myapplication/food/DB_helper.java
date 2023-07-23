package com.example.myapplication.food;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB_helper extends SQLiteOpenHelper {

    /* klasa DB_helper predstavlja klasu koja zabravo kreira bazu podataka  */

    public  static final String DATABASE_NAME = "food.db";
    public static final  int DATABASE_VERSION = 4;

    public DB_helper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_PERSONS_TABLE = "CREATE TABLE " +
                DB_Blueprint.FoodTable.TABLE_NAME + " (" +
                DB_Blueprint.FoodTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DB_Blueprint.FoodTable.COLUMN_NAME + " TEXT NOT NULL, " +
                DB_Blueprint.FoodTable.COLUMN_CALORIES + " INTEGER NOT NULL, " +
                DB_Blueprint.FoodTable.COLUMN_FAT + " FLOAT NOT NULL, " +
                DB_Blueprint.FoodTable.COLUMN_PROTEIN + " FLOAT NOT NULL, " +
                DB_Blueprint.FoodTable.COLUMN_CARB + " FLOAT NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_PERSONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_Blueprint.FoodTable.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    /*  Kada na spinneru sa imenima nutritienata odaberemo neku vrijednost
                Ona se posalje ovoj funkciji kao (String name)  koja odradi query u bazu sa ovim nazivom.
                Taj query vraca zapis iz baze s tim imenom - vraca ga u obliku liste
                  */
    public float[] getSpecificData(String name){
        float[]  rezult = new float[4];

        Cursor cursor;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        cursor=sqLiteDatabase.query(DB_Blueprint.FoodTable.TABLE_NAME,null,DB_Blueprint.FoodTable.COLUMN_NAME+" = '"+name+"'",null,null,null,DB_Blueprint.FoodTable._ID);
        while (cursor.moveToNext()){

            float  cal=cursor.getInt(cursor.getColumnIndexOrThrow(DB_Blueprint.FoodTable.COLUMN_CALORIES));
            float  fat=cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Blueprint.FoodTable.COLUMN_FAT));
            float  pro=cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Blueprint.FoodTable.COLUMN_PROTEIN));
            float  carb=cursor.getFloat(cursor.getColumnIndexOrThrow(DB_Blueprint.FoodTable.COLUMN_CARB));

            rezult[0]=cal;
            rezult[1]=fat;
            rezult[2]=pro;
            rezult[3]=carb;

        }

        return  rezult;
    }



}
