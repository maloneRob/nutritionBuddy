package com.G26.fitnessandnutritionbuddy;

public class Food {

    private String foodName;
    private String brandName;
    private int calories;
    private int fats;
    private int carbohydrates;
    private int protein;

    public Food() {}

    public Food(String foodName, String brandName, int calories, int fats, int carbohydrates, int protein) {
        this.foodName = foodName;
        this.brandName = brandName;
        this.calories = calories;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.protein = protein;
    }

    public String getName() {
        return foodName;
    }

    public void setFoodName(String name) {
        this.foodName = name;
    }

    public void setBrandName(String name) {
        this.brandName = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }
}