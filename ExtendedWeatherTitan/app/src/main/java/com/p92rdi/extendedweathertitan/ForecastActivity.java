package com.p92rdi.extendedweathertitan;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.p92rdi.extendedweathertitan.helper.HttpClient;
import com.p92rdi.extendedweathertitan.helper.JSONWeatherParser;
import com.p92rdi.extendedweathertitan.helper.JsonParser;
import com.p92rdi.extendedweathertitan.model.DailyForecast;
import com.p92rdi.extendedweathertitan.model.Weather;
import com.p92rdi.extendedweathertitan.model.WeatherForecast;

import org.json.JSONException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ForecastActivity extends AppCompatActivity {
    private WeatherForecast weatherForecast;
    private DailyForecast dailyForecast;
    private List<DailyForecast> fillings = new ArrayList<>();
    private ListView lv_forecast;
    private String mCity;

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        lv_forecast = (ListView) findViewById(R.id.lv_forecast);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWeatherForecast();

    }

    private void getWeatherForecast() {
        final String mFinalQuery = mCity;
        Thread mNetworkThread = new Thread(new Runnable() {
            public void run() {
                JSONWeatherParser mJsonWeatherParser = new JSONWeatherParser();
                HttpClient mClient = new HttpClient();
                String mRawJson = mClient.getWeatherData(mFinalQuery);
                if(mRawJson != null && !mRawJson.equals("")){
                    try {
                        weatherForecast = mJsonWeatherParser.getWeather(mRawJson);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("ServiceHandler", "No data received from HTTP request");
                    weatherForecast = new WeatherForecast();
                }
            }
        });
        mNetworkThread.start();
        try {
            mNetworkThread.join();
        } catch (InterruptedException e) {
        }
        displayData();
    }

    private void displayData() {
        //header
        
        //Filling listview
        fillings = generateFiveDaysForecastsList(weatherForecast, weatherForecast.getmDays());
        FillingListAdapter adapter = new FillingListAdapter(this, fillings);
        lv_forecast.setAdapter(adapter);
    }

    public ArrayList<DailyForecast> generateFiveDaysForecastsList(WeatherForecast weatherForecast, List<Long> mDays){
        ArrayList<DailyForecast> fiveDaysForecastsList = new ArrayList<>();
        for(long date : mDays){
            DailyForecast currentDay = new DailyForecast(weatherForecast, date);
            fiveDaysForecastsList.add(currentDay);
        }

        return fiveDaysForecastsList;
    }

}
