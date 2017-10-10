package com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.search_city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.search_city.AdministrativeArea;
import com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.search_city.Country;

/**
 * Created by Володимир on 4/29/2017.
 */

public class SearchCityByName {
    @SerializedName("Version")
    @Expose
    private Integer version;
    @SerializedName("Key")
    @Expose
    private String key;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Rank")
    @Expose
    private Integer rank;
    @SerializedName("LocalizedName")
    @Expose
    private String localizedName;
    @SerializedName("Country")
    @Expose
    private Country country;
    @SerializedName("AdministrativeArea")
    @Expose
    private AdministrativeArea administrativeArea;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public AdministrativeArea getAdministrativeArea() {
        return administrativeArea;
    }

    public void setAdministrativeArea(AdministrativeArea administrativeArea) {
        this.administrativeArea = administrativeArea;
    }
}
