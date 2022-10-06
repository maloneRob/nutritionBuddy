package com.G26.fitnessandnutritionbuddy;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.G26.fitnessandnutritionbuddy.databinding.FragmentSecondBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class SecondFragment extends Fragment implements OnMapReadyCallback {

    private FragmentSecondBinding binding;
    private GoogleMap mMap;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        System.out.println("[MethodCheck] SecondFragment onCreateView");
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        System.out.println("[MethodCheck] SecondFragment onViewCreated");

        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
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

    private void addRestaurants(GoogleMap mMap) {
        ArrayList<Pair<String, LatLng>> restaurantList = new ArrayList<>();
        restaurantList.add(Pair.create("Chick Fil A", new LatLng(41.87227543499208, -87.64773968077166)));
        restaurantList.add(Pair.create("Portillos", new LatLng(41.870422296784994, -87.64005777297913)));
        restaurantList.add(Pair.create("McDonalds", new LatLng(41.87898523054248, -87.63911369839084)));

        for(int i = 0; i < restaurantList.size(); i++) {
            Pair<String, LatLng> r = restaurantList.get(i);
            mMap.addMarker(new MarkerOptions().position(r.second).icon(BitmapDescriptorFactory.defaultMarker(276)).title(r.first));
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker at UIC, move & zoom the camera
        LatLng location = new LatLng(41.8686, -87.6484);
        String locationName = "UIC";

        addRestaurants(mMap);

        mMap.addMarker(new MarkerOptions().position(location).title(locationName));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14));

    }
}