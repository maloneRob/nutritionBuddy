package com.G26.fitnessandnutritionbuddy;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Restaurant {
    private String restaurantName;
    private String address;
    private long lat;
    private long lng;
    private float distance;
    private String website;

    ArrayList<Food> menu;

    Restaurant() {}

    Restaurant(String restaurantName, String address, long lat, long lng, float distance, String website) {
        this.restaurantName = restaurantName;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.distance = distance;
        this.website = website;
    }

    void addMenuItem(Food foodItem) {
        menu.add(foodItem);
    }

    void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    void setAddress(String address) {
        this.address = address;
    }

    void setLat(long lat) {
        this.lat = lat;
    }

    void setLng(long lng) {
        this.lng = lng;
    }

    void setDistance(float distance) {
        this.distance = distance;
    }

    void setWebsite(String website) {
        this.website = website;
    }

    ArrayList<Food> getMenu() {
        return menu;
    }

    String getRestaurantName() {
        return restaurantName;
    }

    String getAddress() {
        return address;
    }

    long getLat() {
        return lat;
    }

    long getLng() {
        return lng;
    }

    float getDistance() {
        return distance;
    }

    String getWebsite() {
        return website;
    }

}
