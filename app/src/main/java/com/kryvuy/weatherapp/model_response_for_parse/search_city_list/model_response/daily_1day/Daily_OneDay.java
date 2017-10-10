package com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.daily_1day;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Володимир on 5/4/2017.
 */

public class Daily_OneDay {
    @SerializedName("Headline")
    @Expose
    private Headline headline;
    @SerializedName("DailyForecasts")
    @Expose
    private List<DailyForecast> dailyForecasts = null;

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public List<DailyForecast> getDailyForecasts() {
        return dailyForecasts;
    }

    public void setDailyForecasts(List<DailyForecast> dailyForecasts) {
        this.dailyForecasts = dailyForecasts;
    }

}
