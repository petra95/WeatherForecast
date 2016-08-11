package com.p92rdi.extendedweathertitan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.p92rdi.extendedweathertitan.helper.FillingListAdapter;
import com.p92rdi.extendedweathertitan.R;
import com.p92rdi.extendedweathertitan.helper.HttpClient;
import com.p92rdi.extendedweathertitan.helper.JSONWeatherParser;
import com.p92rdi.extendedweathertitan.helper.SharedPrefKeys;
import com.p92rdi.extendedweathertitan.model.DailyForecast;
import com.p92rdi.extendedweathertitan.model.WeatherForecastFiveDays;


import org.json.JSONException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class ForecastActivity extends MenuBarActivity {

    private WeatherForecastFiveDays weatherForecastFiveDays;
    private List<DailyForecast> fillings;
    private ListView lv_forecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        navigationMenu(this);

        lv_forecast = (ListView) findViewById(R.id.lv_forecast);
        fillings = new ArrayList<>();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {
            mActualCity = (String) bundle.get(SharedPrefKeys.SEARCH_KEY);
            addToSearchedCities(mActualCity);
            getWeatherForecastFiveDays();
        }
    }

    private class JSONWeatherForecastTask extends AsyncTask<String, Void, WeatherForecastFiveDays> {

        @Override
        protected WeatherForecastFiveDays doInBackground(String... params) {
            WeatherForecastFiveDays weatherForecastFiveDays = new WeatherForecastFiveDays();
            String mRawJson = ((new HttpClient("forecast")).getWeatherData(params[0]));
            Log.d("ServiceHandler", "data: " + mRawJson);
            if(mRawJson != null && !mRawJson.equals("")) {
                try {
                    try {
                        weatherForecastFiveDays = JSONWeatherParser.getWeatherForecastFiveDays(mRawJson);
                        Log.d("ServiceHandler", "weatherForecastFiveDays: " + weatherForecastFiveDays);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("ServiceHandler", "No data received from HTTP request");
                weatherForecastFiveDays = new WeatherForecastFiveDays();
            }
            return weatherForecastFiveDays;
        }

        @Override
        protected void onPostExecute(WeatherForecastFiveDays weatherForecastFiveDays) {
            super.onPostExecute(weatherForecastFiveDays);
            Log.d("ServiceHandler", "weatherForecastFiveDays: " + weatherForecastFiveDays);
            if(weatherForecastFiveDays != null) {
                ForecastActivity.this.weatherForecastFiveDays = weatherForecastFiveDays;
                displayData();
            }
        }
    }

    private void displayData() {
        TextView tvCity = (TextView) findViewById(R.id.tvCity);
        TextView tvGpsLon = (TextView) findViewById(R.id.tvGPS_LON);
        TextView tvGpsLat = (TextView) findViewById(R.id.tvGPS_LAT);
        tvCity.setText(weatherForecastFiveDays.getmLocation().getmCity());
        tvCity.setMovementMethod(new ScrollingMovementMethod());

        tvGpsLon.setText(String.valueOf(weatherForecastFiveDays.getmLocation().getmLongitude()));
        tvGpsLat.setText(String.valueOf(weatherForecastFiveDays.getmLocation().getmLatitude()));

        fillings = generateFiveDaysForecastsList(weatherForecastFiveDays, weatherForecastFiveDays.getmDays());
        FillingListAdapter adapter = new FillingListAdapter(this, fillings);
        lv_forecast.setAdapter(adapter);
    }

    public ArrayList<DailyForecast> generateFiveDaysForecastsList(WeatherForecastFiveDays weatherForecastFiveDays, List<Long> mDays){
        ArrayList<DailyForecast> fiveDaysForecastsList = new ArrayList<>();
        for(long date : mDays){
            DailyForecast currentDay = new DailyForecast(weatherForecastFiveDays, date);
            fiveDaysForecastsList.add(currentDay);
        }

        return fiveDaysForecastsList;
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mActualCity = savedInstanceState.getString(SharedPrefKeys.SEARCH_KEY);

        if(isNetworkAvailable()){
            new JSONWeatherForecastTask().execute(mActualCity);
        } else{
            Toast.makeText(this, "Internet is not available!", Toast.LENGTH_LONG).show();
        }
    }

    public void getWeatherForecastFiveDays(){
        mActualCity = mActualCity.replace(" ", "");
        Log.d("ServiceHandler", "cityName: " + mActualCity);
        if(!mActualCity.equals("")) {
            new JSONWeatherForecastTask().execute(mActualCity);
        }
    } 

    @Override
    public void saveClickAction(){
        saveCityDialog(getSharedPreferences(getString(R.string.preference_file_key_forecast), Context.MODE_PRIVATE));
    }
}