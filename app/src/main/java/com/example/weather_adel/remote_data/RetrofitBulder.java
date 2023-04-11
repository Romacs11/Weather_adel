package com.example.weather_adel.remote_data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBulder {

    private static WeatherApi instance;
    public static WeatherApi getInstance(){
        if (instance == null){            instance = createRetrofit();
        }        return instance;
    }
    private static WeatherApi createRetrofit() {
        return new Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
            .build()                .create(WeatherApi.class);
    }}

