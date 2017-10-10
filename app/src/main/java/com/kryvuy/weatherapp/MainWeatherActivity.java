package com.kryvuy.weatherapp;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.kryvuy.weatherapp.adapter_for_recycle.AdapterRecycle;
import com.kryvuy.weatherapp.adapter_for_recycle.AdapterRecycle_5Days;
import com.kryvuy.weatherapp.api.Service_Retrofit;
import com.kryvuy.weatherapp.dialog.DialogSearchCity;
import com.kryvuy.weatherapp.dialog.Dialog_No_Network;
import com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.daily_1day.Daily_OneDay;
import com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.daily_5days.Daily_FiveDay;
import com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.search_city.SearchCityByName;
import com.kryvuy.weatherapp.start.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Володимир on 9/4/2017.
 */

public class MainWeatherActivity extends AppCompatActivity
        implements MenuItemCompat.OnActionExpandListener,SearchView.OnQueryTextListener{
    public static final String EXTRA_KEY_CITY = "com.kryvuy.weatherapp.MainWeatherActivity.EXTRA_KEY_CITY";
    private RecyclerView mRecyclerView;
    private MenuItem searchMenuItem;
    private SearchView searchView;
    private DialogSearchCity mDialogFragment = new DialogSearchCity();

    private List<String> mListCity_CountryName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wether_main_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_5days);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(MainWeatherActivity.this,LinearLayoutManager.HORIZONTAL,false));


        Service_Retrofit.getService().getDaily_5Day(intent.getStringExtra(EXTRA_KEY_CITY),
                Constant.API_KEY,Constant.LANGUAGE,true,true)
                .enqueue(new Callback<Daily_FiveDay>() {
                    @Override
                    public void onResponse(Call<Daily_FiveDay> call, Response<Daily_FiveDay> response) {
                        mRecyclerView.setAdapter(new AdapterRecycle_5Days(response.body(),getApplicationContext()));
                    }

                    @Override
                    public void onFailure(Call<Daily_FiveDay> call, Throwable t) {

                    }
                });

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
    private void createListForCityContry(){
        if(!mListCity_CountryName.isEmpty()){
            Log.d(MainActivity.LOG_TAG,"start dialog serch city and country");
            mDialogFragment.setListCityCountry(mListCity_CountryName);
            mDialogFragment.show(getSupportFragmentManager(),"dialog_fragment_serch_city");
        }
    }
}
