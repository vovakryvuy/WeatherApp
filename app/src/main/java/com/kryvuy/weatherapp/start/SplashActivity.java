package com.kryvuy.weatherapp.start;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;

import com.kryvuy.weatherapp.MainActivity;
import com.kryvuy.weatherapp.MainWeatherActivity;
import com.kryvuy.weatherapp.R;
import com.kryvuy.weatherapp.api.Service_Retrofit;
import com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.daily_1day.Daily_OneDay;
import com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.locations.geoposition.GeopositionSearchModel;
import com.kryvuy.weatherapp.position.GPSTracker;

import java.io.IOException;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Володимир on 5/7/2017.
 */

public class SplashActivity extends Activity {
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private String cityKey  = null;
    private String cityName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        /*
        initilization Realm*/
        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder().name("weatherapp.realm").build());

        GPSTracker gpsTracker = new GPSTracker(SplashActivity.this);
        if(gpsTracker.canGetLocation){
            Log.d(MainActivity.LOG_TAG, "Latitude = "+gpsTracker.getLatitude());
            Log.d(MainActivity.LOG_TAG, "Longitude = "+gpsTracker.getLongitude());
            Double latitude = gpsTracker.getLatitude();
            Double longitude = gpsTracker.getLongitude();
            if (latitude != 0.0 && longitude != 0.0){
                Service_Retrofit.getService().getGeoposition(Constant.API_KEY,
                        latitude+","+longitude,Constant.LANGUAGE_UA,
                        false,false).enqueue(new Callback<GeopositionSearchModel>() {
                    @Override
                    public void onResponse(Call<GeopositionSearchModel> call, Response<GeopositionSearchModel> response) {
                        if(response.body().getKey()==null){
                            Log.d(MainActivity.LOG_TAG, "onResponse: error"+response.errorBody().toString());
                        }else {
                            cityKey = response.body().getKey();
                            cityName = response.body().getLocalizedName();
                        }
                    }

                    @Override
                    public void onFailure(Call<GeopositionSearchModel> call, Throwable t) {

                    }
                });


            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                SharedPreferences sharedPreferences = getSharedPreferences(Constant.SAVE_CITY_KEY, Context.MODE_PRIVATE);
                if (sharedPreferences.contains(Constant.SAVE_CITY_KEY) || cityKey!=null){
                    intent = new Intent(SplashActivity.this,MainWeatherActivity.class);
                    if(cityKey!=null){
                        if(cityName!=null){
                            intent.putExtra(MainWeatherActivity.EXTRA_NAME_CITY,cityName);
                            //save name in SharePreferences
                            saveCityNameInSharedPreferences(getApplicationContext(),cityName);
                        }
                    }else{
                        cityKey = sharedPreferences.getString(Constant.SAVE_CITY_KEY,"");
                    }
                    intent.putExtra(MainWeatherActivity.EXTRA_KEY_CITY,cityKey);

                }else{
                    intent = new Intent(SplashActivity.this,MainActivity.class);
                }
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        },SPLASH_DISPLAY_LENGTH);

    }

    public void setLanguages(){
            Locale myLocale = new Locale("uk_UA");
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.setLocale(myLocale);
            res.updateConfiguration(conf, dm);
    }

    private void saveCityNameInSharedPreferences(Context context,String cityName){
        SharedPreferences sharedPreferences_nameCity = context
                .getSharedPreferences(Constant.SAVE_NAME_CITY,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences_nameCity.edit();
        editor.putString(Constant.SAVE_NAME_CITY, cityName);
        editor.apply();
    }
}
