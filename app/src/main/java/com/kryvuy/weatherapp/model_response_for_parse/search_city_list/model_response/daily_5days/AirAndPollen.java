package com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.daily_5days;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Володимир on 9/4/2017.
 */

public class AirAndPollen {
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Value")
    @Expose
    private Integer value;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("CategoryValue")
    @Expose
    private Integer categoryValue;
    @SerializedName("Type")
    @Expose
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCategoryValue() {
        return categoryValue;
    }

    public void setCategoryValue(Integer categoryValue) {
        this.categoryValue = categoryValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
