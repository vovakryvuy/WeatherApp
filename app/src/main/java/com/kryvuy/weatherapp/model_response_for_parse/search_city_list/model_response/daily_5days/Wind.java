package com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.daily_5days;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Володимир on 9/4/2017.
 */

public class Wind {
    @SerializedName("Speed")
    @Expose
    private Speed speed;
    @SerializedName("Direction")
    @Expose
    private Direction direction;

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
