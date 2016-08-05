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
                }
                else if(currentHour == 12 || currentHour == 13 || currentHour == 14 || currentHour == 15) {
                    mIconCodeDay = forecast.getmWeatherCondition().getmIconCode();
                    mDescriptionDay = forecast.getmWeatherCondition().getmDescription();
                    mTemperatureDay = forecast.getmWeatherCondition().getmTemperature();
                }

                /*Thread mNetworkThread = new Thread(new Runnable() {
                    public void run() {
                        HttpClient httpClient = new HttpClient();
                        mIconNight = httpClient.getImage(mIconCodeNight);
                        mIconDay = httpClient.getImage(mIconCodeDay);
                    }
                });
                mNetworkThread.start();
                try {
                    mNetworkThread.join();
                } catch (InterruptedException e) {
                }*/
            }

            DailyForecastTask task = new DailyForecastTask();
            task.execute(new String[]{mIconCodeNight, mIconCodeDay});
        }
    }

    //Itt valami nagyon nem jó...
    private class DailyForecastTask extends AsyncTask<String, Void, ArrayList<Bitmap> > {

        @Override
        protected ArrayList<Bitmap> doInBackground(String... params) {
            //Looper.prepare();
            ArrayList<Bitmap> icons = new ArrayList<>();

            HttpClient httpClient = new HttpClient();

            icons.add(httpClient.getImage(params[0]));
            icons.add(httpClient.getImage(params[1]));

            return icons;
        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> icons) {
            super.onPostExecute(icons);
            DailyForecast.this.mIconNight = icons.get(0);
            DailyForecast.this.mIconDay = icons.get(1);
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