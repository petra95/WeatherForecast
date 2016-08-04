package com.p92rdi.extendedweathertitan.model;

import android.graphics.Bitmap;

public class Weather {
    private int mCityId;
    private String mCity;
    private String mCountry;
    private String mDescription;
    private int mTemperature;
    private int mTempMin;
    private int mTempMax;
    private Bitmap mIcon;
    private String mIconCode;
    private int mHumidity;
    private int mWind;

    public Weather() {

        mCityId = -1;
        mCity = "Unknown Settlement!";
        mCountry = "";
        mDescription = "";
        mTemperature = 273;
        mTempMin = 273;
        mTempMax = 273;
        mIcon = null;
        mIconCode = "";
        mHumidity = 0;
        mWind = 0;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getmTemperature() {
        return mTemperature;
    }

    public void setmTemperature(int mTemperature) {
        this.mTemperature = mTemperature;
    }

    public int getmTempMin() {
        return mTempMin;
    }

    public void setmTempMin(int mTempMin) {
        this.mTempMin = mTempMin;
    }

    public int getmTempMax() {
        return mTempMax;
    }

    public void setmTempMax(int mTempMax) {
        this.mTempMax = mTempMax;
    }

    public Bitmap getmIcon() {
        return mIcon;
    }

    public void setmIcon(Bitmap mIcon) {
        this.mIcon = mIcon;
    }

    public int getmHumidity() {
        return mHumidity;
    }

    public void setmHumidity(int mHumidity) {
        this.mHumidity = mHumidity;
    }

    public int getmWind() {
        return mWind;
    }

    public void setmWind(int mWind) {
        this.mWind = mWind;
    }

    public int getmCityId() {
        return mCityId;
    }

    public void setmCityId(int mCityId) {
        this.mCityId = mCityId;
    }

    public String getmIconCode() {
        return mIconCode;
    }

    public void setmIconCode(String mIconCode) {
        this.mIconCode = mIconCode;
    }
}
