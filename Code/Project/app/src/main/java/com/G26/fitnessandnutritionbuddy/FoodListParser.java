package com.G26.fitnessandnutritionbuddy;

import android.content.Context;

import com.google.android.gms.common.util.IOUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class FoodListParser {

    private ArrayList<Food> foodList;
    private Context context;

    FoodListParser(Context c) {
        foodList = new ArrayList<>();
        context = c;
        parseList();
    }

    ArrayList<Food> getFoodList() {
        return foodList;
    }

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

    private void parseList() {

        ArrayList<String> files = new ArrayList<>();
        files.add("chickfila.json");
        files.add("mcdonalds.json");
        files.add("portillos.json");

        for (int i = 0; i < files.size(); i++) {
            String jsonString = getJsonString(files.get(i));
            try {
                JSONObject jsonObject = new JSONObject(jsonString);

                JSONArray foodArray = jsonObject.getJSONArray("Foods");
                for (int j = 0; j < foodArray.length(); j++) {
                    Food food = new Food();

                    JSONObject jsonObj = foodArray.getJSONObject(j);

                    food.setName(jsonObj.getString("name"));
                    food.setCalories(jsonObj.getInt("calories"));
                    food.setFats(jsonObj.getInt("fats"));
                    food.setCarbohydrates(jsonObj.getInt("carbohydrates"));
                    food.setProtein(jsonObj.getInt("protein"));
                    foodList.add(food);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

