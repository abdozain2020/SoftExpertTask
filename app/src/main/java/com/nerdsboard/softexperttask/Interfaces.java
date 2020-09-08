package com.nerdsboard.softexperttask;

import com.nerdsboard.softexperttask.Models.Car;

import java.util.List;

public class Interfaces {

    public interface getCarsData{
        void onSuccess(List<Car> carsList);
        void onFailed(String errorMessage);
    }

}
