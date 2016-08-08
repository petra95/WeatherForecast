package com.p92rdi.extendedweathertitan.model;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;

import com.p92rdi.extendedweathertitan.helper.HttpClient;
import com.p92rdi.extendedweathertitan.helper.JSONWeatherParser;

import org.json.JSONException;

import java.text.ParseException;
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
    private Bitmap mIconDay;
    private Bitmap mIconNight;

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
                if(currentHour == 1 || currentHour == 2 || currentHour == 3 || currentHour == 4){
                    mIconCodeNight = forecast.getmWeatherCondition().getmIconCode();
                    mDescriptionNight = forecast.getmWeatherCondition().getmDescription();
                    mTemperatureNight = forecast.getmWeatherCondition().getmTemperature();

                    DailyForecastTask task = new DailyForecastTask();
                    if(!mIconCodeNight.equals("")) {
                        Log.d("ExtendedWeatherTitan", "DailyForecast / mIconCodeNight: " + mIconCodeNight);
                        task.execute(mIconCodeNight, "night");
                    }
                }
                else if(currentHour == 12 || currentHour == 13 || currentHour == 14 || currentHour == 15) {
                    mIconCodeDay = forecast.getmWeatherCondition().getmIconCode();
                    mDescriptionDay = forecast.getmWeatherCondition().getmDescription();
                    mTemperatureDay = forecast.getmWeatherCondition().getmTemperature();

                    DailyForecastTask task = new DailyForecastTask();
                    if(!mIconCodeDay.equals("")) {
                        Log.d("ExtendedWeatherTitan", "DailyForecast / mIconCodeDay: " + mIconCodeDay);
                        task.execute(mIconCodeDay, "day");
                    }
                }
            }
        }
    }

    private class DailyForecastTask extends AsyncTask<String, Void, Bitmap> {

        boolean isDay;

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap icon;
            HttpClient httpClient = new HttpClient();

            icon = httpClient.getImage(params[0]);
            Log.d("ExtendedWeatherTitan", "doInBackground / params[0] = " + params[0]);
            isDay = params[1].equals("day");

            if(icon == null) {
                Log.d("ExtendedWeatherTitan", "doInBackground / icon is NULL!");
            }

            return icon;
        }

        @Override
        protected void onPostExecute(Bitmap icon) {
            super.onPostExecute(icon);
            if(isDay){
                DailyForecast.this.mIconDay = icon;
            }else {
                DailyForecast.this.mIconNight = icon;
            }
        }
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

    public Bitmap getmIconNight() {
        return mIconNight;
    }

    public Bitmap getmIconDay() {
        return mIconDay;
    }

}