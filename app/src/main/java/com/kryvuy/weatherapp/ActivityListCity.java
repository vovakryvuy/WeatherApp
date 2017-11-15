package com.kryvuy.weatherapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

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

public class ActivityListCity extends AppCompatActivity {
    public static String EXTRA_STRING_FOR_SEARCH_CITY = "ActivityListCity.EXTRA_STRING_FOR_SEARCH_CITY";
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_city);
        Intent intent = getIntent();
        String quareText = intent.getStringExtra(EXTRA_STRING_FOR_SEARCH_CITY);

        ((TextView) findViewById(R.id.quare_text_for_search_city)).setText(quareText);


        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view_list_city);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new AdapterRecycle(null,getApplicationContext()));


        Service_Retrofit.getService().getListCities(Constant.API_KEY,quareText,Constant.LANGUAGE).enqueue(new Callback<List<SearchCityByName>>() {
            @Override
            public void onResponse(Call<List<SearchCityByName>> call, Response<List<SearchCityByName>> response) {
                if(response.isSuccessful()) {
                    mRecyclerView.setAdapter(new AdapterRecycle(response, ActivityListCity.this));
                    for (SearchCityByName cityByName : response.body()) {
                        Log.d(MainActivity.LOG_TAG,"city  = "+cityByName.getLocalizedName());
                    }
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
}
