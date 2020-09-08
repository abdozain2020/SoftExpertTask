package com.nerdsboard.softexperttask.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        RecyclerView.Adapter<CarsAdapter.CarDataViewHolder> {

    private Context context;
    private List<Car> cars;

    public CarsAdapter(Context context, List<Car> cars) {
        this.context = context;
        this.cars = cars;
    }

    @NonNull
    @Override
    public CarDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.car_data_item, parent,
                false);

        return new CarsAdapter.CarDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarDataViewHolder holder, int position) {
        Car car = cars.get(position);

        holder.carBrandName.setText(car.getBrand());
        holder.carStatus.setText(car.getStatus());
        holder.carConstructionYear.setText(car.getConstructionYear());

        Picasso.get().load(car.getImageUrl()).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(holder.carImage);
    }

    @Override
    public int getItemCount() {
        if (cars == null) return 0;
        return cars.size();
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
}
