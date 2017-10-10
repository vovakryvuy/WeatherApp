package com.kryvuy.weatherapp.api;

import com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.daily_1day.Daily_OneDay;
import com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.daily_5days.Daily_FiveDay;
import com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.hourly_12hour_model.Hourly_12HourModel;
import com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.search_city.SearchCityByName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Володимир on 4/29/2017.
 */

public interface Interface_Response {
    /*
        search cities */
    @GET("locations/v1/cities/autocomplete")
    Call<List<SearchCityByName>> getListCities(@Query("apikey") String api, @Query("q") String name_city,
                                               @Query("language") String languuage);

    /*
    get weather 1 day by key location*/
    @GET("forecasts/v1/daily/1day/{key_location}")
    Call<Daily_OneDay> getDaily_1Day(@Path("key_location")String key_city,@Query("apikey") String api,
                                     @Query("language") String language,
                                     @Query("details") Boolean detail, @Query("metric") Boolean metric);

    /*
    get weather 5 days by key location*/
    @GET("forecasts/v1/daily/5day/{key_location}")
    Call<Daily_FiveDay> getDaily_5Day(@Path("key_location")String key_city, @Query("apikey") String api,
                                          @Query("language")String language, @Query("details")Boolean details,
                                          @Query("metric")Boolean metric);

    /*
    get wether hourly 12 hour*/
    @GET("forecasts/v1/hourly/12hour/{key_location}")
    Call<List<Hourly_12HourModel>> getHourly_12Hour(@Path("key_location")String key_city,
                                                    @Query("apikey") String api, @Query("language") String language,
                                                    @Query("details") Boolean detail, @Query("metric") Boolean metric);
}