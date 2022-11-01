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


        // to home screen
        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);

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
            Log.i("[method check]", "adding restaurant marker");
            mMap.addMarker(new MarkerOptions().position(restaurant.second).icon(BitmapDescriptorFactory.defaultMarker(276)).title(restaurant.first));
//        }
        Log.i("[method check]", "ALRIGHT I restaurant marker");
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker at UIC, move & zoom the camera
        LatLng location = new LatLng(41.8686, -87.6484);
        String locationName = "UIC";

//        addRestaurants(mMap);
        queryRestaurants();

        mMap.addMarker(new MarkerOptions().position(location).title(locationName));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14));

    }
    private void queryRestaurants() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("[method check]", "about to parse list");
                NutritionXRestaurantListParsing list = new NutritionXRestaurantListParsing(getContext());
                Log.i("[method check]", "I parsed the ***");
                ArrayList<Restaurant> restList = list.getRestaurantList();
                mUiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (Restaurant rest : restList) {
                            LatLng posit = new LatLng(rest.getLat(), rest.getLng());
                            addRestaurants(mMap, Pair.create(rest.getRestaurantName(), posit));
                        }
                    }
                });

            }
        }).start();

    }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
////                QueryRestaurants query = new QueryRestaurants(getActivity());
//                query.queryLocation(41.8725, -87.6493, new QueryRestaurants.VolleyResponseListener() {
//                    @Override
//                    public void onError(String message) {
//                        Log.i("[RESPONSE]", message);
//                    }
//
//                    @Override
//                    public void onResponse(JSONObject response) {
////                        Log.i("[Response object]", response.toString());
//                        NutritionXRestaurantListParsing list = new NutritionXRestaurantListParsing(getActivity());
//
//                        ArrayList<Restaurant> restList = list.getRestaurantList();
//
//                        NutritionXFoodListParsing foodList = new NutritionXFoodListParsing(getActivity());
//
//                        mUiHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                for ( Restaurant rest: restList){
//                                    LatLng posit = new LatLng(rest.getLat(),rest.getLng());
//                                    addRestaurants(mMap,Pair.create(rest.getRestaurantName(),posit));
//                                }
//
////                                arrayAdapter.add(response.toString());
//                            }
//                        });
//                    }
//                });
//            }
//        }).start();

}