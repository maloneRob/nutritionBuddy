package com.G26.fitnessandnutritionbuddy.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserProfile implements Parcelable {
    private String displayName;
    private UserNutrition nutritionGoal;
    private UserNutrition nutritionCount;



    public UserProfile(String displayName) {
        this.displayName = displayName;
        this.nutritionCount = new UserNutrition();
        this.nutritionGoal = new UserNutrition();
    }

    protected UserProfile(Parcel in) {
        displayName = in.readString();
    }

    public static final Creator<UserProfile> CREATOR = new Creator<UserProfile>() {
        @Override
        public UserProfile createFromParcel(Parcel in) {
            return new UserProfile(in);
        }

        @Override
        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };

    public String getDisplayName() {
        return displayName;
    }

    public void updateCount(Boolean isGoal, String nutrient, Integer value) {
        UserNutrition n;
        if (isGoal)
            n = nutritionGoal;
        else
            n = nutritionCount;
        switch (nutrient) {
            case "calorie": n.setCalories(value);
            case "carbohydrate": n.setCarbohydrates(value);
            case "fats": n.setFats(value);
            case "proteins": n.setProtein(value);
            default: System.out.println("Error in backend logic");
        }
    }

    public Integer getNutrientGoal(String nutrient) {
        return nutritionGoal.getNutrientCountByName(nutrient);
    }

    public Integer getNutrientCount(String nutrient) {
        return nutritionCount.getNutrientCountByName(nutrient);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(displayName);
    }
}
