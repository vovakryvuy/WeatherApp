package com.kryvuy.weatherapp.control_mesurements;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.kryvuy.weatherapp.R;
import com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.hourly_12hour_model.Hourly_12HourModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Володимир on 8/29/2017.
 */

public class ControlMeasurements {
    public int getDrawableWeatherIcon(Integer n){
        int id;
        switch (n){
            case 1:
                id = R.mipmap.ic_weather_1_1;
                break;
            case 2:id = R.mipmap.ic_weather_2_1;
                break;
            case 3:id = R.mipmap.ic_weather_3_1;
                break;
            case 4:id = R.mipmap.ic_weather_4;
                break;
            case 5:id = R.mipmap.ic_weather_5;
                break;
            case 6:id = R.mipmap.ic_weather_6;
                break;
            case 7:id = R.mipmap.ic_weather_7;
                break;
            case 8:id = R.mipmap.ic_weather_8;
                break;
            case 11:id = R.mipmap.ic_weather_11;
                break;
            case 12:id = R.mipmap.ic_weather_12;
                break;
            case 13:id = R.mipmap.ic_weather_13;
                break;
            case 14:id = R.mipmap.ic_weather_14;
                break;
            case 15:id =R.mipmap.ic_weather_15;
                break;
            case 16:id =R.mipmap.ic_weather_16;
                break;
            case 17:id =R.mipmap.ic_weather_17;
                break;
            case 18:id =R.mipmap.ic_weather_18;
                break;
            case 19:id =R.mipmap.ic_weather_19;
                break;
            case 20:id =R.mipmap.ic_weather_20;
                break;
            case 21:id =R.mipmap.ic_weather_21;
                break;
            case 22:id =R.mipmap.ic_weather_22;
                break;
            case 23:id =R.mipmap.ic_weather_23;
                break;
            case 24:id =R.mipmap.ic_weather_24;
                break;
            case 25:id =R.mipmap.ic_weather_25;
                break;
            case 26:id =R.mipmap.ic_weather_26;
                break;
            case 29:id =R.mipmap.ic_weather_29;
                break;
            case 30:id =R.mipmap.ic_weather_30;
                break;
            case 31:id =R.mipmap.ic_weather_31;
                break;
            case 32:id =R.mipmap.ic_weather_32;
                break;
            case 33:id =R.mipmap.ic_weather_33;
                break;
            case 34:id =R.mipmap.ic_weather_34;
                break;
            case 35:id =R.mipmap.ic_weather_35;
                break;
            case 36:id =R.mipmap.ic_weather_36;
                break;
            case 37:id =R.mipmap.ic_weather_37;
                break;
            case 38:id =R.mipmap.ic_weather_38;
                break;
            case 39:id =R.mipmap.ic_weather_39;
                break;
            case 40:id =R.mipmap.ic_weather_40;
                break;
            case 41:id =R.mipmap.ic_weather_41;
                break;
            case 42:id =R.mipmap.ic_weather_42;
                break;
            case 43:id =R.mipmap.ic_weather_43;
                break;
            case 44:id =R.mipmap.ic_weather_44;
                break;
                default:
                    id = R.mipmap.ic_wether_icon_error;
                    break;
        }
        return id;
    }

    public int getDrawableDropIcon(Integer n){
        int id = 0 ;
        if (n <= 10)
            id = R.drawable.ic_drop_0;
        if (n > 10 && n <= 60)
            id = R.drawable.ic_drop_40;
        if (n > 60 && n <= 85)
            id = R.drawable.ic_drop_80;
        if (n > 85)
            id = R.drawable.ic_drops_100;
        return id;
    }

    public int getColorChengeTermometer(Double temperature, Context context){
        int temp_0,temp_10,temp_20,temp_30,temp_40;
         temp_0 = 0;
         temp_10 = 10;
         temp_20 = 20;
         temp_30 = 30;
         temp_40 = 40;

        if (temperature != null)
        {
            if (temperature >= temp_10 && temperature < 20)
                return ContextCompat.getColor(context,R.color.temp_10);
            if (temperature >= temp_0 && temperature < temp_10)
                return ContextCompat.getColor(context,R.color.temp_0);
            if (temperature < -temp_10)
                return ContextCompat.getColor(context,R.color.temp_min10);
            if(temperature >= temp_40)
                return Color.RED;
            if (temperature >= -temp_10 && temperature < temp_0)
                return ContextCompat.getColor(context,R.color.temp_min10);
            if (temperature >= temp_30 && temperature < temp_40)
                return ContextCompat.getColor(context,R.color.temp_30);
            if (temperature >= temp_20 && temperature < temp_30)
                return ContextCompat.getColor(context,R.color.temp_20);
        }
        return Color.BLACK;
    }

    public boolean getDrawableUmbrella(int n){
        boolean is = false;
        if (n > 39){
            is = true;
        }
        return is;
    }

    public String parseDateToString_MonthAndDay(String in_date){
        String date;
        /*SimpleDateFormat simpleDateFormat_day = new SimpleDateFormat("d");*/
        /*SimpleDateFormat simpleDateFormat_month = new SimpleDateFormat("MMMM",new Locale("uk","UA"));*/
        SimpleDateFormat simpleDateFormat_month = new SimpleDateFormat("EEEE, d MMM",new Locale("uk","UA"));
        Calendar calendar = parseDate(in_date);

        date = simpleDateFormat_month.format(calendar.getTime());

        return date;
    }

    private Calendar parseDate(String in_date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.ENGLISH);
        try {
            Date date = dateFormat.parse(in_date);
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            return calendar;
        }
    }

    public List<String> parseTime(List<Hourly_12HourModel> list){
        Date date;
        List<String> time = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat  simpleDateFormat = new SimpleDateFormat("HH:mm");
        for (Hourly_12HourModel hourModel : list){
            try {
                date = dateFormat.parse(hourModel.getDateTime());
                time.add(simpleDateFormat.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return time;
    }

    public String parseTimeHourMinutesToString(String time) throws ParseException {
        Date date;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat  simpleDateFormat = new SimpleDateFormat("HH:mm");
            date  = dateFormat.parse(time);
        return simpleDateFormat.format(date);
    }

    public List<Double> parseTemperature_Double(List<Hourly_12HourModel> list){
        List<Double> mTemperature_Double = new ArrayList<>();
        for (Hourly_12HourModel hourModel : list){
            mTemperature_Double.add(hourModel.getTemperature().getValue());
        }
        return mTemperature_Double;
    }

    public List<Double> parseTemperatureRealFeel(List<Hourly_12HourModel> list){
        List<Double> mRealFeelTemperature = new ArrayList<>();
        for (Hourly_12HourModel hourModel : list){
            mRealFeelTemperature.add(hourModel.getRealFeelTemperature().getValue());
        }
        return mRealFeelTemperature;
    }

    public List<Double> parseWindSpeed(List<Hourly_12HourModel> list){
        ArrayList<Double> windSpeed = new ArrayList<>();
        for (Hourly_12HourModel hourModel : list) {
            windSpeed.add(hourModel.getWind().getSpeed().getValue());
        }
        return windSpeed;
    }

    public List<Integer> parseDirectionWind(List<Hourly_12HourModel> list){
        List<Integer> directionWind = new ArrayList<>();
        for (Hourly_12HourModel hourModel : list) {
            directionWind.add(hourModel.getWind().getDirection().getDegrees());
        }
        return directionWind;
    }

    public List<Integer> parseNumberIconWeather(List<Hourly_12HourModel> list){
        List<Integer> integerList = new ArrayList<>();
        for (Hourly_12HourModel hourModel: list) {
            integerList.add(hourModel.getWeatherIcon());
        }
        return integerList;
    }

    public List<Integer> parsePrecipitation(List<Hourly_12HourModel> list){
        List<Integer> doubleList = new ArrayList<>();
        for (Hourly_12HourModel hourModel: list) {
            doubleList.add(hourModel.getPrecipitationProbability());
        }
        return doubleList;
    }
}
