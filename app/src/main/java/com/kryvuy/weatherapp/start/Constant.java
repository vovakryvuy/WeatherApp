package com.kryvuy.weatherapp.start;

import android.content.Context;

/**
 * Created by Володимир on 4/29/2017.
 */

public  class Constant {
    public static final String API_KEY = "WKfgB10KxfqPC9dpIMEKjERr50ZBaVO6";
    public static final String BASE_URL = "http://dataservice.accuweather.com/";
    public static final String LANGUAGE = "en-us";
    public static final String LANGUAGE_UA = "uk-ua";
    public static final String SAVE_CITY_KEY = "save_city_key";
    public static final String EXTRA_KEY_LOCATION_CITY = "com.kryvuy.weatherapp.start.Constant";
    public static final String SAVE_NAME_CITY = "save_name_city";
    public static final long MIN_DISTANCE_CHANGE_GPS_FOR_UPDATE = 2000;
    public static final long MIN_TIME_BW_GPS_UPDATE = 1000 * 60 * 60;
}
