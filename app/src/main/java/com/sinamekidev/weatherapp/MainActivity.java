package com.sinamekidev.weatherapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.sinamekidev.weatherapp.adapter.RecyclerViewAdapter;
import com.sinamekidev.weatherapp.api_package.Api_Interface;
import com.sinamekidev.weatherapp.api_package.api_models.AnswerBox;
import com.sinamekidev.weatherapp.api_package.api_models.Forecast;
import com.sinamekidev.weatherapp.api_package.api_models.SearchQuery;
import com.sinamekidev.weatherapp.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private String API_KEY;
    private ActivityMainBinding binding;
    private AnswerBox answerBox;
    private RecyclerViewAdapter adapter;
    private List<Forecast> forecastList;
    private LocationManager locationManager;
    private ActivityResultLauncher<String> permission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        API_KEY = getString(R.string.api_key);
        registar();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getInfo("default");
    }
    private void registar(){
        permission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result){
                    getInfo("default");
                }
                else{
                    Snackbar.make(MainActivity.this,binding.layoutView,"Permission needed for get location",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
    private void getInfo(String postalCode) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://serpapi.com/").addConverterFactory(GsonConverterFactory.create()).build();
        Api_Interface apiInterface = retrofit.create(Api_Interface.class);
        if(postalCode.equals("default")){
            getLocation();
        }
        else{
            String qu = postalCode + " weather";
            apiInterface.getQueryModel(qu, API_KEY).enqueue(new Callback<SearchQuery>() {
                @Override
                public void onResponse(Call<SearchQuery> call, Response<SearchQuery> response) {
                    if (response.isSuccessful()) {
                        SearchQuery searchQuery = response.body();
                        answerBox = searchQuery.getAnswerBox();
                        Picasso.get().load(answerBox.getThumbnail()).resize(129, 129).into(binding.todayWeatherImage);
                        binding.cityText.setText(answerBox.getLocation());
                        binding.todayHumidityText.setText(answerBox.getHumidity());
                        binding.todayPrecipitationText.setText(answerBox.getPrecipitation());
                        binding.todayTemperature.setText(((Integer.valueOf(answerBox.getTemperature()) - 32) * 5) / 9 + "°C");
                        binding.todayWindText.setText(answerBox.getWind());
                        forecastList = answerBox.getForecasts();
                        adapter = new RecyclerViewAdapter(forecastList);
                        binding.daysRecyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        binding.daysRecyclerview.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<SearchQuery> call, Throwable t) {
                    Snackbar.make(findViewById(R.id.layout_view), t.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            });
        }

    }

    private void getLocation() {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(MainActivity.this,binding.layoutView,"Allow location permission",Snackbar.LENGTH_INDEFINITE).setAction("Allow", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    permission.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                }
            }).show();
        }
        else{
            System.out.println("DEBUG 5");
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    try {
                        List<Address> addressList= geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),5);
                        Address address = addressList.get(0);
                        String str = address.getAdminArea();
                        System.out.println("LOCATION: " + str);
                        getInfo(str);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }
}