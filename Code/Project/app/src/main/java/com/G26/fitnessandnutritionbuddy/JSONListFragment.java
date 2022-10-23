package com.G26.fitnessandnutritionbuddy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.G26.fitnessandnutritionbuddy.databinding.FragmentJsonListBinding;
import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;

public class JSONListFragment extends Fragment {

    private FragmentJsonListBinding binding;
    private GoogleMap mMap;
    private ArrayList<Food> foodList;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        System.out.println("[MethodCheck] JsonListFragment onCreateView");
        binding = FragmentJsonListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        FoodListParser foodListParser = new FoodListParser(getContext());
        foodList = foodListParser.getFoodList();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        System.out.println("[MethodCheck] JsonListFragment onViewCreated");

        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(JSONListFragment.this)
                        .navigate(R.id.action_fragmentJsonList_to_second_fragment);
            }
        });

        for (int i = 0; i < foodList.size(); i++) {
            TextView tv = new TextView(getContext());
            Food f = foodList.get(i);
            tv.setTextSize(35);

            tv.setText(String.format("%s\n  Calories: %s\n  Carbohydrates: %s\n  Fats: %s\n  Proteins: %s", f.getName(), f.getCalories(), f.getCarbohydrates(), f.getFats(), f.getProtein()));
            binding.linearlayout.addView(tv);
        }
        binding.linearlayout.setBackgroundColor(Color.parseColor("#347836"));
    }

}
