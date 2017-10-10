package com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.daily_5days;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Володимир on 9/4/2017.
 */

public class DegreeDaySummary {
    @SerializedName("Heating")
    @Expose
    private Heating heating;
    @SerializedName("Cooling")
    @Expose
    private Cooling cooling;

    public Heating getHeating() {
        return heating;
    }

    public void setHeating(Heating heating) {
        this.heating = heating;
    }

    public Cooling getCooling() {
        return cooling;
    }

    public void setCooling(Cooling cooling) {
        this.cooling = cooling;
    }
}
