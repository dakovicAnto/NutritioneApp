package com.example.myapplication.food;

public class Food_Blueprint {

    /*Klasa koja predstavlja hranu ,sadrzi njene atribut i metode*/

    private String name;
    private int calories;
    private int fat;
    private int protein;
    private int carb;

    public Food_Blueprint(String name, int calories, int fat, int protein, int carb) {
        this.name = name;
        this.calories = calories;
        this.fat = fat;
        this.protein = protein;
        this.carb = carb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getCarb() {
        return carb;
    }

    public void setCarb(int carb) {
        this.carb = carb;
    }

}
