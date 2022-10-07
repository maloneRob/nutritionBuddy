package com.G26.fitnessandnutritionbuddy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FoodRecyclerViewAdaptor extends RecyclerView.Adapter<FoodRecyclerViewAdaptor.MyViewHolder> {

    private ArrayList<Food> foodList = new ArrayList<>();

    public FoodRecyclerViewAdaptor(ArrayList<Food> fList) {
        this.foodList = fList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View foodRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_row, parent, false);

        MyViewHolder myHolder = new MyViewHolder(foodRow);

        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodRecyclerViewAdaptor.MyViewHolder holder, int position) {

        String name = "Name: " + foodList.get(position).getName();
        holder.nameTextView.setText(name);

        String cals = "Calories: " + foodList.get(position).getCalories();
        holder.calsTextView.setText(cals);

        String fats = "Fats: " + foodList.get(position).getFats();
        holder.fatsTextView.setText(fats);

        String carbs = "Carbohydrates: " + foodList.get(position).getCarbohydrates();
        holder.carbsTextView.setText(carbs);

        String protein = "Protein: " + foodList.get(position).getProtein();
        holder.proteinTextView.setText(protein);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView calsTextView;
        private TextView fatsTextView;
        private TextView carbsTextView;
        private TextView proteinTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.nameTextView);
            calsTextView = itemView.findViewById(R.id.caloriesTextView);
            fatsTextView = itemView.findViewById(R.id.fatsTextView);
            carbsTextView = itemView.findViewById(R.id.carbohydratesTextView);
            proteinTextView = itemView.findViewById(R.id.proteinTextView);
        }
    }
}