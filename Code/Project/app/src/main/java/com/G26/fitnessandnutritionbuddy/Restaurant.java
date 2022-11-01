package com.G26.fitnessandnutritionbuddy;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Restaurant implements Parcelable {
    private String restaurantName;
    private String address;
    private double lat;
    private double lng;
    private long distance;
    private String website;

    ArrayList<Food> menu;

    public Restaurant() {}

    public Restaurant(String restaurantName, String address, double lat, double lng, long distance, String website) {
        this.restaurantName = restaurantName;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.distance = distance;
        this.website = website;
        this.menu = new ArrayList<Food>();
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

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
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

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public long getDistance() {
        return distance;
    }

    public String getWebsite() {
        return website;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.restaurantName);
        dest.writeString(this.address);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lng);
        dest.writeFloat(this.distance);
        dest.writeString(this.website);
        dest.writeList(this.menu);
    }

    public void readFromParcel(Parcel source) {
        this.restaurantName = source.readString();
        this.address = source.readString();
        this.lat = source.readLong();
        this.lng = source.readLong();
        this.distance = source.readLong();
        this.website = source.readString();
        this.menu = new ArrayList<Food>();
        source.readList(this.menu, Food.class.getClassLoader());
    }

    protected Restaurant(Parcel in) {
        this.restaurantName = in.readString();
        this.address = in.readString();
        this.lat = in.readLong();
        this.lng = in.readLong();
        this.distance = in.readLong();
        this.website = in.readString();
        this.menu = new ArrayList<Food>();
        in.readList(this.menu, Food.class.getClassLoader());
    }

    public static final Parcelable.Creator<Restaurant> CREATOR = new Parcelable.Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel source) {
            return new Restaurant(source);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };
}
