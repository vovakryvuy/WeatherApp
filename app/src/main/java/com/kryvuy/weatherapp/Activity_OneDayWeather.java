package com.kryvuy.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.kryvuy.weatherapp.adapter_for_recycle.AdapterRecycle_12Hour;
import com.kryvuy.weatherapp.api.Service_Retrofit;
import com.kryvuy.weatherapp.control_mesurements.ControlMeasurements;
import com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.hourly_12hour_model.Hourly_12HourModel;
import com.kryvuy.weatherapp.start.Constant;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Володимир on 5/4/2017.
 */

public class Activity_OneDayWeather extends AppCompatActivity {
    public static final String EXTRA_KEY_LOCATION_CITY = "com.kryvuy.weatherapp.Activity_OneDayWeather.EXTRA_KEYLOCATION_CITY";
    public static final String EXTRA_NAME_CITY = "com.kryvuy.weatherapp.Activity_OneDayWeather.EXTRA_NAME_CITY";
    public static final String EXTRA_NAME_COUNTRY = "com.kryvuy.weatherapp.Activity_OneDayWeather.EXTRA_NAME_COUNTRY";
    private TextView mTextCity,mTextCountry,mTextDateDay,mTextDateMonth,mTextToolBarTemperature;
    private ImageView mImageView_icon_weather;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_day_weather);
        final Intent intent = getIntent();
        String key_location = intent.getStringExtra(Activity_OneDayWeather.EXTRA_KEY_LOCATION_CITY);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_12hour);
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(Activity_OneDayWeather.this));
                String nameCity = intent.getStringExtra(Activity_OneDayWeather.EXTRA_NAME_CITY);
                String nameCountry = intent.getStringExtra(Activity_OneDayWeather.EXTRA_NAME_COUNTRY);
                mTextCity = (TextView) findViewById(R.id.text_cocation_city);
                mTextCountry  = (TextView) findViewById(R.id.text_location_country);
                mTextDateDay = (TextView)findViewById(R.id.text_data_and_day);
                mTextDateMonth = (TextView)findViewById(R.id.text_data_and_month);
                mTextToolBarTemperature = (TextView)findViewById(R.id.text_panel_termometer);
                mImageView_icon_weather = (ImageView)findViewById(R.id.panel_icon_wether_now);
                mTextCity.setText(nameCity);
                mTextCountry.setText(nameCountry);
                //Метод для збереження ключа міста
                saveCityKeySharedPreferences(intent.getStringExtra(Activity_OneDayWeather.EXTRA_KEY_LOCATION_CITY));
                Log.d(MainActivity.LOG_TAG,"Save city key = "
                        +intent.getStringExtra(Activity_OneDayWeather.EXTRA_KEY_LOCATION_CITY)
                        +" in SharePreferences");

            }
        }).start();

        if(!key_location.equals("")){
            Service_Retrofit.getService().getHourly_12Hour(key_location, Constant.API_KEY,Constant.LANGUAGE,true,true)
                    .enqueue(new Callback<List<Hourly_12HourModel>>() {
                        @Override
                        public void onResponse(Call<List<Hourly_12HourModel>> call, Response<List<Hourly_12HourModel>> response) {
                            if(response.isSuccessful()){
                                Calendar calendar = parseDate(response.body().get(0).getDateTime());
                                SimpleDateFormat simpleDateFormat_day = new SimpleDateFormat("d");
                                SimpleDateFormat simpleDateFormat_month = new SimpleDateFormat("MMMM",new Locale("uk","UA"));
                                mTextDateDay.setText(simpleDateFormat_day.format(calendar.getTime()));
                                mTextDateMonth.setText(simpleDateFormat_month.format(calendar.getTime()));
                                mTextToolBarTemperature.setText(String.valueOf(response.body().get(0).getTemperature().getValue())+"°C");
                                mImageView_icon_weather.setImageDrawable(getApplicationContext().
                                        getDrawable(
                                                new ControlMeasurements()
                                                        .getDrawableWeatherIcon(response.body()
                                                                .get(0)
                                                                .getWeatherIcon())));
                                mRecyclerView.setAdapter(new AdapterRecycle_12Hour(response.body(),getApplicationContext()));
                            }
                        }
                        @Override
                        public void onFailure(Call<List<Hourly_12HourModel>> call, Throwable t) {

                        }
                    });
        }
    }

    private Calendar parseDate(String in_date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.ENGLISH);
        try {
            Date date = dateFormat.parse(in_date);
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            return calendar;
        }
    }

    private void saveCityKeySharedPreferences(String city_key){
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.SAVE_CITY_KEY,Context.MODE_PRIVATE);
        if (city_key != null){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constant.SAVE_CITY_KEY, city_key);
            editor.apply();
        }
    }
}

