package com.sinamekidev.weatherapp.api_package.api_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnswerBox {
    @SerializedName("temperature")
    private String temperature;
    @SerializedName("precipitation")
    private String precipitation;
    @SerializedName("humidity")
    private String humidity;
    @SerializedName("wind")
    private String wind;
    @SerializedName("location")
    private String location;
    @SerializedName("date")
    private String date;
    @SerializedName("weather")
    private String weather;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("forecast")
    private List<Forecast> forecasts;

    public String getTemperature() {
        return this.temperature;
    }

    public String getPrecipitation() {
        return this.precipitation;
    }

    public String getHumidity() {
        return this.humidity;
    }

    public String getWind() {
        return this.wind;
    }

    public String getLocation() {
        return this.location;
    }

    public String getDate() {
        return this.date;
    }

    public String getWeather() {
        return this.weather;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }
}
