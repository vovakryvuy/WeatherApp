package com.kryvuy.weatherapp;

import android.Manifest;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kryvuy.weatherapp.adapter_for_recycle.AdapterRecycle_12Hour;
import com.kryvuy.weatherapp.adapter_for_recycle.AdapterRecycle_5Days;
import com.kryvuy.weatherapp.api.Service_Retrofit;
import com.kryvuy.weatherapp.control_mesurements.ControlMeasurements;
import com.kryvuy.weatherapp.data_base.DatabaseWetherFiveDay;
import com.kryvuy.weatherapp.data_base.DatabaseWetherTwelveHour;
import com.kryvuy.weatherapp.dialog.DialogSearchCity;
import com.kryvuy.weatherapp.dialog.DialogSpecialView_12Hours;
import com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.daily_5days.DailyForecast;
import com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.daily_5days.Daily_FiveDay;
import com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.hourly_12hour_model.Hourly_12HourModel;
import com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.search_city.SearchCityByName;
import com.kryvuy.weatherapp.start.Constant;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Володимир on 9/4/2017.
 */

public class MainWeatherActivity extends AppCompatActivity
        implements MenuItemCompat.OnActionExpandListener, SearchView.OnQueryTextListener {
    public static final String EXTRA_KEY_CITY = "com.kryvuy.weatherapp.MainWeatherActivity.EXTRA_KEY_CITY";
    public static final String EXTRA_NAME_CITY = "MainWeatherActivity.EXTRA_NAME_CITY";
    public static final int REAULT_FOR_ACTIVITY_SERACH_CITY = 1;
    private Bundle saveInstanceState_second;
    private RecyclerView mRecyclerViewHours;
    private RecyclerView mRecyclerViewDays;
    private String mKeyCity;
    private String mNameCity;
    private MenuItem searchMenuItem;
    private SearchView searchView;
    private DialogSearchCity mDialogFragment = new DialogSearchCity();
    private List<String> mListCity_CountryName = new ArrayList<>();
    private List<DatabaseWetherFiveDay> mDatabaseWetherFiveDayList;
    private List<DatabaseWetherTwelveHour> mDatabaseWetherTwelveHourList;
    private Boolean mStatusTTS = false;
    private int mCount_wetherDays = 0;
    private int mCount_wetherHours = 0;
    private int toastCount = 0;
    private Toast mToast;
    private Toolbar mToolbar;
    private Realm mRealm;
    private TextToSpeech mTextToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveInstanceState_second = savedInstanceState;
        setContentView(R.layout.activity_wether_main_layout);
        Log.d(MainActivity.LOG_TAG, "-----------START------------");

        /*
        inizializi RealmDataBase*/
        Realm.init(this);
        mRealm = Realm.getDefaultInstance();
        /*
        create weather list for TTS*/
        createWetherListForTTS();
        /*
        inizializi TextToSpeech*/
        mTextToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    mTextToSpeech.setLanguage(Locale.getDefault());
                }
            }
        });

        /*Get Preferenses key City */
        Intent intent = getIntent();
        mKeyCity = intent.getStringExtra(MainWeatherActivity.EXTRA_KEY_CITY);
        mNameCity = intent.getStringExtra(MainWeatherActivity.EXTRA_NAME_CITY);

        //mKeyCity = intent.getStringExtra(EXTRA_KEY_CITY);
        //save city_key in SharePreferences*/
        saveCityKeySharedPreferences(intent);

        //init and view city name in Toolbar Title with SharePreferences
        initToolbar();


        /*
        RecycleView 5Days Wether*/
        mRecyclerViewDays = (RecyclerView) findViewById(R.id.my_recycler_view_5days);
        mRecyclerViewDays.setHasFixedSize(true);
        mRecyclerViewDays.setLayoutManager(
                new LinearLayoutManager(MainWeatherActivity.this,LinearLayoutManager.HORIZONTAL,false));
        /*
        RecycleView 12Hours Wether*/
        mRecyclerViewHours = (RecyclerView) findViewById(R.id.my_recycler_view_12hour);
        mRecyclerViewHours.setHasFixedSize(true);
        mRecyclerViewHours.setLayoutManager(
                new LinearLayoutManager(MainWeatherActivity.this,LinearLayoutManager.VERTICAL,false));

        Service_Retrofit.getService().getDaily_5Day(mKeyCity,Constant.API_KEY,Constant.LANGUAGE_UA,true,true)
                .enqueue(new Callback<Daily_FiveDay>() {
                    @Override
                    public void onResponse(Call<Daily_FiveDay> call, Response<Daily_FiveDay> response) {
                        mRecyclerViewDays.setAdapter(new AdapterRecycle_5Days(response.body(),getApplicationContext()));
                        /*
                        save five days in database*/
                        if(response.body().getDailyForecasts() == null){
                            mRecyclerViewDays.setAdapter(new AdapterRecycle_5Days(null,getApplicationContext()));
                        }else {
                            saveFiveDayInDatabaseRealm(response.body());
                        }
                    }
                    @Override
                    public void onFailure(Call<Daily_FiveDay> call, Throwable t) {
                        Log.d(MainActivity.LOG_TAG, "onFailure: "+t.getMessage()+ " open realm and push data");
                        mRecyclerViewDays.setAdapter(new AdapterRecycle_5Days(null,getApplicationContext()));
                    }
                });

        Service_Retrofit.getService().getHourly_12Hour(mKeyCity,Constant.API_KEY,Constant.LANGUAGE_UA,true,true)
                .enqueue(new Callback<List<Hourly_12HourModel>>() {
                    @Override
                    public void onResponse(Call<List<Hourly_12HourModel>> call, Response<List<Hourly_12HourModel>> response) {

                        mRecyclerViewHours.setAdapter(new AdapterRecycle_12Hour(response.body(),getApplicationContext()));
                        /*
                        save twelve hours in database*/
                        if(response.body().iterator().next().getDateTime() == null){
                            mRecyclerViewHours.setAdapter(new AdapterRecycle_12Hour(null,getApplicationContext()));
                        }else {
                            try {
                                saveTwelveHoursInDatabaseRealm(response.body());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Hourly_12HourModel>> call, Throwable t) {
                        Log.d(MainActivity.LOG_TAG, "onFailure: "+t.getMessage()+ " open realm and push data");
                        mRecyclerViewHours.setAdapter(new AdapterRecycle_12Hour(null,getApplicationContext()));
                    }
                });
        Log.d(MainActivity.LOG_TAG,"------------END-------------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mTextToSpeech.stop();
        mTextToSpeech.shutdown();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_toolbar, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) searchMenuItem.getActionView();
        mSearchView.setOnQueryTextListener(MainWeatherActivity.this);
        MenuItemCompat.setOnActionExpandListener(searchMenuItem, this);
        MenuItemCompat.setActionView(searchMenuItem,mSearchView);
        searchMenuItem.setVisible(true);

       /* MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar_weather, menu);

       *//* SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);*//*
        searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchMenuItem.getActionView();

        *//*searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));*//*
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);*/
        return true;
    }
    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }
    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(MainActivity.LOG_TAG, "MainWeatherActivity  onQueryTextSubmit: "+query);
        if( query!=null && !query.isEmpty() ) {
            Intent intent = new Intent(MainWeatherActivity.this,ActivityListCity.class);
            intent.putExtra(ActivityListCity.EXTRA_STRING_FOR_SEARCH_CITY,query);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivityForResult(intent,REAULT_FOR_ACTIVITY_SERACH_CITY);
        }

        return true;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(MainActivity.LOG_TAG, "MainWeatherActivity  onQueryTextChange: "+newText);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REAULT_FOR_ACTIVITY_SERACH_CITY && resultCode == Activity.RESULT_OK){
            Log.d(MainActivity.LOG_TAG, "onActivityResult: "+resultCode);
            Log.d(MainActivity.LOG_TAG, "SANGE data after change city: -------");

            Service_Retrofit.getService().getDaily_5Day(mKeyCity,Constant.API_KEY,Constant.LANGUAGE_UA,true,true)
                    .enqueue(new Callback<Daily_FiveDay>() {
                        @Override
                        public void onResponse(Call<Daily_FiveDay> call, Response<Daily_FiveDay> response) {
                            mRecyclerViewDays.setAdapter(new AdapterRecycle_5Days(response.body(),getApplicationContext()));
                        /*
                        save five days in database*/
                            saveFiveDayInDatabaseRealm(response.body());
                        }
                        @Override
                        public void onFailure(Call<Daily_FiveDay> call, Throwable t) {
                            Log.d(MainActivity.LOG_TAG, "onFailure: "+t.getMessage()+ " open realm and push data");
                            mRecyclerViewDays.setAdapter(new AdapterRecycle_5Days(null,getApplicationContext()));
                        }
                    });

            Service_Retrofit.getService().getHourly_12Hour(mKeyCity,Constant.API_KEY,Constant.LANGUAGE_UA,true,true)
                    .enqueue(new Callback<List<Hourly_12HourModel>>() {
                        @Override
                        public void onResponse(Call<List<Hourly_12HourModel>> call, Response<List<Hourly_12HourModel>> response) {

                            mRecyclerViewHours.setAdapter(new AdapterRecycle_12Hour(response.body(),getApplicationContext()));
                        /*
                        save twelve hours in database*/
                            try {
                                saveTwelveHoursInDatabaseRealm(response.body());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(getApplicationContext(),getApplicationContext()
                                            .getResources()
                                            .getText(R.string.string_text_data_updated), Toast.LENGTH_LONG)
                                    .show();
                        }

                        @Override
                        public void onFailure(Call<List<Hourly_12HourModel>> call, Throwable t) {
                            Log.d(MainActivity.LOG_TAG, "onFailure: "+t.getMessage()+ " open realm and push data");
                            mRecyclerViewHours.setAdapter(new AdapterRecycle_12Hour(null,getApplicationContext()));
                        }
                    });
            //init and view city name in Toolbar Title with SharePreferences
            initToolbar();

            createWetherListForTTS();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mStatusTTS){
            //Cancel Toast while Toast work
            if(toastCount >= 1){
                mToast.cancel();
                toastCount = 0;
            }
            mToast = new Toast(getApplicationContext());
            toastCount++;
            //////////////
            if(mTextToSpeech.isSpeaking())
                mTextToSpeech.stop();

            switch (keyCode){
                case KeyEvent.KEYCODE_VOLUME_UP:
                    Log.d(MainActivity.LOG_TAG, "PRESSS BUTTON UP start speech 5Days");
                        mTextToSpeech.speak(speechAboutDaysWether(mCount_wetherDays % 5)
                                ,TextToSpeech.QUEUE_FLUSH,null,null);

                    //Special view weather 5 Day
                    Log.d(MainActivity.LOG_TAG, "onKeyUp: Start Special View 5Day Weather");
                        viewToastSpecial_Day(mDatabaseWetherFiveDayList.get(mCount_wetherDays%5),mToast);
                        mRecyclerViewDays.onScrolled((mCount_wetherDays%5)*50,0);
                        mCount_wetherDays++;
                    break;
                case KeyEvent.KEYCODE_VOLUME_DOWN:
                    Log.d(MainActivity.LOG_TAG, "PRESSS BUTTON DOWN start speech 12Hours");
                        mTextToSpeech.speak(speechAboutHoursWeather(mCount_wetherHours % 12)
                                , TextToSpeech.QUEUE_FLUSH, null, null);

                        //Special view weather 12 Hours
                        Log.d(MainActivity.LOG_TAG, "onKeyDown: Start Special View 12Hours Weather");
                        viewToastSpecial_Hour(mDatabaseWetherTwelveHourList.get(mCount_wetherHours%12),mToast);
                        mCount_wetherHours++;
                    break;
                default:
                    return super.onKeyDown(keyCode, event);
            }
        }
        return true;
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            Log.d(MainActivity.LOG_TAG,"LONG PRESSS BUTTON DOWN AND UP");
            return true;
        }else {
            return super.onKeyLongPress(keyCode, event);
        }
    }

    private void createListForCityContry(){
        if(!mListCity_CountryName.isEmpty()){
            Log.d(MainActivity.LOG_TAG,"start dialog serch city and country");
            mDialogFragment.setListCityCountry(mListCity_CountryName);
            mDialogFragment.show(getSupportFragmentManager(),"dialog_fragment_serch_city");
        }
    }

    public  void saveFiveDayInDatabaseRealm(Daily_FiveDay dailyFiveDay){

        List<DatabaseWetherFiveDay> databaseWetherFiveDaysList = new ArrayList<>();
        if (!mRealm.where(DatabaseWetherFiveDay.class).isValid()){
            //Create Database Model class
            mRealm.createObject(DatabaseWetherFiveDay.class);
            Log.d(MainActivity.LOG_TAG,"Create Database Model class");
        }

        //TEST LOG data 5Day weather
        int i = 0;
        for(DailyForecast dailyForecast : dailyFiveDay.getDailyForecasts()){
            DatabaseWetherFiveDay databaseWetherFiveDay = new DatabaseWetherFiveDay();

            databaseWetherFiveDay.setId(i);
            Log.d(MainActivity.LOG_TAG,"====== DAY "+ i +" ======");
            databaseWetherFiveDay.setData(new ControlMeasurements()
                    .parseDateToString_MonthAndDay(dailyForecast.getDate()));
            Log.d(MainActivity.LOG_TAG,"Month and Day = "
                    +new ControlMeasurements().parseDateToString_MonthAndDay(dailyForecast.getDate()));

            databaseWetherFiveDay.setDayTemperature(dailyForecast
                    .getTemperature().getMaximum().getValue());
            Log.d(MainActivity.LOG_TAG,"dayTemperature = "
                    +dailyForecast.getTemperature().getMaximum().getValue());

            databaseWetherFiveDay.setNightTemperature(dailyForecast
                    .getTemperature().getMinimum().getValue());
            Log.d(MainActivity.LOG_TAG,"nightTemperature = "
                    +dailyForecast.getTemperature().getMinimum().getValue());

            databaseWetherFiveDay.setDayDiscribe(dailyForecast.getDay().getShortPhrase()
                    //delete errror string
                    .replace("вряди-годи",""));
            Log.d(MainActivity.LOG_TAG,"dayDiscribe = "
                    +dailyForecast.getDay().getShortPhrase());

            databaseWetherFiveDay.setNightDiscribe(dailyForecast.getNight().getShortPhrase()
                    .replace("вряди-годи",""));
            Log.d(MainActivity.LOG_TAG,"nightDiscribe = "
                    +dailyForecast.getNight().getShortPhrase());

            databaseWetherFiveDay.setDayIdIcon(dailyForecast.getDay().getIcon());
            Log.d(MainActivity.LOG_TAG,"dayIdIcon = "
                    +dailyForecast.getDay().getIcon());

            databaseWetherFiveDay.setNightIdIcon(dailyForecast.getNight().getIcon());
            Log.d(MainActivity.LOG_TAG,"nightIdIcon = "
                    +dailyForecast.getNight().getIcon());

            databaseWetherFiveDay.setDayPrecipation(dailyForecast
                    .getDay().getPrecipitationProbability());
            Log.d(MainActivity.LOG_TAG,"dayPrecipation = "
                    +dailyForecast.getDay().getPrecipitationProbability());

            databaseWetherFiveDay.setNightPrecipation(dailyForecast
                    .getNight().getPrecipitationProbability());
            Log.d(MainActivity.LOG_TAG,"nightPrecipation = "
                    +dailyForecast.getNight().getPrecipitationProbability());

            databaseWetherFiveDay.setDaySpeedWind(dailyForecast
                    .getDay().getWind().getSpeed().getValue().intValue());
            Log.d(MainActivity.LOG_TAG,"daySpeedWind = "
                    +dailyForecast.getDay().getWind()
                    .getSpeed().getValue().intValue());

            databaseWetherFiveDay.setNightSpeedWind(dailyForecast
                    .getNight().getWind().getSpeed().getValue().intValue());
            Log.d(MainActivity.LOG_TAG,"nightSpeedWind = "
                    +dailyForecast.getNight().getWind()
                    .getSpeed().getValue().intValue());

            //add object 1-5 day weather
            databaseWetherFiveDaysList.add(databaseWetherFiveDay);
            i++;
        }

        /*
        Save or Update into database */
        mRealm.beginTransaction();
        mRealm.insertOrUpdate(databaseWetherFiveDaysList);
        Log.d(MainActivity.LOG_TAG,"SAve or update Weather 5 Day");
        Log.d(MainActivity.LOG_TAG,"Realm path = "+mRealm.getPath());
        mRealm.commitTransaction();
    }

    public void saveTwelveHoursInDatabaseRealm(List<Hourly_12HourModel> hourly_12HourModel) throws ParseException {
        int i = 0;
        List<DatabaseWetherTwelveHour> databaseWetherTwelveHourList  = new ArrayList<>();
        if (!mRealm.where(DatabaseWetherTwelveHour.class).isValid()){
            //Create data Model class for Realm
            mRealm.createObject(DatabaseWetherTwelveHour.class);
            Log.d(MainActivity.LOG_TAG, "Create Table for date about " +
                    "TwelveHoursInDatabaseRealm: ");
        }
        for (Hourly_12HourModel hourlyForecast : hourly_12HourModel) {
            DatabaseWetherTwelveHour databaseWetherTwelveHour =
                    new DatabaseWetherTwelveHour();
            Log.d(MainActivity.LOG_TAG, " --- Hour "+i+"-----");
            databaseWetherTwelveHour.setId(i);


            Log.d(MainActivity.LOG_TAG, "Time ="+hourlyForecast.getDateTime());
            databaseWetherTwelveHour.setTime(
                    new ControlMeasurements()
                            .parseTimeHourMinutesToString(hourlyForecast.getDateTime()));

            Log.d(MainActivity.LOG_TAG, "Temperature ="+hourlyForecast
                    .getTemperature().getValue());
            databaseWetherTwelveHour.setTemperature(hourlyForecast
                    .getTemperature().getValue());

            Log.d(MainActivity.LOG_TAG, "RealFeel temperature ="+hourlyForecast
                    .getRealFeelTemperature().getValue());
            databaseWetherTwelveHour.setRealFeelTemperature(hourlyForecast
                    .getRealFeelTemperature().getValue());


            Log.d(MainActivity.LOG_TAG, "Icon ID ="+hourlyForecast.getWeatherIcon());
            databaseWetherTwelveHour.setIdIcon(hourlyForecast.getWeatherIcon());

            Log.d(MainActivity.LOG_TAG, "Precipitation ="+hourlyForecast
                    .getPrecipitationProbability());
            databaseWetherTwelveHour.setPrecipitation(hourlyForecast
            .getPrecipitationProbability());

            Log.d(MainActivity.LOG_TAG, "Wind Speed ="+hourlyForecast
                    .getWind().getSpeed().getValue());
            databaseWetherTwelveHour.setSpeedWind(hourlyForecast
                    .getWind().getSpeed().getValue());

            Log.d(MainActivity.LOG_TAG, "Wind Direction ="+hourlyForecast
                    .getWind().getDirection().getDegrees());
            databaseWetherTwelveHour.setDirectionWind(hourlyForecast
            .getWind().getDirection().getDegrees());

            databaseWetherTwelveHourList.add(databaseWetherTwelveHour);
            i++;
        }
        /*
        Save or Update into database */
        mRealm.beginTransaction();
        mRealm.insertOrUpdate(databaseWetherTwelveHourList);
        Log.d(MainActivity.LOG_TAG,"SAve or update Weather 12 Hours");
        Log.d(MainActivity.LOG_TAG,"Realm path = "+mRealm.getPath());
        mRealm.commitTransaction();
    }

    public void createWetherListForTTS(){
                if (mRealm.where(DatabaseWetherTwelveHour.class).isValid() && mRealm.where(DatabaseWetherFiveDay.class).isValid()){
                    mDatabaseWetherFiveDayList = mRealm.where(DatabaseWetherFiveDay.class).findAll();
                    mDatabaseWetherTwelveHourList = mRealm.where(DatabaseWetherTwelveHour.class).findAll();
                    mStatusTTS = true;
                }else{
                    mStatusTTS = false;
                }
    }



    public StringBuilder speechAboutHoursWeather(int i){
        Context context = getApplicationContext();
        StringBuilder speech = new StringBuilder();
        speech.append(mDatabaseWetherTwelveHourList.get(i).getTime().substring(0,2))
                .append(" ")
                .append(context.getResources().getString(R.string.string_tts_hour))
                .append(",")
                .append(context.getResources().getString(R.string.string_tts_temperature))
                .append(" ")
                .append(mDatabaseWetherTwelveHourList.get(i).getTemperature())
                .append(context.getResources().getString(R.string.string_celsius))
                .append(",")
                .append(context.getResources().getString(R.string.string_tts_speed_wind))
                .append(" ")
                .append(Math.floor(mDatabaseWetherTwelveHourList.get(i).getSpeedWind()))
                .append(",")
                .append(context.getResources().getString(R.string.string_tts_km_h));
        if (speech.length() == 0){
            speech.append(getApplicationContext().getResources().getString(R.string.string_tts_no_data));
        }
        return speech;
    }

    public StringBuilder speechAboutDaysWether(int i){
        Context context = getApplicationContext();
        StringBuilder speech = new StringBuilder();
        if(i==0)
            speech.append(context.getResources().getString(R.string.string_tts_today));

        if(i==1)
            speech.append(context.getResources().getString(R.string.string_tts_tomorrow));

        speech.append(" ")
                .append(mDatabaseWetherFiveDayList.get(i).getData())
                .append(",")
                .append(context.getResources().getString(R.string.string_tts_on_day))
                .append(" ")
                .append(mDatabaseWetherFiveDayList.get(i).getDayTemperature())
                .append(context.getResources().getString(R.string.string_celsius))
                .append(" ")
                .append(mDatabaseWetherFiveDayList.get(i).getDayDiscribe())
                .append(",")
                .append(context.getResources().getString(R.string.string_tts_in_the_night))
                .append(" ")
                .append(mDatabaseWetherFiveDayList.get(i).getNightTemperature())
                .append(" ")
                .append(context.getResources().getString(R.string.string_celsius))
                .append(",")
                .append(mDatabaseWetherFiveDayList.get(i).getNightDiscribe());
        return speech;
    }

    public void viewToastSpecial_Hour(DatabaseWetherTwelveHour twelveHour,Toast toast){
        //final Toast toast = new Toast(getApplicationContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_special_wether_12hours,
                (ViewGroup) findViewById(R.id.special_toast_conteiner_12hours));
        TextView time = (TextView) view.findViewById(R.id.text_special_dialog_time_12hours);
        TextView temperature = (TextView) view.findViewById(R.id.text_special_dialog_temperature_12hours);
        TextView speedWind = (TextView) view.findViewById(R.id.text_special_dialog_speed_wind_12hours);

        time.setText(twelveHour.getTime());
        temperature.setText(String.valueOf(twelveHour.getTemperature()));
        speedWind.setText(String.valueOf(twelveHour.getSpeedWind()));

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
       /* Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 10000);*/
    }

    public void viewToastSpecial_Day(DatabaseWetherFiveDay fiveDay,Toast toast){
        //final Toast toast = new Toast(getApplicationContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_special_wether_5days,
                (ViewGroup) findViewById(R.id.special_toast_conteiner_5days));

        TextView date = (TextView) view.findViewById(R.id.dialog_special_date_5day);
        TextView dayTemperatura = (TextView) view.findViewById(R.id.dialog_special_temperature_day_5day);
        TextView nightTemperatura = (TextView) view.findViewById(R.id.dialog_special_temperature_night_5day);
        TextView dayPrecipation = (TextView) view.findViewById(R.id.dialog_special_precipation_day_5day);
        TextView nightPrecipation = (TextView) view.findViewById(R.id.dialog_special_precipation_night_5day);
        TextView dayDiscribe = (TextView) view.findViewById(R.id.dialog_special_discribe_day_5day);
        TextView nightDiscribe = (TextView) view.findViewById(R.id.dialog_special_discribe_night_5day);


        String dateIn = fiveDay.getData();
        date.setText(dateIn.substring(0,3)+","+ dateIn.split(",")[1]);
        dayTemperatura.setText(String.valueOf(fiveDay.getDayTemperature()));
        nightTemperatura.setText(String.valueOf(fiveDay.getNightTemperature()));
        dayPrecipation.setText(String.valueOf(fiveDay.getDayPrecipation()));
        nightPrecipation.setText(String.valueOf(fiveDay.getDayPrecipation()));
        dayDiscribe.setText(fiveDay.getDayDiscribe());
        nightDiscribe.setText(fiveDay.getNightDiscribe());

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    private void saveCityKeySharedPreferences(Intent intent){
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.SAVE_CITY_KEY,Context.MODE_PRIVATE);
        if (!sharedPreferences.contains(Constant.SAVE_CITY_KEY) && mKeyCity != null){
            String city_key = intent.getStringExtra(Constant.EXTRA_KEY_LOCATION_CITY);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constant.SAVE_CITY_KEY, mKeyCity);
            editor.apply();
        }
    }

    private void initToolbar(){
                //init and view city name in Toolbar Title with SharePreferences
                SharedPreferences sharedPreferences = getSharedPreferences(Constant.SAVE_NAME_CITY, Context.MODE_PRIVATE);
                String city_name = sharedPreferences.getString(Constant.SAVE_NAME_CITY, "");
                mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
                mToolbar.dismissPopupMenus();
                if(city_name!=null && !city_name.equals("")){
                    setTitle(city_name);
                }else{
                    if(mNameCity!=null){
                        setTitle(mNameCity);
                    }
                }
                setSupportActionBar(mToolbar);

    }

}


