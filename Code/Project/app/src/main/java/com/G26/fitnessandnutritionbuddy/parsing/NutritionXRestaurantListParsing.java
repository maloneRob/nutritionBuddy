package com.G26.fitnessandnutritionbuddy.parsing;

import android.content.Context;

import com.G26.fitnessandnutritionbuddy.Food;
import com.G26.fitnessandnutritionbuddy.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class NutritionXRestaurantListParsing {

    private ArrayList<Restaurant> restaurantList;
    private Context context;
    private JSONObject jsonObject;

    public NutritionXRestaurantListParsing(Context c, JSONObject jobject) {
        restaurantList = new ArrayList<>();
        this.jsonObject = jobject;
        context = c;
        parseList();
    }

    public ArrayList<Restaurant> getRestaurantList() {
        return restaurantList;
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

    private void parseList() {
//        String filename = "RestaurantsList.json";
//        String jsonString = getJsonString(filename);
        try {
//            JSONObject jsonObject = new JSONObject(jsonString);

            JSONArray restaurantArray = jsonObject.getJSONArray("locations");
            for (int restIndex = 0; restIndex < 10; restIndex++) { // limit to first 5 restaurants for now
                JSONObject restObject = restaurantArray.getJSONObject(restIndex);

                Restaurant restaurant = new Restaurant();
                restaurant.setRestaurantName(restObject.getString("name"));
                restaurant.setAddress(restObject.getString("address"));
                restaurant.setWebsite(restObject.getString("website"));
                restaurant.setLat(restObject.getDouble("lat"));
                restaurant.setLng(restObject.getDouble("lng"));
                restaurant.setDistance(restObject.getLong("distance_km"));

                // add to restaurant list
                restaurantList.add(restaurant);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
