package com.p92rdi.extendedweathertitan.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by totha on 2016. 08. 03..
 */
public class DailyForecast {

    private long mDate;
    private String mIconCodeDay;
    private String mIconCodeNight;
    private String mDescriptionDay;
    private String mDescriptionNight;
    private float mTemperatureDay;
    private float mTemperatureNight;

    public DailyForecast(WeatherForecast weatherForecast, long date){
        ArrayList<Forecast> forecastsList = new ArrayList<>(weatherForecast.getmForecastsList());
        mDate = date;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        for(Forecast forecast : forecastsList){

            calendar.setTimeInMillis(forecast.getmDate());
            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

            int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

            if( currentDay == day){
                //System.out.println("currentDay == day");
                //System.out.println("currentHour: " + currentHour);
                if(currentHour == 1 || currentHour == 2 || currentHour == 3 || currentHour == 4){
                    //System.out.println("currentHour == 2");
                    mIconCodeNight = forecast.getmWeatherCondition().getmIconCode();
                    mDescriptionNight = forecast.getmWeatherCondition().getmDescription();
                    mTemperatureNight = forecast.getmWeatherCondition().getmTemperature();
                }
                else if(currentHour == 12 || currentHour == 13 || currentHour == 14 || currentHour == 15) {
                    //System.out.println("currentHour == 14");
                    mIconCodeDay = forecast.getmWeatherCondition().getmIconCode();
                    mDescriptionDay = forecast.getmWeatherCondition().getmDescription();
                    mTemperatureDay = forecast.getmWeatherCondition().getmTemperature();
                }
            }
        }
    }

    public ArrayList<DailyForecast> generateFiveDaysForecastsList(WeatherForecast weatherForecast, List<Long> mDays){
        ArrayList<DailyForecast> fiveDaysForecastsList = new ArrayList<>();
        for(long date : mDays){
            DailyForecast currentDay = new DailyForecast(weatherForecast, date);
            fiveDaysForecastsList.add(currentDay);
        }

        return fiveDaysForecastsList;
    }

    public long getmDate() {
        return mDate;
    }

    public String getmIconCodeDay() {
        return mIconCodeDay;
    }

    public String getmIconCodeNight() {
        return mIconCodeNight;
    }

    public String getmDescriptionDay() {
        return mDescriptionDay;
    }

    public String getmDescriptionNight() {
        return mDescriptionNight;
    }

    public float getmTemperatureDay() {
        return mTemperatureDay;
    }

    public float getmTemperatureNight() {
        return mTemperatureNight;
    }

}
