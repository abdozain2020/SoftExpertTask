package com.nerdsboard.softexperttask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
    SwipeRefreshLayout pullToRefresh;
    boolean isLoading = false;
    List<Car> carsArrayList = new ArrayList<>();
    int lastLoadedPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        handleViewsActions();
        setupCarsRecyclerView();
        fetchCarsData(false);
        initScrollListener();
    }

    void initializeViews() {
        carsRecyclerView = findViewById(R.id.cars_recycler);
        progressBar = findViewById(R.id.progressBar);
        pullToRefresh = findViewById(R.id.pullToRefresh);
    }

    void handleViewsActions() {
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchCarsData(false);
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    private void setupCarsRecyclerView(){
        carsAdapter = new CarsAdapter(carsArrayList);
        carsRecyclerView.setAdapter(carsAdapter);
        carsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void handleActionsForRefreshSuccessFromPagination(List<Car> carsList){
        removeLoadingItemFromAdapter();
        carsArrayList.addAll(carsList);
        carsAdapter.changeData(carsArrayList);
        lastLoadedPage +=1;
        isLoading = false;
    }

    private void handleActionsForRefreshSuccessFromRefresh(List<Car> carsList){
        carsArrayList = carsList;
        lastLoadedPage = 1;
        carsAdapter.changeData(carsList);
        progressBar.setVisibility(View.GONE);
    }

    private void removeLoadingItemFromAdapter(){
        carsArrayList.remove(carsArrayList.size() - 1);
        carsAdapter.notifyItemRemoved(carsArrayList.size());
    }

    private void fetchCarsData(final boolean forPagination) {
        //show loader only at first loading
        if(lastLoadedPage == 1 && !forPagination){progressBar.setVisibility(View.VISIBLE);}

        int page = 1;
        if(forPagination){page = lastLoadedPage+1;}

        Api.fetchCarData(MainActivity.this,page, new Interfaces.getCarsData() {
            @Override
            public void onSuccess(List<Car> carsList) {
                if(forPagination){
                    handleActionsForRefreshSuccessFromPagination(carsList);
                }else{
                    handleActionsForRefreshSuccessFromRefresh(carsList);
                }
            }

            @Override
            public void onFailed(String errorMessage) {
                if(forPagination){
                    isLoading = false;
                    removeLoadingItemFromAdapter();
                }else {
                    progressBar.setVisibility(View.GONE);
                }
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initScrollListener() {
        carsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == carsArrayList.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void loadMore() {
        carsArrayList.add(null);
        carsAdapter.notifyItemInserted(carsArrayList.size() - 1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchCarsData(true);
            }
        }, 2000);
    }
}

