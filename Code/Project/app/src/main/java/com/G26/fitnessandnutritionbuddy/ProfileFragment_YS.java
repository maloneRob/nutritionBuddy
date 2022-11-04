package com.G26.fitnessandnutritionbuddy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment_YS#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment_YS extends Fragment implements View.OnClickListener {

    Button back;

    public ProfileFragment_YS() {
        // Required empty public constructor
    }

    public static ProfileFragment_YS newInstance(String param1, String param2) {
        ProfileFragment_YS fragment = new ProfileFragment_YS();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile__y_s, container, false);
        back = view.findViewById(R.id.back_buttonYS);
        back.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View view) {
        Navigation.findNavController(view).navigate(R.id.action_profileFragment_YS_to_MainMenuFragment);
    }
}