package com.p92rdi.extendedweathertitan.model;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WeatherForecast{

    private Location mLocation;
    private List<Long> mDays;
    private ArrayList<Forecast> mForecastsList;

    public WeatherForecast() {
        mDays = new ArrayList<>();
        mLocation = new Location();
    }

    public Location getmLocation() {
        return mLocation;
    }

    public void setmLocation(Location mLocation) {
        this.mLocation = mLocation;
    }

    public ArrayList<Forecast> getmForecastsList() {
        return mForecastsList;
    }

    public void setmForecastsList(ArrayList<Forecast> mForecastsList) {
        this.mForecastsList = mForecastsList;
        collectDays();
    }

    public List<Long> getmDays(){
        return mDays;
    }

    //collect all different days, we dont know nothing about hours
    private void collectDays() {
        Calendar calendar = Calendar.getInstance();
        int prevDay = 0;

        for(Forecast forecast : mForecastsList){
            calendar.setTimeInMillis(forecast.getmDate());
            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
            Log.d("ExtendedWeatherTitan", "currentDay: " + currentDay);
            if(prevDay != currentDay && mDays.size() < 5){
                mDays.add(forecast.getmDate());
            }
            prevDay = currentDay;
        }

        Log.d("ExtendedWeatherTitan", "mDays.size(): " + mDays.size());
    }

}
