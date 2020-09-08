package com.nerdsboard.softexperttask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nerdsboard.softexperttask.Adapters.CarsAdapter;
import com.nerdsboard.softexperttask.Models.Car;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView carsRecyclerView;
    CarsAdapter carsAdapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        fetchCarsData();
    }

    void initializeViews() {
        carsRecyclerView = findViewById(R.id.cars_recycler);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupCarsRecyclerView(List<Car> carsData) {
        carsAdapter = new CarsAdapter(this,carsData);
        carsRecyclerView.setAdapter(carsAdapter);
        carsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void fetchCarsData() {
        progressBar.setVisibility(View.VISIBLE);

        int page = 1;

        Api.fetchCasData(MainActivity.this,page, new Interfaces.getCarsData() {
            @Override
            public void onSuccess(List<Car> carsList) {
                setupCarsRecyclerView(carsList);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailed(String errorMessage) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}