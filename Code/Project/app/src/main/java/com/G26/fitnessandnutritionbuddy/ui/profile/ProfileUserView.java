package com.G26.fitnessandnutritionbuddy.ui.profile;

import com.G26.fitnessandnutritionbuddy.data.model.UserNutrition;

import java.util.HashMap;
import java.util.Map;

public class ProfileUserView {
    private String displayName;
    private UserNutrition nutritionGoal;
    private UserNutrition nutritionCount;

    //... other data fields that may be accessible to the UI

    ProfileUserView(String displayName) {
        this.displayName = displayName;
        this.nutritionCount = new UserNutrition();
        this.nutritionGoal = new UserNutrition();
    }

    String getDisplayName() {
        return displayName;
    }

    Integer getNutrientGoal(String nutrient) {
        return nutritionGoal.getNutrientCountByName(nutrient);
    }

    Integer getNutritionCount(String nutrient) {
        return nutritionCount.getNutrientCountByName(nutrient);
    }
}
