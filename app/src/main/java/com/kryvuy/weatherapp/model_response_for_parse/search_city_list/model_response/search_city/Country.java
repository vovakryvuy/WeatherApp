package com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.search_city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Володимир on 4/29/2017.
 */

public class Country {
    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("LocalizedName")
    @Expose
    private String localizedName;

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }
}
