package com.G26.fitnessandnutritionbuddy.data.model;

public class UserProfile {
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
}
