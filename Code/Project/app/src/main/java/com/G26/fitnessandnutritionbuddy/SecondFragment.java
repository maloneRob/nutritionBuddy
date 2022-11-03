package com.G26.fitnessandnutritionbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.G26.fitnessandnutritionbuddy.data.model.UserProfile;
import com.G26.fitnessandnutritionbuddy.databinding.FragmentSecondBinding;
import com.G26.fitnessandnutritionbuddy.parsing.NutritionXFoodListParsing;
import com.G26.fitnessandnutritionbuddy.parsing.NutritionXRestaurantListParsing;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment implements OnMapReadyCallback {

    private FragmentSecondBinding binding;
    private GoogleMap mMap;
    Bundle restaurants;
    Bundle data;
    UserProfile user;
    ListView foodsNearby;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> items;


    private Handler mUiHandler = new Handler(Looper.getMainLooper());
    private Handler lUiHandler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        System.out.println("[MethodCheck] SecondFragment onCreateView");
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        MainActivity activity = (MainActivity) getActivity();
        Bundle saveData = activity.getSaveData();
        user = saveData.getParcelable("profile");
        Log.i("[printing user information name]", user.getDisplayName());
        Log.i("[printing user information calories]", user.getNutrientGoal("calories").toString());
        Log.i("[printing user information carbs]", user.getNutrientGoal("carbohydrates").toString());
        Log.i("[printing user information protein]", user.getNutrientGoal("protein").toString());
        Log.i("[printing user information fats]", user.getNutrientGoal("fats").toString());
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        System.out.println("[MethodCheck] SecondFragment onViewCreated");

        super.onViewCreated(view, savedInstanceState);
        items = new ArrayList<String>();
        foodsNearby = view.findViewById(R.id.ListViewFoodsNearby);
        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, items);
        foodsNearby.setAdapter(arrayAdapter);
        // to main menu
        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_MainMenuFragment);

//                Intent intent = new Intent(getContext(), JSONActivity.class);
//                startActivity(intent);
            }
        });

        // to food list
//        binding.buttonThird.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(SecondFragment.this)
//                        .navigate(R.id.action_SecondFragment_to_JsonListFragment);
//            }
//        });

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map3);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    //function for adding a marker to the google map
    private void addRestaurants(GoogleMap mMap, Pair<String,LatLng> restaurant) {
        mMap.addMarker(new MarkerOptions().position(restaurant.second).icon(BitmapDescriptorFactory.defaultMarker(276)).title(restaurant.first));

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker at UIC, move & zoom the camera
        LatLng location = new LatLng(41.8725, -87.6493);
        String locationName = "Your Location";

        new Thread(new Runnable() {
            @Override
            public void run() {
                queryRestaurants(mMap);
            }
        }).start();


        mMap.addMarker(new MarkerOptions().position(location).title(locationName));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14));

    }
    // function for making restaurant queries
    private void queryRestaurants(GoogleMap mMap) {
        // make a new thread to not bog down UI
        new Thread(new Runnable() {
            @Override
            public void run() {
//                Log.i("[method check]", "about to parse list");
                //make query object and pass it latlng to make api request for restaurants nearby
                QueryRestaurants restQuery = new QueryRestaurants(getContext());
                restQuery.queryLocation(41.8725, -87.6493, new QueryRestaurants.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Log.i("[Error response]","error getting restaurants");
                    }

                    @Override
                    public void onResponse(JSONObject response) {
                        //we get a response so we make a NutritionXRestaurantList Parsing object to parse the json object response
                        NutritionXRestaurantListParsing list = new NutritionXRestaurantListParsing(getContext(), response);
                        //get the list of restaurants nearby our location from the parser class
                        ArrayList<Restaurant> restList = list.getRestaurantList();
                        //for every location we get we're going to iterate through them
                        for (Restaurant rest : restList){
                            //make LatLng pair of restaurant location to send it to the gmap marker
                            LatLng posit = new LatLng(rest.getLat(), rest.getLng());
                            mUiHandler.post(new Runnable() {
                                //we use the mUIHandler to update the UI thread since our api calls are asynchronous we update as we get them
                                @Override
                                public void run() {
                                    //addRestaurants adds the marker to the map
                                    addRestaurants(mMap, Pair.create(rest.getRestaurantName(), posit));
                                }
                            });
                            // we then start querying for the menu for that restaurant by passing the name to the queryMenu function
                            queryMenu(rest.getRestaurantName());
                        }
                    }
                });

            }
        }).start(); //start the thread

    }
    // function for making menu queries
    private void queryMenu(String restaurantName) {
        //grab the nutrition goals for the user to parse the list by
        int calorieGoal = user.getNutrientGoal("calories");
        int carbGoal = user.getNutrientGoal("carbohydrates");
        int fatsGoal = user.getNutrientGoal("fats");
        int proteinGoal = user.getNutrientGoal("protein");
        // make a new thread as to not bog down UI
        new Thread(new Runnable() {
            @Override
            public void run() {
//                Log.i("[thread check]", "about to parse");
                //make a QueryMenu object
                QueryMenu menuQuery = new QueryMenu(getContext());
                //make a Querymenu object and call the queryMenuItems method with the restaurant name
                menuQuery.queryMenuItems(restaurantName, new QueryRestaurants.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Log.i("[queryMenuError]", "error getting json back from menu for" + restaurantName);
                    }

                    @Override
                    public void onResponse(JSONObject response) {
                        //we got a response so we pass that response to an instance of the NutritionXFoodListParsing class
                        NutritionXFoodListParsing list = new NutritionXFoodListParsing(getContext(), response);
                        // we get the list build by the parser using the getFoodList() function
                        ArrayList<Food> foodList = list.getFoodList();
                        //for every food in the food list
                        for (Food food: foodList){
                            //if the food macronutrient info is within bounds of the user's nutrients goal
                            if((food.getCalories() <= calorieGoal) && (food.getProtein() <= proteinGoal) && (food.getFats() <= fatsGoal) && (food.getCarbohydrates() <= carbGoal)){
                                // build a string of the information for the food to add to the ListView
                                String item = food.getName()+" Calories:"+food.getCalories() + " Carbs:" + food.getCarbohydrates()
                                        + " Fats:" + food.getFats() + " Protein:" + food.getProtein();
                                //since these are asynchronous calls we update the UI when we get them by using the lUIHandler
                                lUiHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        // add the string we just created to the listview via the array adapter
                                        arrayAdapter.add(item);
                                    }
                                });
                            }

                        }
                    }
                });
            }
        }).start();
    }

}