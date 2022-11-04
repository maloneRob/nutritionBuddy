package com.G26.fitnessandnutritionbuddy.ui.mainmenu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.G26.fitnessandnutritionbuddy.R;
import com.G26.fitnessandnutritionbuddy.databinding.FragmentMainmenuBinding;

public class MainMenuFragment extends Fragment {
    private FragmentMainmenuBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        System.out.println("[MethodCheck] MainMenuFragment onCreateView");

        binding = FragmentMainmenuBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        System.out.println("[MethodCheck] MainMenuFragment onViewCreated");
        super.onViewCreated(view, savedInstanceState);

        binding.buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainMenuFragment.this)
                         .navigate(R.id.action_MainMenuFragment_to_profileFragment_YS);

            }
        });
        binding.buttonFindfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //
                NavHostFragment.findNavController(MainMenuFragment.this)
                        .navigate(R.id.action_MainMenuFragment_to_UserProfileFragment);
            }
        });
        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainMenuFragment.this)
                        .navigate(R.id.action_MainMenuFragment_to_FirstFragment);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}