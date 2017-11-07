package com.kryvuy.weatherapp;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import com.kryvuy.weatherapp.adapter_for_recycle.AdapterRecycle_12Hour;
import com.kryvuy.weatherapp.adapter_for_recycle.AdapterRecycle_5Days;
import com.kryvuy.weatherapp.api.Service_Retrofit;
import com.kryvuy.weatherapp.control_mesurements.ControlMeasurements;
import com.kryvuy.weatherapp.data_base.DatabaseWetherFiveDay;
import com.kryvuy.weatherapp.data_base.DatabaseWetherTwelveHour;
import com.kryvuy.weatherapp.dialog.DialogSearchCity;
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
        implements MenuItemCompat.OnActionExpandListener,SearchView.OnQueryTextListener{
    public static final String EXTRA_KEY_CITY = "com.kryvuy.weatherapp.MainWeatherActivity.EXTRA_KEY_CITY";
    private RecyclerView mRecyclerViewHours;
    private RecyclerView mRecyclerViewDays;
    private String mKeyCity;
    private MenuItem searchMenuItem;
    private SearchView searchView;
    private DialogSearchCity mDialogFragment = new DialogSearchCity();
    private List<String> mListCity_CountryName = new ArrayList<>();
    private List<DatabaseWetherFiveDay> mDatabaseWetherFiveDayList;
    private List<DatabaseWetherTwelveHour> mDatabaseWetherTwelveHourList;
    private Boolean mStatusTTS = false;
    private int mCount_wetherDays = 0;
    private int mCount_wetherHours = 0;
    private Realm mRealm;
    private TextToSpeech mTextToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wether_main_layout);
        Log.d(MainActivity.LOG_TAG,"-----------START------------");

        /*
        inizializi RealmDataBase*/
            mRealm = Realm.getDefaultInstance();

        /*
        create weather list for TTS*/
            createWetherListForTTS();
        /*
        inizializi TextToSpeech*/
        mTextToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR ){
                    mTextToSpeech.setLanguage(Locale.getDefault());
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        /*
        Get Preferenses key City */
        Intent intent = getIntent();
        mKeyCity = intent.getStringExtra(EXTRA_KEY_CITY);
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

        Service_Retrofit.getService().getDaily_5Day(mKeyCity,Constant.API_KEY,Constant.LANGUAGE,true,true)
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
                        mRecyclerViewDays.setAdapter(new AdapterRecycle_5Days(null,getApplicationContext()));
                    }
                });

        Service_Retrofit.getService().getHourly_12Hour(mKeyCity,Constant.API_KEY,Constant.LANGUAGE,true,true)
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

                    }

                    @Override
                    public void onFailure(Call<List<Hourly_12HourModel>> call, Throwable t) {
                        mRecyclerViewHours.setAdapter(new AdapterRecycle_12Hour(null,getApplicationContext()));
                    }
                });
        Log.d(MainActivity.LOG_TAG,"------------END-------------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
        mTextToSpeech.stop();
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
            mListCity_CountryName.clear();
            Service_Retrofit.getService().getListCities(Constant.API_KEY, query, Constant.LANGUAGE)
                    .enqueue(new Callback<List<SearchCityByName>>() {
                        @Override
                        public void onResponse(Call<List<SearchCityByName>> call, Response<List<SearchCityByName>> response) {
                           /*
                                put city name and country*/
                            getCityAndCounrty(response.body());
                            if (!mListCity_CountryName.isEmpty()) {
                                /*
                                create list with contry and city in dialogFragment*/
                                createListForCityContry();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<SearchCityByName>> call, Throwable t) {

                        }
                    });
        }

        return true;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(MainActivity.LOG_TAG, "MainWeatherActivity  onQueryTextChange: "+newText);
        return true;
    }
    private void getCityAndCounrty(List<SearchCityByName> listCity){
        for (SearchCityByName searchCityByName : listCity) {
            mListCity_CountryName.add(getString(R.string._text_city) +" "+ searchCityByName.getLocalizedName()+"\n"+
            getString(R.string._text_country) +" "+ searchCityByName.getCountry().getLocalizedName());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mStatusTTS){
            switch (keyCode){
                case KeyEvent.KEYCODE_VOLUME_UP:
                    Log.d(MainActivity.LOG_TAG, "PRESSS BUTTON UP start speech 5Days");
                        mTextToSpeech.stop();
                        mTextToSpeech.speak(speechAboutDaysWether(mCount_wetherDays % 5)
                                ,TextToSpeech.QUEUE_FLUSH,null,null);
                        mCount_wetherDays++;
                    break;
                case KeyEvent.KEYCODE_VOLUME_DOWN:
                    Log.d(MainActivity.LOG_TAG, "PRESSS BUTTON DOWN start speech 12Hours");
                        mTextToSpeech.stop();
                        mTextToSpeech.speak(speechAboutHoursWeather(mCount_wetherHours % 12)
                                , TextToSpeech.QUEUE_FLUSH, null, null);
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

            databaseWetherFiveDay.setDayDiscribe(dailyForecast.getDay().getShortPhrase());
            Log.d(MainActivity.LOG_TAG,"dayDiscribe = "
                    +dailyForecast.getDay().getShortPhrase());

            databaseWetherFiveDay.setNightDiscribe(dailyForecast.getNight().getShortPhrase());
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
                .append(" ")
                .append(context.getResources().getString(R.string.string_tts_temperature))
                .append(" ")
                .append(mDatabaseWetherTwelveHourList.get(i).getTemperature())
                .append(context.getResources().getString(R.string.string_celsius))
                .append(" ")
                .append(context.getResources().getString(R.string.string_tts_speed_wind))
                .append(" ")
                .append(Math.floor(mDatabaseWetherTwelveHourList.get(i).getSpeedWind()))
                .append(" ")
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

        speech.append(mDatabaseWetherFiveDayList.get(i).getData())
                .append(" ")
                .append(context.getResources().getString(R.string.string_tts_on_day))
                .append(mDatabaseWetherFiveDayList.get(i).getDayTemperature())
                .append(context.getResources().getString(R.string.string_celsius))
                .append(" ")
                .append(mDatabaseWetherFiveDayList.get(i).getDayDiscribe())
                .append(" ")
                .append(context.getResources().getString(R.string.string_tts_in_the_night))
                .append(mDatabaseWetherFiveDayList.get(i).getNightTemperature())
                .append(" ")
                .append(context.getResources().getString(R.string.string_celsius))
                .append(" ")
                .append(mDatabaseWetherFiveDayList.get(i).getNightDiscribe());
        return speech;
    }


}


