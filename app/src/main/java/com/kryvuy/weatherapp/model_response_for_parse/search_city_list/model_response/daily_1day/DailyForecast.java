package com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.daily_1day;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Володимир on 5/4/2017.
 */

public class DailyForecast {
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("EpochDate")
    @Expose
    private Integer epochDate;
    @SerializedName("Temperature")
    @Expose
    private Temperature temperature;
    @SerializedName("Day")
    @Expose
    private Day day;
    @SerializedName("Night")
    @Expose
    private Night night;
    @SerializedName("Sources")
    @Expose
    private List<String> sources = null;
    @SerializedName("MobileLink")
    @Expose
    private String mobileLink;
    @SerializedName("Link")
    @Expose
    private String link;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getEpochDate() {
        return epochDate;
    }

    public void setEpochDate(Integer epochDate) {
        this.epochDate = epochDate;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Night getNight() {
        return night;
    }

    public void setNight(Night night) {
        this.night = night;
    }

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    public String getMobileLink() {
        return mobileLink;
    }

    public void setMobileLink(String mobileLink) {
        this.mobileLink = mobileLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
