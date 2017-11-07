package com.kryvuy.weatherapp.data_base;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Volodymyr on 10/31/2017.
 */

public class DatabaseWetherTwelveHour extends RealmObject {
    @PrimaryKey
    private int id;

    private String time;
    private double temperature;
    private double realFeelTemperature;
    private int idIcon;
    private int precipitation;
    private double speedWind;
    private int directionWind;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getRealFeelTemperature() {
        return realFeelTemperature;
    }

    public void setRealFeelTemperature(double realFeelTemperature) {
        this.realFeelTemperature = realFeelTemperature;
    }

    public int getIdIcon() {
        return idIcon;
    }

    public void setIdIcon(int idIcon) {
        this.idIcon = idIcon;
    }

    public int getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(int precipitation) {
        this.precipitation = precipitation;
    }

    public double getSpeedWind() {
        return speedWind;
    }

    public void setSpeedWind(double speedWind) {
        this.speedWind = speedWind;
    }

    public int getDirectionWind() {
        return directionWind;
    }

    public void setDirectionWind(int directionWind) {
        this.directionWind = directionWind;
    }
}
