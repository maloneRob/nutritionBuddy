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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.displayName);
        dest.writeParcelable(this.nutritionGoal, flags);
        dest.writeParcelable(this.nutritionCount, flags);
    }

    public void readFromParcel(Parcel source) {
        this.displayName = source.readString();
        this.nutritionGoal = source.readParcelable(UserNutrition.class.getClassLoader());
        this.nutritionCount = source.readParcelable(UserNutrition.class.getClassLoader());
    }

    protected UserProfile(Parcel in) {
        this.displayName = in.readString();
        this.nutritionGoal = in.readParcelable(UserNutrition.class.getClassLoader());
        this.nutritionCount = in.readParcelable(UserNutrition.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserProfile> CREATOR = new Parcelable.Creator<UserProfile>() {
        @Override
        public UserProfile createFromParcel(Parcel source) {
            return new UserProfile(source);
        }

        @Override
        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };
}
