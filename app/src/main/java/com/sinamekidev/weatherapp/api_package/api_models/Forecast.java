package com.sinamekidev.weatherapp.api_package.api_models;

import com.google.gson.annotations.SerializedName;

public class Forecast {
    @SerializedName("day")
    private String day;
    @SerializedName("temperature")
    private Temperature temperature;
    @SerializedName("thumbnail")
    private String thumbnail;

    public String getDay() {
        return this.day;
    }

    public Temperature getTemperature() {
        return this.temperature;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }
}
