package com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.daily_5days;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Володимир on 9/4/2017.
 */

public class Headline {
    @SerializedName("EffectiveDate")
    @Expose
    private String effectiveDate;
    @SerializedName("EffectiveEpochDate")
    @Expose
    private Integer effectiveEpochDate;
    @SerializedName("Severity")
    @Expose
    private Integer severity;
    @SerializedName("Text")
    @Expose
    private String text;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("EndDate")
    @Expose
    private String endDate;
    @SerializedName("EndEpochDate")
    @Expose
    private Integer endEpochDate;
    @SerializedName("MobileLink")
    @Expose
    private String mobileLink;
    @SerializedName("Link")
    @Expose
    private String link;

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Integer getEffectiveEpochDate() {
        return effectiveEpochDate;
    }

    public void setEffectiveEpochDate(Integer effectiveEpochDate) {
        this.effectiveEpochDate = effectiveEpochDate;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getEndEpochDate() {
        return endEpochDate;
    }

    public void setEndEpochDate(Integer endEpochDate) {
        this.endEpochDate = endEpochDate;
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
