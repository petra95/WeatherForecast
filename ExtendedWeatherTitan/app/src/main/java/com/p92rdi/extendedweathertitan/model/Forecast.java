package com.p92rdi.extendedweathertitan.model;

public class Forecast {

    private long mDate;
    WeatherCondition mWeatherCondition;
    Clouds mClouds;
    Wind mWind;

    public long getmDate() {
        return mDate;
    }

    public void setmDate(long date) {
        long helper = 1000;
        this.mDate = date * helper;
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
