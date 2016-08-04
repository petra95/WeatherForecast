package app.weather.titans.weatherforecast.model;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import app.weather.titans.weatherforecast.model.Location;
import app.weather.titans.weatherforecast.R;
import app.weather.titans.weatherforecast.model.Forecast;

public class WeatherForecast extends AppCompatActivity {

    private Location mLocation;
    private List<Long> mDays;
    private ArrayList<Forecast> mForecastsList;

    public WeatherForecast() {
        mDays = new ArrayList<>();
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
            if(prevDay != currentDay){
                mDays.add(forecast.getmDate());
            }
            prevDay = currentDay;
        }
    }
}
