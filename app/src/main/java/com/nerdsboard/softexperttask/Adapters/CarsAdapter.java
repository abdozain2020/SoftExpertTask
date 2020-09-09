package com.nerdsboard.softexperttask.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nerdsboard.softexperttask.Models.Car;
import com.nerdsboard.softexperttask.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CarsAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<Car> cars;

    public CarsAdapter(List<Car> cars) {
       this.cars = cars;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_data_item, parent, false);
            return new CarDataViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof CarDataViewHolder) {
            populateItemRows((CarDataViewHolder) viewHolder, position);
        }
    }

    @Override
    public int getItemCount() {
        if (cars == null) return 0;
        return cars.size();
    }

    @Override
    public int getItemViewType(int position) {
        return cars.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void populateItemRows(CarDataViewHolder viewHolder, int position) {
        Car car = cars.get(position);

        viewHolder.carBrandName.setText(car.getBrand());
        viewHolder.carStatus.setText(car.getStatus());
        viewHolder.carConstructionYear.setText(car.getConstructionYear());

        if (!car.getImageUrl().isEmpty()) {
            Picasso.get().load(car.getImageUrl()).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(viewHolder.carImage);
        } else{
            viewHolder.carImage.setImageResource(R.drawable.car_model_default);
        }

        if(car.getStatus().equals("New")){
            viewHolder.carStatus.setTextColor(Color.GREEN);
        }else{
            viewHolder.carStatus.setTextColor(Color.BLUE);
        }
    }

    class CarDataViewHolder extends RecyclerView.ViewHolder{

        TextView carBrandName,carStatus,carConstructionYear;
        CircleImageView carImage;

        CarDataViewHolder(@NonNull final View itemView) {
            super(itemView);

            carBrandName = itemView.findViewById(R.id.car_brand_textView);
            carStatus = itemView.findViewById(R.id.car_status_textView);
            carConstructionYear = itemView.findViewById(R.id.car_construction_year_textView);
            carImage = itemView.findViewById(R.id.car_image);
        }
    }

    public void changeData(List<Car> carsList){
        this.cars = carsList;
        notifyDataSetChanged();
    }
}
