package com.G26.fitnessandnutritionbuddy;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class QueryMenu {
    Context context;
    JSONObject return_response;

    public QueryMenu(Context context){
        this.context = context;
    }
    public interface VolleyResponseListener{
        void onError(String message);
        void onResponse(JSONObject response);
    }

    public void queryMenuItems(String restaurantName, QueryRestaurants.VolleyResponseListener volleyResponseListener){
        //build query string for api
        //example query string
        //String url = "https://trackapi.nutritionix.com/v2/search/instant?query=wendy's&common_restaurant=true&common_grocery=false&detailed=true";
        String url = "https://trackapi.nutritionix.com/v2/search/instant?query="+ restaurantName + "&common_restaurant=true&common_grocery=false&detailed=true";
        //make query
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                return_response = response;
                Log.i("[Response]", "got a response");
                Log.i("[Response]", response.toString());
                // send response to RestaurantParser
                volleyResponseListener.onResponse(return_response);
                //ArrayList<Restaurant> restaurants = RestaurantParser.getRestaurants();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i("[RESPONSE]", "error getting response restaurants");
                volleyResponseListener.onError("error getting response restaurants");
            }
        })
        {
            /** Passing some request headers* */
                    /*Required HEADERS when accessing Nutritionix V2 API endpoints:
                    x-app-id: Your app ID issued from developer.nutritionix.com)
                    x-app-key: Your app key issued from developer.nutritionix.com)
                    x-remote-user-id:  A unique identifier to represent the end-user who is accessing the Nutritionix API.
                    If in development mode, set this to 0.  This is used for billing purposes to determine the number of active users your app has.
                    */
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("x-app-id", "5ae189a7");
                headers.put("x-app-key", "b8bc7227f1ddae4c5e81dd08c43d814e");
                headers.put("x-remote-user-id", "0");
                return headers;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(request);

//        return return_response;
    }
}
