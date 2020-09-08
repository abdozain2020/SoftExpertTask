package com.nerdsboard.softexperttask.Utils;

import android.util.Log;

import com.nerdsboard.softexperttask.Models.Car;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonUtil {

    public static final String JSON_DATA_KEY = "data";

    public static List<Car> parseCarsData(String response) {
        try {
            List<Car> cars = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(response);
            JSONArray carsJsonArray = jsonObject.getJSONArray(JSON_DATA_KEY);

            for (int i = 0; carsJsonArray.length() > i; i++) {
                Log.e("JsonUtil","cars parsing =  " + i);

                // Get the values of the JSONObject
                JSONObject routineJsonObject = carsJsonArray.getJSONObject(i);

                String brand = routineJsonObject.optString("brand");
                String status = "Used";

                //TODO- change car status to boolean instead of string
                boolean carIsUsed = routineJsonObject.optBoolean("isUsed");
                if(!carIsUsed){
                    status = "New";
                }

                String year = routineJsonObject.optString("constractionYear");

                String imageUrl = routineJsonObject.optString("imageUrl");

                Car car = new Car(brand,status,imageUrl,year);
                // Add the created car to the list of cars
                cars.add(car);
            }
            return cars;
        } catch (Exception e) {
            Log.e("error",e.toString());
        }
        return Collections.emptyList();
    }

}
