package com.example.weather_adel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.weather_adel.databinding.ActivityMainBinding;
import com.example.weather_adel.models.Example;
import com.example.weather_adel.models.WeatherModel;
import com.example.weather_adel.remote_data.RetrofitBulder;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private  LottieAnimationView lotty_bad_weather;
    String my_time =
            java.text.DateFormat.getDateTimeInstance().format(new Date());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        lotty_bad_weather = findViewById(R.id.lotty_bad_weather);
        lotty_bad_weather.setAnimation(R.raw.women_in_rain);
        binding.badWeatherCloud1.setVisibility(View.VISIBLE);
        binding.badWeatherCloud2.setVisibility(View.VISIBLE);
        lotty_bad_weather.setVisibility(View.VISIBLE);
        binding.sun.setVisibility(View.VISIBLE);
        binding.goodWeatherSky.setVisibility(View.VISIBLE);



        {

            final String apiKey = "3f79e84e432fbf9a0993d59f8f07ef72";

            @Override
            public void onClick(View v) {
                RetrofitBulder.getInstance().getCurrentWeather(cityName.getText().toString()
                        .trim(), apiKey).enqueue(new Callback<Example>() {
                            @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            WeatherModel model = response.body().getModel();

                            Double temp = model.getTemp();
                            Integer humidity = model.getHumidity();
                            Integer pressure = model.getPressure();
                            Double tempMax = model.getTempMax();
                            Double tempMin = model.getTempMin();
                            long sunrise = model.getSunrise();
                            long sunset = model.getSunset();

                            if (temp < 15.0) {
                                binding.editCity.setVisibility(View.VISIBLE);
                            } else {
                                binding.editCity.setVisibility(View.INVISIBLE);
                                binding.btnShow.setVisibility(View.VISIBLE);
                                Toast.makeText(MainActivity.this, "Ураа, там солнышко!", Toast.LENGTH_SHORT).show();
                            }
                            Integer tMax = (int) (tempMax - 273.15);
                            Integer tMin = (int) (tempMin - 273.15);
                            Integer pressure_mBar = pressure / 1000;
                            Integer temperature = (int) (temp - 273.15);
                           binding.textGradus.setText(String.valueOf(temperature));
                        }
                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "К сожалению, данные о погоде не пришли!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        showBottomSheet();
        setRotationForArrow();
    }

    private void setRotationForArrow() {
        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                header_Arrow_Image.setRotation(slideOffset * 180);
            }
        });
    }

    private void showBottomSheet() {
        header_Arrow_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
    }
}

