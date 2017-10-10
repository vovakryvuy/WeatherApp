package com.kryvuy.weatherapp.model_response_for_parse.search_city_list.model_response.hourly_12hour_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Володимир on 5/5/2017.
 */

public class Hourly_12HourModel {
    @SerializedName("DateTime")
    @Expose
    private String dateTime;
    @SerializedName("EpochDateTime")
    @Expose
    private Integer epochDateTime;
    @SerializedName("WeatherIcon")
    @Expose
    private Integer weatherIcon;
    @SerializedName("IconPhrase")
    @Expose
    private String iconPhrase;
    @SerializedName("IsDaylight")
    @Expose
    private Boolean isDaylight;
    @SerializedName("Temperature")
    @Expose
    private Temperature temperature;
    @SerializedName("RealFeelTemperature")
    @Expose
    private RealFeelTemperature realFeelTemperature;
    @SerializedName("WetBulbTemperature")
    @Expose
    private WetBulbTemperature wetBulbTemperature;
    @SerializedName("DewPoint")
    @Expose
    private DewPoint dewPoint;
    @SerializedName("Wind")
    @Expose
    private Wind wind;
    @SerializedName("WindGust")
    @Expose
    private WindGust windGust;
    @SerializedName("RelativeHumidity")
    @Expose
    private Integer relativeHumidity;
    @SerializedName("Visibility")
    @Expose
    private Visibility visibility;
    @SerializedName("Ceiling")
    @Expose
    private Ceiling ceiling;
    @SerializedName("UVIndex")
    @Expose
    private Integer uVIndex;
    @SerializedName("UVIndexText")
    @Expose
    private String uVIndexText;
    @SerializedName("PrecipitationProbability")
    @Expose
    private Integer precipitationProbability;
    @SerializedName("RainProbability")
    @Expose
    private Integer rainProbability;
    @SerializedName("SnowProbability")
    @Expose
    private Integer snowProbability;
    @SerializedName("IceProbability")
    @Expose
    private Integer iceProbability;
    @SerializedName("TotalLiquid")
    @Expose
    private TotalLiquid totalLiquid;
    @SerializedName("Rain")
    @Expose
    private Rain rain;
    @SerializedName("Snow")
    @Expose
    private Snow snow;
    @SerializedName("Ice")
    @Expose
    private Ice ice;
    @SerializedName("CloudCover")
    @Expose
    private Integer cloudCover;
    @SerializedName("MobileLink")
    @Expose
    private String mobileLink;
    @SerializedName("Link")
    @Expose
    private String link;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getEpochDateTime() {
        return epochDateTime;
    }

    public void setEpochDateTime(Integer epochDateTime) {
        this.epochDateTime = epochDateTime;
    }

    public Integer getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(Integer weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public String getIconPhrase() {
        return iconPhrase;
    }

    public void setIconPhrase(String iconPhrase) {
        this.iconPhrase = iconPhrase;
    }

    public Boolean getIsDaylight() {
        return isDaylight;
    }

    public void setIsDaylight(Boolean isDaylight) {
        this.isDaylight = isDaylight;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public RealFeelTemperature getRealFeelTemperature() {
        return realFeelTemperature;
    }

    public void setRealFeelTemperature(RealFeelTemperature realFeelTemperature) {
        this.realFeelTemperature = realFeelTemperature;
    }

    public WetBulbTemperature getWetBulbTemperature() {
        return wetBulbTemperature;
    }

    public void setWetBulbTemperature(WetBulbTemperature wetBulbTemperature) {
        this.wetBulbTemperature = wetBulbTemperature;
    }

    public DewPoint getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(DewPoint dewPoint) {
        this.dewPoint = dewPoint;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public WindGust getWindGust() {
        return windGust;
    }

    public void setWindGust(WindGust windGust) {
        this.windGust = windGust;
    }

    public Integer getRelativeHumidity() {
        return relativeHumidity;
    }

    public void setRelativeHumidity(Integer relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public Ceiling getCeiling() {
        return ceiling;
    }

    public void setCeiling(Ceiling ceiling) {
        this.ceiling = ceiling;
    }

    public Integer getUVIndex() {
        return uVIndex;
    }

    public void setUVIndex(Integer uVIndex) {
        this.uVIndex = uVIndex;
    }

    public String getUVIndexText() {
        return uVIndexText;
    }

    public void setUVIndexText(String uVIndexText) {
        this.uVIndexText = uVIndexText;
    }

    public Integer getPrecipitationProbability() {
        return precipitationProbability;
    }

    public void setPrecipitationProbability(Integer precipitationProbability) {
        this.precipitationProbability = precipitationProbability;
    }

    public Integer getRainProbability() {
        return rainProbability;
    }

    public void setRainProbability(Integer rainProbability) {
        this.rainProbability = rainProbability;
    }

    public Integer getSnowProbability() {
        return snowProbability;
    }

    public void setSnowProbability(Integer snowProbability) {
        this.snowProbability = snowProbability;
    }

    public Integer getIceProbability() {
        return iceProbability;
    }

    public void setIceProbability(Integer iceProbability) {
        this.iceProbability = iceProbability;
    }

    public TotalLiquid getTotalLiquid() {
        return totalLiquid;
    }

    public void setTotalLiquid(TotalLiquid totalLiquid) {
        this.totalLiquid = totalLiquid;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Snow getSnow() {
        return snow;
    }

    public void setSnow(Snow snow) {
        this.snow = snow;
    }

    public Ice getIce() {
        return ice;
    }

    public void setIce(Ice ice) {
        this.ice = ice;
    }

    public Integer getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(Integer cloudCover) {
        this.cloudCover = cloudCover;
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
