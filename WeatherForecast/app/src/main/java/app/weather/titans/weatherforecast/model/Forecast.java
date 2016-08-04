package app.weather.titans.weatherforecast.model;

import java.util.Date;

/**
 * Created by totha on 2016. 08. 03..
 */
public class Forecast {

    private long mDate;
    WeatherCondition mWeatherCondition;
    Clouds mClouds;
    Wind mWind;

    public long getmDate() {
        return mDate;
    }

    public void setmDate(long date) {
        this.mDate = date;
    }

    public WeatherCondition getmWeatherCondition() {
        return mWeatherCondition;
    }

    public void setmWeatherCondition(WeatherCondition mWeatherCondition) {
        this.mWeatherCondition = mWeatherCondition;
    }

    public Clouds getmClouds() {
        return mClouds;
    }

    public void setmClouds(Clouds mClouds) {
        this.mClouds = mClouds;
    }

    public Wind getmWind() {
        return mWind;
    }

    public void setmWind(Wind mWind) {
        this.mWind = mWind;
    }

}
