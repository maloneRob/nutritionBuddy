package com.G26.fitnessandnutritionbuddy.ui.profile;

import com.G26.fitnessandnutritionbuddy.data.model.UserProfile;

import java.util.HashMap;
import java.util.Map;

public class ProfileUserView {
    private UserProfile profile;

    //... other data fields that may be accessible to the UI

    ProfileUserView(String displayName) {
        this.profile = new UserProfile(displayName);
    }

    public String getDisplayName() {
        return profile.getDisplayName();
    }

    public Integer getNutrientGoal(String nutrient) {
        return profile.getNutrientGoal(nutrient);
    }

    public Integer getNutrientCount(String nutrient) {
        return profile.getNutrientCount(nutrient);
    }
}
