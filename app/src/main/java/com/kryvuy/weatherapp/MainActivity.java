package com.kryvuy.weatherapp;

import android.app.FragmentTransaction;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.MenuItemCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.kryvuy.weatherapp.adapter_for_recycle.AdapterRecycle;
import com.kryvuy.weatherapp.api.Service_Retrofit;
import com.kryvuy.weatherapp.dialog.Dialog_No_Network;
import com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.search_city.SearchCityByName;
import com.kryvuy.weatherapp.start.Constant;

import java.net.UnknownHostException;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MenuItemCompat.OnActionExpandListener,SearchView.OnQueryTextListener {
    public static final String LOG_TAG = "LOG_WEATHER_APP_TAG";
    private RecyclerView mRecyclerView;
    private String searchText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /*
       create Toolbar*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new AdapterRecycle(null,getApplicationContext()));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_toolbar, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
            SearchView mSearchView = (SearchView) searchMenuItem.getActionView();
            mSearchView.setOnQueryTextListener(this);
            MenuItemCompat.setOnActionExpandListener(searchMenuItem, this);
            MenuItemCompat.setActionView(searchMenuItem,mSearchView);
            searchMenuItem.setVisible(true);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        searchText = query;
        if(!searchText.equals("")){
            Service_Retrofit.getService().getListCities(Constant.API_KEY,searchText,Constant.LANGUAGE).enqueue(new Callback<List<SearchCityByName>>() {
                @Override
                public void onResponse(Call<List<SearchCityByName>> call, Response<List<SearchCityByName>> response) {
                    if(response.isSuccessful()) {
                        mRecyclerView.setAdapter(new AdapterRecycle(response, getApplicationContext()));
                        Log.d(MainActivity.LOG_TAG,"Shearch city  = "+response.body().iterator());
                    }
                }
                @Override
                public void onFailure(Call<List<SearchCityByName>> call, Throwable t) {
                    if(t.fillInStackTrace() instanceof UnknownHostException){
                        DialogFragment dialogFragment = new Dialog_No_Network();
                        Log.d(MainActivity.LOG_TAG,"start dialog not internet conection");
                        dialogFragment.show(getSupportFragmentManager(),"dialog_fragment_no_internet");
                    }
                }
            });
        }
        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(LOG_TAG,"New text = "+searchText);
        return false;
    }
}
