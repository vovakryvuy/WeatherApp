package com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.daily_5days;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Володимир on 9/4/2017.
 */

public class Moon {
    @SerializedName("Rise")
    @Expose
    private String rise;
    @SerializedName("EpochRise")
    @Expose
    private Integer epochRise;
    @SerializedName("Set")
    @Expose
    private String set;
    @SerializedName("EpochSet")
    @Expose
    private Integer epochSet;
    @SerializedName("Phase")
    @Expose
    private String phase;
    @SerializedName("Age")
    @Expose
    private Integer age;

    public String getRise() {
        return rise;
    }

    public void setRise(String rise) {
        this.rise = rise;
    }

    public Integer getEpochRise() {
        return epochRise;
    }

    public void setEpochRise(Integer epochRise) {
        this.epochRise = epochRise;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public Integer getEpochSet() {
        return epochSet;
    }

    public void setEpochSet(Integer epochSet) {
        this.epochSet = epochSet;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
