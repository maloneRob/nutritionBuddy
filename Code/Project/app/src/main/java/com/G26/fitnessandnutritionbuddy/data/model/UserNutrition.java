package com.G26.fitnessandnutritionbuddy.data.model;

public class UserNutrition {
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
}
