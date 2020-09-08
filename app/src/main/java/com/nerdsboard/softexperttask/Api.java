package com.nerdsboard.softexperttask;

import android.content.Context;
import android.util.Log;

import com.nerdsboard.softexperttask.Models.Car;
import com.nerdsboard.softexperttask.Utils.JsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Api class in the Middle Man between the app and the server, any request to the server goes through this class
 **/

public class Api {

    private final static String url = "http://demo1585915.mockable.io/api/v1/cars";

    public static void fetchCarData(Context context, final int page,
                                    final Interfaces.getCarsData getCarsDataInterface) {

        HttpCalls.get(context, Api.url+"?page="+page,  new HttpCalls.OnResponseReceived() {

            @Override
            public void onSuccess(String successResponse) {
                List<Car> carsData = JsonUtil.parseCarsData(successResponse);
                getCarsDataInterface.onSuccess(carsData);
            }

            @Override
            public void onError(String errorMessage) {
                getCarsDataInterface.onFailed(errorMessage);
            }
        });
    }
}