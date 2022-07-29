package com.sinamekidev.weatherapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sinamekidev.weatherapp.api_package.api_models.Forecast;
import com.sinamekidev.weatherapp.databinding.WeatherCardBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<Forecast> weather_days;
    public RecyclerViewAdapter(List<Forecast> weather_days){
        this.weather_days = weather_days;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(WeatherCardBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Forecast forecast = weather_days.get(position);
        holder.binding.dayText.setText(forecast.getDay());
        Picasso.get().load(forecast.getThumbnail()).resize(62,62).into(holder.binding.futureWeatherImage);

        holder.binding.futureTemperatureText.setText(((Integer.valueOf(forecast.getTemperature().getHigh())-32)*5)/9 + "°C");
    }

    @Override
    public int getItemCount() {
        return weather_days.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private WeatherCardBinding binding;
        public MyViewHolder(@NonNull WeatherCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
