package com.G26.fitnessandnutritionbuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONActivity extends AppCompatActivity {

    private ArrayList<Food> foodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFoodList();

        setUIRef();
    }

    private void setUIRef() {
        RecyclerView recycler = findViewById(R.id.recyclerView);
        FoodRecyclerViewAdaptor viewAdaptor = new FoodRecyclerViewAdaptor(foodList);
        recycler.setAdapter(viewAdaptor);
        LinearLayoutManager linlayManager = new LinearLayoutManager(JSONActivity.this, RecyclerView.VERTICAL, false);
        recycler.setLayoutManager(linlayManager);
    }

    // parses JSON for information
    private void getFoodList() {

        String jsonString1 = loadJSON1();
        String jsonString2 = loadJSON2();
        String jsonString3 = loadJSON3();

        try {
            JSONObject root1 = new JSONObject(jsonString1);
            JSONObject root2 = new JSONObject(jsonString2);
            JSONObject root3 = new JSONObject(jsonString3);

            JSONArray foodArray1 = root1.getJSONArray("Foods");
            JSONArray foodArray2 = root2.getJSONArray("Foods");
            JSONArray foodArray3 = root3.getJSONArray("Foods");

            for(int x = 0; x < foodArray1.length(); x++) {
                Food temp = new Food();

                JSONObject jsonObj = foodArray1.getJSONObject(x);

                temp.setName(jsonObj.getString("name"));
                temp.setCalories(jsonObj.getInt("calories"));
                temp.setFats(jsonObj.getInt("fats"));
                temp.setCarbohydrates(jsonObj.getInt("carbohydrates"));
                temp.setProtein(jsonObj.getInt("protein"));

                foodList.add(temp);
            }

            for(int y = 0; y < foodArray2.length(); y++) {
                Food temp = new Food();

                JSONObject jsonObj = foodArray2.getJSONObject(y);

                temp.setName(jsonObj.getString("name"));
                temp.setCalories(jsonObj.getInt("calories"));
                temp.setFats(jsonObj.getInt("fats"));
                temp.setCarbohydrates(jsonObj.getInt("carbohydrates"));
                temp.setProtein(jsonObj.getInt("protein"));

                foodList.add(temp);
            }

            for(int z = 0; z < foodArray3.length(); z++) {
                Food temp = new Food();

                JSONObject jsonObj = foodArray3.getJSONObject(z);

                temp.setName(jsonObj.getString("name"));
                temp.setCalories(jsonObj.getInt("calories"));
                temp.setFats(jsonObj.getInt("fats"));
                temp.setCarbohydrates(jsonObj.getInt("carbohydrates"));
                temp.setProtein(jsonObj.getInt("protein"));

                foodList.add(temp);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String loadJSON1() {

        String json = null;

        try {
            InputStream in = getAssets().open("chickfila.json");

            int len = in.available();

            byte[] buff = new byte[len];
            in.read(buff);
            in.close();

            json = new String(buff, StandardCharsets.UTF_8);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }

    private String loadJSON2() {

        String json = null;

        try {
            InputStream in = getAssets().open("mcdonalds.json");

            int len = in.available();

            byte[] buff = new byte[len];
            in.read(buff);
            in.close();

            json = new String(buff, StandardCharsets.UTF_8);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }

    private String loadJSON3() {

        String json = null;

        try {
            InputStream in = getAssets().open("portillos.json");

            int len = in.available();

            byte[] buff = new byte[len];
            in.read(buff);
            in.close();

            json = new String(buff, StandardCharsets.UTF_8);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }
}