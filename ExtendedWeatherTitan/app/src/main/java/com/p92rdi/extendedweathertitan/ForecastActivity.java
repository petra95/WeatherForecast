package com.p92rdi.extendedweathertitan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.json.JSONException;

import com.p92rdi.extendedweathertitan.helper.HttpClient;
import com.p92rdi.extendedweathertitan.helper.JSONWeatherParser;
import com.p92rdi.extendedweathertitan.model.DailyForecast;
import com.p92rdi.extendedweathertitan.model.WeatherForecast;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ForecastActivity extends AppCompatActivity {
    private WeatherForecast weatherForecast;
    private DailyForecast dailyForecast;
    private List<DailyForecast> fillings = new ArrayList<>();
    private ListView lv_forecast;
    private String mCity = "Cegled";

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

        JSONWeatherForecastTask task = new JSONWeatherForecastTask();
        task.execute(new String[]{mCity});
    }

    private class JSONWeatherForecastTask extends AsyncTask<String, Void, WeatherForecast> {

        @Override
        protected WeatherForecast doInBackground(String... params) {
            Looper.prepare();
            WeatherForecast weatherForecast = new WeatherForecast();
            String data = ((new HttpClient()).getWeatherData(params[0]));
            if(data != null && !data.equals("")) {
                try {
                    try {
                        weatherForecast = JSONWeatherParser.getWeather(data);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "No data received from HTTP request");
                weatherForecast = new WeatherForecast();
            }
            return weatherForecast;
        }

        @Override
        protected void onPostExecute(WeatherForecast weatherForecast) {
            super.onPostExecute(weatherForecast);
            ForecastActivity.this.weatherForecast = weatherForecast;
            displayData();
            /*
            if (weather.iconData != null && weather.iconData.length > 0) {
                Bitmap img = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length);
                imgView.setImageBitmap(img);
            }

            cityText.setText(weather.location.getCity() + "," + weather.location.getCountry());
            condDescr.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
            temp.setText("" + Math.round((weather.temperature.getTemp() - 273.15)) + "�C");
            hum.setText("" + weather.currentCondition.getHumidity() + "%");
            press.setText("" + weather.currentCondition.getPressure() + " hPa");
            windSpeed.setText("" + weather.wind.getSpeed() + " mps");
            windDeg.setText("" + weather.wind.getDeg() + "�");*/

        }

    }

    @Override
    protected void onResume() {
        super.onResume();


    }
    
    private void displayData() {
        //header

        //Filling listview
        Log.e("displayData()", weatherForecast.getmDays().toString());
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
