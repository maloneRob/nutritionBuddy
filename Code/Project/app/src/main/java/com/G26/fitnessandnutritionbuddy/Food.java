package com.G26.fitnessandnutritionbuddy;

import static java.lang.Math.abs;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.DecimalFormat;

public class Food implements Parcelable {

    private String foodName;
    private String brandName;
    private int calories;
    private int fats;
    private int carbohydrates;
    private int protein;
    private double matchPercentage;

    private DecimalFormat df = new DecimalFormat("0.00");

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

    public double getMatchPercentage() { return this.matchPercentage; }

    public double setAndCalculateMatchPercentage(int calorieGoal, int fatsGoal, int carbsGoal, int proteinGoal) {
        double percentage = 0.0;

        // sorry.
        percentage += 1 - abs(((double)calorieGoal - (double)calories) / (double)calorieGoal);
        percentage += 1 - abs(((double)fatsGoal - (double)fats) / (double)fatsGoal);
        percentage += 1 - abs(((double)carbsGoal - (double)carbohydrates) / (double)carbsGoal);
        percentage += 1 - abs(((double)proteinGoal - (double)protein) / (double)proteinGoal);
        percentage /= 4;
        percentage *= 100;
        this.matchPercentage = Double.parseDouble(df.format(percentage));
        Log.i("[MatchPercentage]", foodName + " percentage: " + matchPercentage);

        return this.matchPercentage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.foodName);
        dest.writeString(this.brandName);
        dest.writeInt(this.calories);
        dest.writeInt(this.fats);
        dest.writeInt(this.carbohydrates);
        dest.writeInt(this.protein);
    }

    public void readFromParcel(Parcel source) {
        this.foodName = source.readString();
        this.brandName = source.readString();
        this.calories = source.readInt();
        this.fats = source.readInt();
        this.carbohydrates = source.readInt();
        this.protein = source.readInt();
    }

    protected Food(Parcel in) {
        this.foodName = in.readString();
        this.brandName = in.readString();
        this.calories = in.readInt();
        this.fats = in.readInt();
        this.carbohydrates = in.readInt();
        this.protein = in.readInt();
    }

    public static final Parcelable.Creator<Food> CREATOR = new Parcelable.Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel source) {
            return new Food(source);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };
}