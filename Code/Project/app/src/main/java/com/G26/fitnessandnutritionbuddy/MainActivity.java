package com.G26.fitnessandnutritionbuddy;

import android.os.Bundle;

import com.G26.fitnessandnutritionbuddy.data.model.UserProfile;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.G26.fitnessandnutritionbuddy.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    EditText userNameText;
    Bundle saveData;
    Bundle restaurauntData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("[MethodCheck] MainActivity onCreate");
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

//        //get username from textview at login screen
//        userNameText = (EditText) findViewById(R.id.editTextTextPersonName);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
    //these classes save the object between fragments
    public void saveData(Bundle data){
        saveData = data;
        UserProfile temp = data.getParcelable("profile");
        Log.d("print save", temp.getDisplayName());
        Log.d("print save calories", temp.getNutrientGoal("calories").toString());
        Log.d("print save carbs", temp.getNutrientGoal("carbohydrates").toString());
        Log.d("print save fats",temp.getNutrientGoal("fats").toString());
        Log.d("print save protein",temp.getNutrientGoal("protein").toString());
    }
    // get the user's profile to pass to new fragment
    public Bundle getSaveData(){
        return saveData;
    }


    // save the list of restaurants to main activity to pass between fragments

    public void saveRestaurants(Bundle data){
        Log.d("[Method check]", "in saveRestaurants method");
        //save restaurant bundle so we can just pass it back when called
        restaurauntData = data;
        //test print restaurant menu's
        ArrayList<Restaurant> restaurants = data.getParcelableArrayList("restaurants");
        for (Restaurant rest : restaurants){
            for (Food food : rest.getMenu()){
                Log.d("printing saved menu items name", food.getName());
                Log.d("printing saved menu items cal", String.valueOf(food.getCalories()));
                Log.d("printing saved menu items carbs", String.valueOf(food.getCarbohydrates()));
                Log.d("printing saved menu items fats", String.valueOf(food.getFats()));
                Log.d("printing saved menu items protein", String.valueOf(food.getProtein()));
            }
        }
    }
    public Bundle getRestaurants(){return restaurauntData;}

}