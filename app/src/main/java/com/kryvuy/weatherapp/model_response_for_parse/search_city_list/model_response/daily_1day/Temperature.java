package com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.daily_1day;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Володимир on 5/4/2017.
 */

public class Temperature {
    @SerializedName("Minimum")
    @Expose
    private Minimum minimum;
    @SerializedName("Maximum")
    @Expose
    private Maximum maximum;

    public Minimum getMinimum() {
        return minimum;
    }

    public void setMinimum(Minimum minimum) {
        this.minimum = minimum;
    }

    public Maximum getMaximum() {
        return maximum;
    }

    public void setMaximum(Maximum maximum) {
        this.maximum = maximum;
    }
}
