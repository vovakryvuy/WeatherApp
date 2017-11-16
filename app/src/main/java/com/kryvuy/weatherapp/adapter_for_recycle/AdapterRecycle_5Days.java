package com.kryvuy.weatherapp.adapter_for_recycle;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kryvuy.weatherapp.Activity_OneDayWeather;
import com.kryvuy.weatherapp.MainActivity;
import com.kryvuy.weatherapp.R;
import com.kryvuy.weatherapp.control_mesurements.ControlMeasurements;
import com.kryvuy.weatherapp.data_base.DatabaseWetherFiveDay;
import com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.daily_5days.DailyForecast;
import com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.daily_5days.Daily_FiveDay;
import com.kryvuy.weatherapp.start.Constant;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Response;

/**
 * Created by Володимир on 4/29/2017.
 */
public class AdapterRecycle_5Days extends RecyclerView.Adapter<AdapterRecycle_5Days.ViewHolder> {
    private List<Double> mDayTemperature = new ArrayList<>();
    private List<Double> mNightTemperature = new ArrayList<>();
    private List<String> mDayAndMonth = new ArrayList<>();
    private List<Integer> mIdIconDay = new ArrayList<>();
    private List<Integer> mIdIconNight = new ArrayList<>();
    private List<String> mDescribeDay = new ArrayList<>();
    private List<String> mDescribeNight = new ArrayList<>();
    private List<Integer> mSpeedWindDay = new ArrayList<>();
    private List<Integer> mSpeedWindNight = new ArrayList<>();
    private List<Integer> mPrecipationDay = new ArrayList<>();
    private List<Integer> mPrecipationNight = new ArrayList<>();
    private String celsium;
    private String km_h;
    private List<DailyForecast> mDailyForecasts;
    private Context mContext;
    private Realm mRealm;
    private ControlMeasurements mMeasurements = new ControlMeasurements();
    public AdapterRecycle_5Days(Daily_FiveDay weatherFiveDay, Context context){
        this.mContext = context;
        this.celsium = context.getResources().getString(R.string.string_celsius);
        this.km_h = context.getResources().getString(R.string.metric_km_h_string);
       /*
        TEST*/
        if (weatherFiveDay!=null){
            this.mDailyForecasts = weatherFiveDay.getDailyForecasts();
            this.mDayTemperature = getDayTemperature(mDailyForecasts);
            this.mNightTemperature = getNightTemperature(mDailyForecasts);
            this.mDayAndMonth = getDayAndMonth(mDailyForecasts);
            this.mIdIconDay = getIdIconDay(mDailyForecasts);
            this.mIdIconNight = getIdIconNight(mDailyForecasts);
            this.mDescribeDay = getDescribeDay(mDailyForecasts);
            this.mDescribeNight = getDescribeNight(mDailyForecasts);
            this.mSpeedWindDay = getSpeedWindDay(mDailyForecasts);
            this.mSpeedWindNight = getSpeedWindNight(mDailyForecasts);
            this.mPrecipationDay = getPrecipationDay(mDailyForecasts);
            this.mPrecipationNight = getPrecipationNight(mDailyForecasts);
        }else{
            mRealm = Realm.getDefaultInstance();
            if (mRealm.where(DatabaseWetherFiveDay.class).isValid()){
                List<DatabaseWetherFiveDay> wetherFiveDays =
                        mRealm.where(DatabaseWetherFiveDay.class).findAll();
                Log.d(MainActivity.LOG_TAG, "AdapterRecycle_5Days: GET realm data");
                for (DatabaseWetherFiveDay fiveDay : wetherFiveDays) {
                    this.mDayTemperature.add(fiveDay.getDayTemperature());
                    this.mNightTemperature.add(fiveDay.getNightTemperature());
                    this.mDayAndMonth.add(fiveDay.getData());
                    this.mIdIconDay.add(fiveDay.getDayIdIcon());
                    this.mIdIconNight.add(fiveDay.getNightIdIcon());
                    this.mDescribeDay.add(fiveDay.getDayDiscribe());
                    this.mDescribeNight.add(fiveDay.getNightDiscribe());
                    this.mSpeedWindDay.add(fiveDay.getDaySpeedWind());
                    this.mSpeedWindNight.add(fiveDay.getNightSpeedWind());
                    this.mPrecipationDay.add(fiveDay.getDayPrecipation());
                    this.mPrecipationNight.add(fiveDay.getNightPrecipation());
                }
            }else {
                this.mDayTemperature.add(0.0);
                this.mNightTemperature.add(0.0);
                this.mDayAndMonth.add("---");
                this.mIdIconDay.add(0);
                this.mIdIconNight.add(0);
                this.mDescribeDay.add("---");
                this.mDescribeNight.add("---");
                this.mSpeedWindDay.add(0);
                this.mSpeedWindNight.add(0);
                this.mPrecipationDay.add(0);
                this.mPrecipationNight.add(0);

            }
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_5_day,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        /*infotmation in to */
        final int posit = position;

        holder.mTextTempMax.setText(mDayTemperature.get(position)+celsium);
        holder.mTextTempMin.setText(mNightTemperature.get(position)+celsium);
        holder.mTextDate.setText(mDayAndMonth.get(position));
        holder.mImageIconDay.setImageDrawable(mContext
                .getDrawable(mMeasurements.getDrawableWeatherIcon(mIdIconDay.get(position))));
        holder.mImageIconNight.setImageDrawable(mContext
                .getDrawable(mMeasurements.getDrawableWeatherIcon(mIdIconNight.get(position))));
        holder.mTextDescribeDay.setText(mDescribeDay.get(position));
        holder.mTextDescribeDay.setTextAppearance(mContext,R.style.TextWithWightShadow);
        holder.mTextDescribeNight.setText(mDescribeNight.get(position));
        holder.mTextSpeedWindDay.setText(mSpeedWindDay.get(position)+km_h);
        holder.mTextSpeedWindNight.setText(mSpeedWindNight.get(position)+km_h);
        holder.mTextPrecipationDay.setText(String.valueOf(mPrecipationDay.get(position)+"%"));
        holder.mTextPrecipationNight.setText(String.valueOf(mPrecipationNight.get(position)+"%"));
        if(mMeasurements.getDrawableUmbrella(mPrecipationDay.get(position))){
            holder.mImageUmbrellaDay.setImageDrawable(mContext.getDrawable(R.drawable.ic_umbrellas_24dp));
        }
        if(mMeasurements.getDrawableUmbrella(mPrecipationNight.get(position))){
            holder.mImageUmbrellaNight.setImageDrawable(mContext.getDrawable(R.drawable.ic_umbrellas_24dp));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(MainActivity.LOG_TAG, "Натиснуто = "+posit);

            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTextTempMax,mTextTempMin,mTextDate,mTextDescribeDay,mTextDescribeNight,
                mTextSpeedWindDay,mTextSpeedWindNight,mTextPrecipationDay,mTextPrecipationNight;
        ImageView mImageIconDay,mImageIconNight,mImageUmbrellaDay,mImageUmbrellaNight;
        ViewHolder(View v) {
            super(v);
            mTextTempMax = (TextView) v.findViewById(R.id.temperature_max_5day);
            mTextTempMin = (TextView) v.findViewById(R.id.temperature_min_5day);
            mTextDate = (TextView) v.findViewById(R.id.text_data_list_5day);
            mImageIconDay = (ImageView) v.findViewById(R.id.image_list_5day_icon_day);
            mImageIconNight = (ImageView) v.findViewById(R.id.image_list_5day_icon_night);
            mTextDescribeDay = (TextView) v.findViewById(R.id.text_describe_day_list5day);
            mTextDescribeNight = (TextView) v.findViewById(R.id.text_describe_night_list5day);
            mTextSpeedWindDay = (TextView) v.findViewById(R.id.text_seed_wind_day_list5day);
            mTextSpeedWindNight = (TextView) v.findViewById(R.id.text_seed_wind_night_list5day);
            mTextPrecipationDay = (TextView) v.findViewById(R.id.text_precipation_day_list5day);
            mTextPrecipationNight = (TextView) v.findViewById(R.id.text_precipation_night_list5day);
            mImageUmbrellaDay = (ImageView) v.findViewById(R.id.image__umbrella_list_5day_icon_day);
            mImageUmbrellaNight = (ImageView) v.findViewById(R.id.image__umbrella_list_5day_icon_night);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mDayTemperature.size();
    }

    private List<Integer> getPrecipationDay(List<DailyForecast> dailyForecasts){
        List<Integer> list = new ArrayList<>();
        for (DailyForecast dailyForecast : dailyForecasts) {
            list.add(dailyForecast.getDay().getPrecipitationProbability());
        }
        return list;
    }
    private List<Integer> getPrecipationNight(List<DailyForecast> dailyForecasts){
        List<Integer> list = new ArrayList<>();
        for (DailyForecast dailyForecast : dailyForecasts) {
            list.add(dailyForecast.getNight().getPrecipitationProbability());
        }
        return list;
    }
    private List<Integer> getSpeedWindDay(List<DailyForecast> dailyForecasts){
        List<Integer> list = new ArrayList<>();
        for (DailyForecast dailyForecast : dailyForecasts) {
            list.add(dailyForecast.getDay().getWind().getSpeed().getValue().intValue());
        }
        return list;
    }
    private List<Integer> getSpeedWindNight(List<DailyForecast> dailyForecasts){
        List<Integer> list = new ArrayList<>();
        for (DailyForecast dailyForecast : dailyForecasts) {
            list.add(dailyForecast.getNight().getWind().getSpeed().getValue().intValue());
        }
        return list;
    }
    private List<String> getDescribeDay(List<DailyForecast> dailyForecasts){
        List<String> list = new ArrayList<>();
        for (DailyForecast dailyForecast : dailyForecasts) {
            list.add(dailyForecast.getDay().getShortPhrase().replace("вряди-годи",""));
        }
        return list;
    }
    private List<String> getDescribeNight(List<DailyForecast> dailyForecasts){
        List<String> list = new ArrayList<>();
        for (DailyForecast dailyForecast : dailyForecasts) {
            list.add(dailyForecast.getNight().getShortPhrase().replace("вряди-годи",""));
        }
        return list;
    }
    private List<Integer> getIdIconDay(List<DailyForecast> dailyForecasts){
        List<Integer> list = new ArrayList<>();
        for (DailyForecast dailyForecast : dailyForecasts) {
            list.add(dailyForecast.getDay().getIcon());
        }
        return list;
    }
    private List<Integer> getIdIconNight(List<DailyForecast> dailyForecasts){
        List<Integer> list = new ArrayList<>();
        for (DailyForecast dailyForecast : dailyForecasts) {
            list.add(dailyForecast.getNight().getIcon());
        }
        return list;
    }
    private List<String> getDayAndMonth(List<DailyForecast> dailyForecasts) {
        List<String> list = new ArrayList<>();
        for (DailyForecast dailyForecast : dailyForecasts) {
            list.add(mMeasurements.parseDateToString_MonthAndDay(dailyForecast.getDate()));
        }
        return list;
    }
    private List<Double> getDayTemperature(List<DailyForecast> daily_fiveDay){
        List<Double> list = new ArrayList<>();
        for (DailyForecast dailyForecast : daily_fiveDay) {
            list.add(dailyForecast.getTemperature().getMaximum().getValue());
        }
        return list;
    }
    private List<Double> getNightTemperature(List<DailyForecast> daily_fiveDay){
        List<Double> list = new ArrayList<>();
        for (DailyForecast dailyForecast : daily_fiveDay) {
            list.add(dailyForecast.getTemperature().getMinimum().getValue());
        }
        return list;
    }

}
