package com.G26.fitnessandnutritionbuddy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import  android.util.Log;

import com.G26.fitnessandnutritionbuddy.data.model.UserProfile;
import com.G26.fitnessandnutritionbuddy.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {
    static int PERSONAL_ID = 1;
    private FragmentFirstBinding binding;
    EditText userName;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get username edit text id
        userName = (EditText) view.findViewById(R.id.editTextTextPersonName);
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                editTextTextPersonName
                String username = userName.getText().toString();
                Log.d("success",username);
                UserProfile profile = new UserProfile(username);

                Bundle bundle = new Bundle();
                bundle.putParcelable("profile", profile);
                MainActivity activity = (MainActivity) getActivity();
                activity.saveData(bundle);

                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_userProfileFragment);

            }
        });
        binding.buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_loginFragment);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}