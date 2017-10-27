package com.kryvuy.weatherapp.data_base;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Володимир on 10/23/2017.
 */

public class DatabaseWetherFiveDay extends RealmObject {
    @PrimaryKey
    private int id;

    private String data;
    private double dayTemperature;
    private double nightTemperature;
    private String dayDiscribe;
    private String nightDiscribe;
    private int dayIdIcon;
    private int nightIdIcon;
    private int dayPrecipation;
    private int nightPrecipation;
    private int daySpeedWind;
    private int nightSpeedWind;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getDayTemperature() {
        return dayTemperature;
    }

    public void setDayTemperature(Double dayTemperature) {
        this.dayTemperature = dayTemperature;
    }

    public Double getNightTemperature() {
        return nightTemperature;
    }

    public void setNightTemperature(Double nightTemperature) {
        this.nightTemperature = nightTemperature;
    }

    public String getDayDiscribe() {
        return dayDiscribe;
    }

    public void setDayDiscribe(String dayDiscribe) {
        this.dayDiscribe = dayDiscribe;
    }

    public String getNightDiscribe() {
        return nightDiscribe;
    }

    public void setNightDiscribe(String nightDiscribe) {
        this.nightDiscribe = nightDiscribe;
    }

    public int getDayIdIcon() {
        return dayIdIcon;
    }

    public void setDayIdIcon(int dayIdIcon) {
        this.dayIdIcon = dayIdIcon;
    }

    public int getNightIdIcon() {
        return nightIdIcon;
    }

    public void setNightIdIcon(int nightIdIcon) {
        this.nightIdIcon = nightIdIcon;
    }

    public int getDayPrecipation() {
        return dayPrecipation;
    }

    public void setDayPrecipation(int dayPrecipation) {
        this.dayPrecipation = dayPrecipation;
    }

    public int getNightPrecipation() {
        return nightPrecipation;
    }

    public void setNightPrecipation(int nightPrecipation) {
        this.nightPrecipation = nightPrecipation;
    }

    public int getDaySpeedWind() {
        return daySpeedWind;
    }

    public void setDaySpeedWind(int daySpeedWind) {
        this.daySpeedWind = daySpeedWind;
    }

    public int getNightSpeedWind() {
        return nightSpeedWind;
    }

    public void setNightSpeedWind(int nightSpeedWind) {
        this.nightSpeedWind = nightSpeedWind;
    }

}
