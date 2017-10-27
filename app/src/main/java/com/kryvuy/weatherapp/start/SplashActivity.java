package com.kryvuy.weatherapp.start;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.kryvuy.weatherapp.MainActivity;
import com.kryvuy.weatherapp.MainWeatherActivity;
import com.kryvuy.weatherapp.R;
import com.kryvuy.weatherapp.api.Service_Retrofit;
import com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.daily_1day.Daily_OneDay;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Володимир on 5/7/2017.
 */

public class SplashActivity extends Activity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        /*
        initilization Realm*/
        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder().name("weatherapp.realm").build());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                SharedPreferences sharedPreferences = getSharedPreferences(Constant.SAVE_CITY_KEY, Context.MODE_PRIVATE);
                if (sharedPreferences.contains(Constant.SAVE_CITY_KEY)){
                    String city_key = sharedPreferences.getString(Constant.SAVE_CITY_KEY,"");
                    intent = new Intent(SplashActivity.this,MainWeatherActivity.class);
                    intent.putExtra(MainWeatherActivity.EXTRA_KEY_CITY,city_key);

                }else{
                    intent = new Intent(SplashActivity.this,MainActivity.class);
                }
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        },SPLASH_DISPLAY_LENGTH);

    }
}
