package com.G26.fitnessandnutritionbuddy.parsing;

import android.content.Context;

import com.G26.fitnessandnutritionbuddy.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class NutritionXFoodListParsing {

    private ArrayList<Food> foodList;
    private Context context;

    public NutritionXFoodListParsing(Context c) {
        foodList = new ArrayList<>();
        context = c;
        parseList();
    }

    ArrayList<Food> getFoodList() {
        return foodList;
    }

    // all grunt work for getting a json string from a filename
    private String getJsonString(String filename) {
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open(filename);

            InputStreamReader isReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isReader);
            StringBuffer sb = new StringBuffer();
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
            json = sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }

    private void getNutrients(Food food, JSONArray nutrientArray) throws JSONException {
        for (int nutrientIndex = 0; nutrientIndex < nutrientArray.length(); nutrientIndex++) {
            JSONObject nutrient = nutrientArray.getJSONObject(nutrientIndex);
            int attr_id = nutrient.getInt("attr_id");
            int value = (int)nutrient.getLong("value");

            // past calories, just break out of function
            if (attr_id > 208) return;

            // set nutrient values if found
            if (attr_id == 203)
                food.setCalories(value);
            else if (attr_id == 204)
                food.setFats(value);
            else if (attr_id == 205)
                food.setCarbohydrates(value);
            else if (attr_id == 208)
                food.setCalories(value);
        }
    }

    private void parseList() {
        String filename = "examples/WendysFoodList.json";
        String jsonString = getJsonString(filename);
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            JSONArray foodArray = jsonObject.getJSONArray("common");
            for (int foodIndex = 0; foodIndex < 5; foodIndex++) { // limit to first 5 foods for now
                JSONObject foodObject = foodArray.getJSONObject(foodIndex);

                Food food = new Food();

                food.setFoodName(foodObject.getString("food_name"));
                food.setBrandName(foodObject.getString("brand_name"));
                getNutrients(food, foodObject.getJSONArray("full_nutrients"));

                // add to food list
                foodList.add(food);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
