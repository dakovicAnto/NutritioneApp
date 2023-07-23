package com.example.myapplication.meal;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;

public class Meal_Blueprint {
    /*Klasa koja obrok ,sadrzi njene atribute i metode*/
    private String m_name;
    private ArrayList<Integer> mass;
    private ArrayList<String> foodNames;
    private String time;
    private float sum_calories;
    private float sum_fat;
    private float sum_protein;
    private float sum_carb;

    public Meal_Blueprint(String m_name,ArrayList<Integer> mass,ArrayList<String>foodNames,String localTime, int sum_calories, float sum_fat, float sum_protein, float sum_carb) {
        this.m_name = m_name;
        this.mass=mass;
        this.foodNames=foodNames;
        this.time=localTime;
        this.sum_calories = sum_calories;
        this.sum_fat = sum_fat;
        this.sum_protein = sum_protein;
        this.sum_carb = sum_carb;
    }

    public  Meal_Blueprint()
    {}

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public ArrayList<Integer> getMass() {
        return mass;
    }

    public void setMass(ArrayList<Integer> mass) {
        this.mass = mass;
    }

    public ArrayList<String> getFoodNames() {
        return foodNames;
    }

    public void setFoodNames(ArrayList<String> foodNames) {
        this.foodNames = foodNames;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getSum_calories() {
        return sum_calories;
    }

    public void setSum_calories(float sum_calories) {
        this.sum_calories = sum_calories;
    }

    public float getSum_fat() {
        return sum_fat;
    }

    public void setSum_fat(float sum_fat) {
        this.sum_fat = sum_fat;
    }

    public float getSum_protein() {
        return sum_protein;
    }

    public void setSum_protein(float sum_protein) {
        this.sum_protein = sum_protein;
    }

    public float getSum_carb() {
        return sum_carb;
    }

    public void setSum_carb(float sum_carb) {
        this.sum_carb = sum_carb;
    }
}
