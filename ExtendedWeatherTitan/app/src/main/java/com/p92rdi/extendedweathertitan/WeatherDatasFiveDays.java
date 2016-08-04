package com.p92rdi.extendedweathertitan;

import com.p92rdi.extendedweathertitan.model.DailyForecast;
import com.p92rdi.extendedweathertitan.model.WeatherForecast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by totha on 2016. 08. 04..
 */
public class WeatherDatasFiveDays {

    ArrayList<DailyForecast> fiveDays;

    public WeatherDatasFiveDays(WeatherForecast weatherForecast, List<Long> mDays){
        ArrayList<DailyForecast> fiveDay = new ArrayList<>();
        fillWithDatas(weatherForecast, mDays);
    }

    private void fillWithDatas(WeatherForecast weatherForecast, List<Long> mDays){
        for(long date : mDays){
            DailyForecast currentDay = new DailyForecast(weatherForecast, date);
            fiveDays.add(currentDay);
        }
    }

    public ArrayList<DailyForecast> getFiveDays() {
        return fiveDays;
    }
}
