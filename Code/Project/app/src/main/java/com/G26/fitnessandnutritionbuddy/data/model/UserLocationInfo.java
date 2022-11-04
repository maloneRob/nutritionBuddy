package com.G26.fitnessandnutritionbuddy.data.model;

public class UserLocationInfo {
    private static double Latitude = 0.0;
    private static double Longitude = 0.0;


    public static void setLatitude(double val){
        Latitude = val;

    }
    public static void setLongitude(double val){
        Longitude = val;


    }
    public static double getLatitude()
    {
        return Latitude;
    }
    public static double getLongitude()
    {
        return Longitude;
    }

}
