package com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.hourly_12hour_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Володимир on 5/5/2017.
 */

public class TotalLiquid {
    @SerializedName("Value")
    @Expose
    private Integer value;
    @SerializedName("Unit")
    @Expose
    private String unit;
    @SerializedName("UnitType")
    @Expose
    private Integer unitType;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getUnitType() {
        return unitType;
    }

    public void setUnitType(Integer unitType) {
        this.unitType = unitType;
    }

}
