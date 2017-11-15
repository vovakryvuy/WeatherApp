package com.kryvuy.weatherapp.adapter_for_recycle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kryvuy.weatherapp.Activity_OneDayWeather;
import com.kryvuy.weatherapp.MainActivity;
import com.kryvuy.weatherapp.MainWeatherActivity;
import com.kryvuy.weatherapp.R;
import com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.search_city.SearchCityByName;
import com.kryvuy.weatherapp.start.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by Володимир on 4/29/2017.
 */

public class AdapterRecycle extends RecyclerView.Adapter<AdapterRecycle.ViewHolder> {
    private List<String> mListCityNames;
    private List<String> mListCountry;
    private List<String> mKeyCity;
    private Context mContext;

    public AdapterRecycle(Response<List<SearchCityByName>> cityByName,Context context){
        mContext = context;

        if(cityByName != null ){
            mKeyCity = new ArrayList<>();
            mListCityNames = new ArrayList<>();
            mListCountry = new ArrayList<>();
            for (SearchCityByName cityByName_ : cityByName.body()) {
                mListCityNames.add(cityByName_.getLocalizedName());
                mListCountry.add(cityByName_.getCountry().getLocalizedName());
                mKeyCity.add(cityByName_.getKey());
            }
        }else {
            mListCityNames = new ArrayList<>();
            mListCityNames.add("Немає");
            mListCountry = new ArrayList<>();
            mListCountry.add("Немає");
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_view_city_search,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        /*infotmation in to */
        final int posit = position;
        holder.mTextView_City.setText(mListCityNames.get(position));
        holder.mTextView_Country.setText(mListCountry.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(MainActivity.LOG_TAG, "Натиснуто = "+posit+"місто = "+mListCityNames.get(posit));

                if(mKeyCity!=null){
                    SharedPreferences sharedPreferences = mContext.getSharedPreferences(Constant.SAVE_NAME_CITY
                            ,Context.MODE_PRIVATE);
                    SharedPreferences sharedPreferencesKey = mContext.getSharedPreferences(Constant.SAVE_CITY_KEY
                            ,Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Constant.SAVE_NAME_CITY, mListCityNames.get(posit));
                        editor.apply();

                        SharedPreferences.Editor editor1 = sharedPreferencesKey.edit();
                        editor1.putString(Constant.SAVE_CITY_KEY, mKeyCity.get(posit));
                        editor1.apply();

                    if(mContext instanceof MainActivity){
                        Intent intent = new Intent(mContext,MainWeatherActivity.class);
                        intent.putExtra(Constant.EXTRA_KEY_LOCATION_CITY,mKeyCity.get(posit));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }else {
                        Activity activity = (Activity) mContext;
                        activity.setResult(Activity.RESULT_OK);
                        activity.onBackPressed();
                    }
                }
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView_City,mTextView_Country;
        ViewHolder(View v) {
            super(v);
            mTextView_City = (TextView) v.findViewById(R.id.city_name);
            mTextView_Country = (TextView) v.findViewById(R.id.country);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mListCityNames.size();
    }

    public void saveNameCitySharePreferences(){

    }

}
