package com.G26.fitnessandnutritionbuddy.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserNutrition implements Parcelable {
    private Integer calories;
    private Integer carbohydrates;
    private Integer fats;
    private Integer protein;

    public UserNutrition() {
        this.calories = -1;
        this.carbohydrates = -1;
        this.fats = -1;
        this.protein = -1;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public void setCarbohydrates(Integer carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public void setFats(Integer fats) {
        this.fats = fats;
    }

    public void setProtein(Integer protein) {
        this.protein = protein;
    }

    public Integer getCalories() {
        return calories;
    }

    public Integer getCarbohydrates() {
        return carbohydrates;
    }

    public Integer getFats() {
        return fats;
    }

    public Integer getProtein() {
        return protein;
    }

    public Integer getNutrientCountByName(String nutrient) {
        switch (nutrient) {
            case "calories": return calories;
            case "carbohydrates": return carbohydrates;
            case "fats": return fats;
            case "protein": return protein;
        }
        System.out.println("Error in backend logic");
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.calories);
        dest.writeValue(this.carbohydrates);
        dest.writeValue(this.fats);
        dest.writeValue(this.protein);
    }

    public void readFromParcel(Parcel source) {
        this.calories = (Integer) source.readValue(Integer.class.getClassLoader());
        this.carbohydrates = (Integer) source.readValue(Integer.class.getClassLoader());
        this.fats = (Integer) source.readValue(Integer.class.getClassLoader());
        this.protein = (Integer) source.readValue(Integer.class.getClassLoader());
    }

    protected UserNutrition(Parcel in) {
        this.calories = (Integer) in.readValue(Integer.class.getClassLoader());
        this.carbohydrates = (Integer) in.readValue(Integer.class.getClassLoader());
        this.fats = (Integer) in.readValue(Integer.class.getClassLoader());
        this.protein = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserNutrition> CREATOR = new Parcelable.Creator<UserNutrition>() {
        @Override
        public UserNutrition createFromParcel(Parcel source) {
            return new UserNutrition(source);
        }

        @Override
        public UserNutrition[] newArray(int size) {
            return new UserNutrition[size];
        }
    };
}
