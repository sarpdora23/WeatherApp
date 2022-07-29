package com.sinamekidev.weatherapp.api_package.api_models;

import com.google.gson.annotations.SerializedName;

public class Temperature {
    @SerializedName("high")
    private String high;
    @SerializedName("low")
    private String low;

    public String getHigh() {
        return this.high;
    }

    public String getLow() {
        return this.low;
    }
}
