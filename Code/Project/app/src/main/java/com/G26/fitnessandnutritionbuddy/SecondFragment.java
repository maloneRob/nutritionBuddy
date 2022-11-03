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
    Bundle user;
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
        user = activity.getSaveData();
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

                Intent intent = new Intent(getContext(), JSONActivity.class);
                startActivity(intent);
            }
        });

        // to map
        binding.buttonThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_JsonListFragment);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map3);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void addRestaurants(GoogleMap mMap, Pair<String,LatLng> restaurant) {
//        ArrayList<Pair<String, LatLng>> restaurantList = new ArrayList<>();
//        restaurantList.add(Pair.create("Chick Fil A", new LatLng(41.87227543499208, -87.64773968077166)));
//        restaurantList.add(Pair.create("Portillos", new LatLng(41.870422296784994, -87.64005777297913)));
//        restaurantList.add(Pair.create("McDonalds", new LatLng(41.87898523054248, -87.63911369839084)));

//        for(int i = 0; i < restaurantList.size(); i++) {
//            Pair<String, LatLng> r = restaurantList.get(i);
//            Log.i("[method check]", "adding restaurant marker");
//            Log.i("[restName]", restaurant.first);
//            Log.i("[latlng]", restaurant.second.toString());
            mMap.addMarker(new MarkerOptions().position(restaurant.second).icon(BitmapDescriptorFactory.defaultMarker(276)).title(restaurant.first));
//        }
//        Log.i("[method check]", "ALRIGHT I restaurant marker");
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
    private void queryRestaurants(GoogleMap mMap) {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                Log.i("[method check]", "about to parse list");
                QueryRestaurants restQuery = new QueryRestaurants(getContext());
                restQuery.queryLocation(41.8725, -87.6493, new QueryRestaurants.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Log.i("[Error response]","error getting restaurants");
                    }

                    @Override
                    public void onResponse(JSONObject response) {
                        NutritionXRestaurantListParsing list = new NutritionXRestaurantListParsing(getContext(), response);
                        ArrayList<Restaurant> restList = list.getRestaurantList();
                        for (Restaurant rest : restList){
                            LatLng posit = new LatLng(rest.getLat(), rest.getLng());
                            mUiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    addRestaurants(mMap, Pair.create(rest.getRestaurantName(), posit));
                                }
                            });
                            queryMenu(rest.getRestaurantName());
                        }
                    }
                });

            }
        }).start();

    }
    private void queryMenu(String restaurantName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                Log.i("[thread check]", "about to parse");
                QueryMenu menuQuery = new QueryMenu(getContext());
                //make a Querymenu object and call the queryMenuItems method with the restaurant name
                menuQuery.queryMenuItems(restaurantName, new QueryRestaurants.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Log.i("[queryMenuError]", "error getting json back from menu for" + restaurantName);
                    }

                    @Override
                    public void onResponse(JSONObject response) {
                        NutritionXFoodListParsing list = new NutritionXFoodListParsing(getContext(), response);
                        ArrayList<Food> foodList = list.getFoodList();
                        for (Food food: foodList){
                            String item = food.getName()+" Calories:"+food.getCalories() + " Carbs:" + food.getCarbohydrates()
                                    + " Fats:" + food.getFats() + " Protein:" + food.getProtein();
                            lUiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    arrayAdapter.add(item);
                                }
                            });
                        }
                    }
                });
            }
        }).start();
    }

}