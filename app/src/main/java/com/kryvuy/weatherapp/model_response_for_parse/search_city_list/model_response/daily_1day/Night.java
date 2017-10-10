package com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.daily_1day;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Володимир on 5/4/2017.
 */

public class Night {
    @SerializedName("Icon")
    @Expose
    private Integer icon;
    @SerializedName("IconPhrase")
    @Expose
    private String iconPhrase;

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public String getIconPhrase() {
        return iconPhrase;
    }

    public void setIconPhrase(String iconPhrase) {
        this.iconPhrase = iconPhrase;
    }
}
