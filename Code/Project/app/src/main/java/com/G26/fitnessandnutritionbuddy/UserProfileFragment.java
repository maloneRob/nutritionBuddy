package com.G26.fitnessandnutritionbuddy;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.G26.fitnessandnutritionbuddy.data.model.UserProfile;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    UserProfile user;
    EditText userName, calories, fats, proteins, carbs;
    JSONObject restaurantJSON = null;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("[MethodCheck] UserProfileFragment onCreate");
        super.onCreate(savedInstanceState);
        // gets the saved bundle from the main activity
        MainActivity activity = (MainActivity) getActivity();
        Bundle savedData = activity.getSaveData();
        // attach it the instance of UserProfile
        user = savedData.getParcelable("profile");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("[MethodCheck] UserProfileFragment onCreateView");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        System.out.println("[MethodCheck] UserProfileFragment onViewCreated");
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);
        userName = (EditText) view.findViewById(R.id.editTextTextUserProfileName);
        calories = (EditText) view.findViewById(R.id.editTextTextUserProfileCalories);
        carbs = (EditText) view.findViewById(R.id.editTextTextUserProfileCarbs);
        fats = (EditText) view.findViewById(R.id.editTextTextUserProfileFats);
        proteins = (EditText) view.findViewById(R.id.editTextTextUserProfileProteins);
        //find meal button
        Button button = view.findViewById(R.id.btnUserProfile);

        //fill in text fields
        userName.setText(user.getDisplayName());
        userName.setEnabled(false);
        calories.setText(user.getNutrientGoal("calories").toString());
        carbs.setText(user.getNutrientGoal("carbohydrates").toString());
        fats.setText(user.getNutrientGoal("fats").toString());
        proteins.setText(user.getNutrientGoal("protein").toString());

        //find meal button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create new bundle to send back
                Bundle newGoals = new Bundle();
                //update user class with new goals taken from text fields
                user.updateCount(true, "calorie", Integer.valueOf(calories.getText().toString()));
                user.updateCount(true, "carbohydrate", Integer.valueOf(carbs.getText().toString()));
                user.updateCount(true, "fats", Integer.valueOf(fats.getText().toString()));
                user.updateCount(true, "proteins", Integer.valueOf(proteins.getText().toString()));
                //store parcelable
                newGoals.putParcelable("profile", user);
                //call .saveData to store info from class in activity
                MainActivity activity = (MainActivity) getActivity();
                activity.saveData(newGoals);

                // todo
                // get user location
                //float lat = LocationClass.getLat();


                double tmpLat = 41.5250;
                double tmpLng = -88.0817;
                //float lng = LocationClass.getLng()
                //instantiate instance of QueryRestaurant class
                QueryRestaurants query = new QueryRestaurants(getActivity());

                // .queryLocation(float, float, new VolleyResponse listener(){)
                // must implement the onError and onResponse methods
                query.queryLocation(tmpLat, tmpLng, new QueryRestaurants.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        // print error message
                        Log.i("[RESPONSE]", message);
                    }

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("[RESPONSE]", response.toString());
                        restaurantJSON = response;
                        Log.i("[Response object]", restaurantJSON.toString());

                        if (restaurantJSON != null) {
                            Log.i("[Response]", "about to start parsing the json");

                            // todo
                            // pass JSON to RestaurantParser here
                            // RestaurauntParser rp = new RestaurantParser(restaurantJSON);

                            // get the list of RestaurantObjects
                            // ArrayList<Restaurant> restaurantsList = rp.getRestaurants();

                            //todo
                            // implement new class QueryMenu.java
                            //will fix syntax
                            /*for (Restaurant restaurants : restuarantsList){
                                make seperate query for each restaurant
                                parse the JSON returned
                                get food objects and insert them into restuarant's menu

                            }
                            pass back arraylist of restaurant objects to the main activity to save to fragment
                            put the restaurants on the gmap
                            parse the menus to the listview
                            */

                        } else {
                            Log.i("[RESPONSE]", "ERROR, EITHER NO RESTAURANTS OR ERROR IN METHOD");

                        }

                    }
                });

                //transition to the next fragment
                navController.navigate(R.id.action_userProfileFragment_to_SecondFragment);
            }
        });
    }
}