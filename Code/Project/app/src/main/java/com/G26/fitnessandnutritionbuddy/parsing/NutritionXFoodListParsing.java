package com.G26.fitnessandnutritionbuddy.parsing;

import android.content.Context;
import android.util.Log;

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

    public ArrayList<Food> getFoodList() {
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
        Log.i("[parse check]", "getNutrients0");
        for (int nutrientIndex = 0; nutrientIndex < nutrientArray.length(); nutrientIndex++) {
            Log.i("[parse check]", "getNutrients1");
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
        Log.i("[parse check]", "getNutrients2");
    }

    private void parseList() {
        Log.i("[parse check]", "parsing list");
        String filename = "WendysFoodList.json";
        String jsonString = getJsonString(filename);
        try {
            Log.i("[parse check]", "in try0");
            JSONObject jsonObject = new JSONObject(jsonString);

            JSONArray foodArray = jsonObject.getJSONArray("common");
            Log.i("[parse check]", "in try1");
            for (int foodIndex = 0; foodIndex < 5; foodIndex++) { // limit to first 5 foods for now
                Log.i("[parse check]", "in try2");
                JSONObject foodObject = foodArray.getJSONObject(foodIndex);

                Food food = new Food();

                food.setFoodName(foodObject.getString("food_name"));
//                food.setBrandName(foodObject.getString("brand_name"));
                Log.i("[foodcheck]", food.getName());
                getNutrients(food, foodObject.getJSONArray("full_nutrients"));

                String item = food.getName()+" Calories:"+food.getCalories() + " Carbs:" + food.getCarbohydrates()
                        + " Fats:" + food.getFats() + " Protein:" + food.getProtein();
                Log.i("[adding item]", item);

                // add to food list
                foodList.add(food);
            }
            Log.i("[parse check]", "in try3");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
