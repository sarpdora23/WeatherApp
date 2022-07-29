package com.sinamekidev.weatherapp.api_package;

import com.sinamekidev.weatherapp.api_package.api_models.SearchQuery;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api_Interface {
    @GET("search.json")
    Call<SearchQuery> getQueryModel(@Query("q") String city_name,@Query("api_key") String api_key);
}
