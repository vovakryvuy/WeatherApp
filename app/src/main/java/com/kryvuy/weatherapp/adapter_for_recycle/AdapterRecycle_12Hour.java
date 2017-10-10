package com.kryvuy.weatherapp.adapter_for_recycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.kryvuy.weatherapp.control_mesurements.ControlMeasurements;
import com.kryvuy.weatherapp.R;
import com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.hourly_12hour_model.Hourly_12HourModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Володимир on 5/7/2017.
 */
public class AdapterRecycle_12Hour extends RecyclerView.Adapter<AdapterRecycle_12Hour.ViewHolder> {
    private Context mContext;
    private List<String> list_time;
    private List<Double> mTemperature_Double = new ArrayList<>();
    private List<String> mRealFeelTemperature = new ArrayList<>();
    private List<Double> mWindSpeed;
    private List<Integer> mDirectionWind;
    private List<Integer> mNumberIcon;
    private List<Integer> mPrecipitation;
    private ControlMeasurements mControlMeasurements = new ControlMeasurements();

    public AdapterRecycle_12Hour(List<Hourly_12HourModel> list, Context context) {
        this.mContext = context;
        this.list_time = mControlMeasurements.parseTime(list);
        this.mTemperature_Double = mControlMeasurements.parseTemperature_Double(list);
        this.mRealFeelTemperature  = mControlMeasurements.parseTemperatureRealFeel(list);
        this.mNumberIcon = mControlMeasurements.parseNumberIconWeather(list);
        this.mWindSpeed = mControlMeasurements.parseWindSpeed(list);
        this.mDirectionWind = mControlMeasurements.parseDirectionWind(list);
        this.mPrecipitation = mControlMeasurements.parsePrecipitation(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.item_list_12_hour,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTextTime.setText(list_time.get(position));
        holder.mTextTemperature.setText(String.valueOf(mTemperature_Double.get(position)));
        holder.mTextRealFeelTemperature.setText(mRealFeelTemperature.get(position));
        holder.mImageTermometer.setColorFilter(
                mControlMeasurements.getColorChengeTermometer(mTemperature_Double.get(position),mContext));
        holder.mWeatherIcon.setImageDrawable(
                mContext.getDrawable(mControlMeasurements.getDrawableWeatherIcon(mNumberIcon.get(position))));

        holder.mImageDrop.setImageDrawable(
                mContext.getDrawable(mControlMeasurements.getDrawableDropIcon(mPrecipitation.get(position))));

        holder.mTextWindSpeed.setText(String.valueOf(mWindSpeed.get(position)));
        holder.mTextPrecipitation.setText(String.valueOf(mPrecipitation.get(position)));
        animationRotationWindDirection(holder.mImageDiractionWind,position);
        holder.mImageDiractionWind.setRotation(mDirectionWind.get(position));
    }

    @Override
    public void onViewDetachedFromWindow(final ViewHolder holder) {
        holder.clearAnimation();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTextTime,mTextTemperature,mTextRealFeelTemperature,mTextWindSpeed,mTextPrecipitation;
        ImageView mImageTermometer,mImageDiractionWind,mWeatherIcon,mImageDrop;
        public ViewHolder(View v) {
            super(v);
            mTextTime = (TextView) v.findViewById(R.id.text_time);
            mTextTemperature = (TextView)v.findViewById(R.id.text_temperature);
            mTextRealFeelTemperature = (TextView)v.findViewById(R.id.text_real_feel_temperature);
            mTextWindSpeed = (TextView) v.findViewById(R.id.text_wind_speed_12);
            mWeatherIcon = (ImageView) v.findViewById(R.id.image_weather_icon);
            mTextPrecipitation = (TextView) v.findViewById(R.id.text_precipitation);
            mImageDrop = (ImageView) v.findViewById(R.id.drop_icon);
            mImageTermometer = (ImageView)v.findViewById(R.id.image_termometer_12);
            mImageDiractionWind = (ImageView)v.findViewById(R.id.image_diraction_wind_12);
        }

        public void clearAnimation() {
           // mImageMillWindRotation.clearAnimation();
        }
    }

    @Override
    public int getItemCount() {
        return list_time.size();
    }


    private void animationRotationWindDirection(View view,int position){
        RotateAnimation rotate = new RotateAnimation(0,mDirectionWind.get(position),
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setFillEnabled(true);
        rotate.setFillAfter(true);
        rotate.setInterpolator(new AccelerateDecelerateInterpolator());
        view.startAnimation(rotate);
    }
}
