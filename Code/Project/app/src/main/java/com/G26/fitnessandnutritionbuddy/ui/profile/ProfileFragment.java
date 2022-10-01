package com.G26.fitnessandnutritionbuddy.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.G26.fitnessandnutritionbuddy.databinding.FragmentLoginBinding;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    // need to make binding class but where /?

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

//        binding = FragmentLoginBinding.inflate(inflater, container, false);
//        return binding.getRoot();
        return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
