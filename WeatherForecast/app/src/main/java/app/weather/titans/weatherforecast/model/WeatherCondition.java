package app.weather.titans.weatherforecast.model;

import android.graphics.Bitmap;

/**
 * Created by totha on 2016. 08. 03..
 */
public class WeatherCondition {

    private String mDescription;
    private float mTemperature;
    private float mTempMin;
    private float mTempMax;
    private float mPressure;
    private float mHumidity;
    private float mRain;
    private Bitmap mIcon;
    private String mIconCode;


    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public float getmTemperature() {
        return mTemperature;
    }

    public void setmTemperature(float mTemperature) {
        this.mTemperature = mTemperature;
    }

    public float getmTempMin() {
        return mTempMin;
    }

    public void setmTempMin(float mTempMin) {
        this.mTempMin = mTempMin;
    }

    public float getmTempMax() {
        return mTempMax;
    }

    public void setmTempMax(float mTempMax) {
        this.mTempMax = mTempMax;
    }

    public float getmPressure() {
        return mPressure;
    }

    public void setmPressure(float mPressure) {
        this.mPressure = mPressure;
    }

    public float getmHumidity() {
        return mHumidity;
    }

    public void setmHumidity(float mHumidity) {
        this.mHumidity = mHumidity;
    }

    public float getmRain() {
        return mRain;
    }

    public void setmRain(float mRain) {
        this.mRain = mRain;
    }

    public Bitmap getmIcon() {
        return mIcon;
    }

    public void setmIcon(Bitmap mIcon) {
        this.mIcon = mIcon;
    }

    public String getmIconCode() {
        return mIconCode;
    }

    public void setmIconCode(String mIconCode) {
        this.mIconCode = mIconCode;
    }
}
