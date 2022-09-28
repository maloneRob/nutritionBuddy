package com.G26.fitnessandnutritionbuddy.ui.profile;

import androidx.lifecycle.ViewModel;

import com.G26.fitnessandnutritionbuddy.data.model.UserProfile;

public class ProfileViewModel extends ViewModel {
    private UserProfile userProfile;

    // bruh what do i even do here
    ProfileViewModel() {
        // pretty sure this is wrong shrug
        userProfile = new UserProfile("prof bell");
    }

    private void updateNutrientGoals() {
        // update goal
        // calculate % filled
        // update ui
    }

    private void updateNutrientCount() {
        // update count
        // calculate %
        // update ui
    }

    public void updateNutrition() {
        // user hits button
        updateNutrientCount();
        updateNutrientGoals();
        // ok cool
    }

}
