package com.example.weather_adel.remote_data;

import com.example.weather_adel.models.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WeatherApi {
    @GET("data/.2.5/weather")
    Call<Example> getCurrentWeather(
            @Query("g") String name,
            @Query("appid") String key);

    String url = "api.openweathermap.org/data/2.5/weather?q=London& appid=3f79e84e432fbf9a0993d59f8f07ef72\"\n";
}
