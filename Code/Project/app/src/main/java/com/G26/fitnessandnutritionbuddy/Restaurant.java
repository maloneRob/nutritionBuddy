package com.G26.fitnessandnutritionbuddy;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Restaurant {
    private String restaurantName;
    private String address;
    private long lat;
    private long lng;
    private long distance;
    private String website;

    ArrayList<Food> menu;

    public Restaurant() {}

    public Restaurant(String restaurantName, String address, long lat, long lng, long distance, String website) {
        this.restaurantName = restaurantName;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.distance = distance;
        this.website = website;
    }

    public void addMenuItem(Food foodItem) {
        menu.add(foodItem);
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public void setLng(long lng) {
        this.lng = lng;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public ArrayList<Food> getMenu() {
        return menu;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getAddress() {
        return address;
    }

    public long getLat() {
        return lat;
    }

    public long getLng() {
        return lng;
    }

    public long getDistance() {
        return distance;
    }

    public String getWebsite() {
        return website;
    }

}
