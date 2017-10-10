package com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.hourly_12hour_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Володимир on 5/5/2017.
 */

public class WindGust {
    @SerializedName("Speed")
    @Expose
    private Speed_ speed;

    public Speed_ getSpeed() {
        return speed;
    }

    public void setSpeed(Speed_ speed) {
        this.speed = speed;
    }
}
