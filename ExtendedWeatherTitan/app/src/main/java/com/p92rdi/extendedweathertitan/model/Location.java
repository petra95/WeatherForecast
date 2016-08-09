package com.p92rdi.extendedweathertitan.model;

/**
 * Created by totha on 2016. 08. 03..
 */
public class Location {
    private float mLongitude;
    private float mLatitude;
    private String mCountry;
    private String mCity;
    private int mCityId;

    public Location(){
        mCity = "unkown";
    }

    public float getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(float mLongitude) {
        this.mLongitude = mLongitude;
    }

    public float getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(float mLatitude) {
        this.mLatitude = mLatitude;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public int getmCityId() {
        return mCityId;
    }

    public void setmCityId(int mCityId) {
        this.mCityId = mCityId;
    }


}
