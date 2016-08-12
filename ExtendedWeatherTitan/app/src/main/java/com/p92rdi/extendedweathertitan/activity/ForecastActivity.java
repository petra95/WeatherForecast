package com.p92rdi.extendedweathertitan.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
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

public class ForecastActivity extends AbstractActivity {

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
            getWeatherForecastFiveDays();
        }
    }

    private class JSONWeatherForecastTask extends AsyncTask<String, Void, WeatherForecastFiveDays> {

        @Override
        protected WeatherForecastFiveDays doInBackground(String... params) {
            WeatherForecastFiveDays weatherForecastFiveDays = new WeatherForecastFiveDays();
            String mRawJson = ((new HttpClient("forecast")).getWeatherData(params[0]));
            if(mRawJson != null && !mRawJson.equals("")) {
                try {
                    try {
                        weatherForecastFiveDays = JSONWeatherParser.getWeatherForecastFiveDays(mRawJson);
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
            if(weatherForecastFiveDays != null) {
                ForecastActivity.this.weatherForecastFiveDays = weatherForecastFiveDays;
                displayData();
                addToSearchedCities(weatherForecastFiveDays.getmLocation().getmCity().concat(" " + weatherForecastFiveDays.getmLocation().getmCountry()));
            }else{
                displayNotFoundCity();
            }
        }
    }

    private void displayNotFoundCity() {
        TextView tvCity = (TextView) findViewById(R.id.tvCity);
        tvCity.setText(R.string.not_found_city);
    }

    private void displayData() {
        TextView tvCity = (TextView) findViewById(R.id.tvCity);
        TextView tvGps = (TextView) findViewById(R.id.tvGPS);
        tvCity.setText(weatherForecastFiveDays.getmLocation().getmCity().concat(" " + weatherForecastFiveDays.getmLocation().getmCountry()));
        tvCity.setMovementMethod(new ScrollingMovementMethod());

        String lon = String.valueOf(weatherForecastFiveDays.getmLocation().getmLongitude()).concat(getResources().getString(R.string.degree));
        String lat = String.valueOf(weatherForecastFiveDays.getmLocation().getmLatitude()).concat(getResources().getString(R.string.degree));
        String gps = "( Lon: " + lon + ", Lat: " + lat + " )";

        tvGps.setText(gps);
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
        new JSONWeatherForecastTask().execute(mActualCity);
    }

    public void getWeatherForecastFiveDays(){
        Log.d("ServiceHandler", "cityName: " + mActualCity);
        mActualCity = mActualCity.replace(" ", "%20");
        Log.d("ServiceHandler", "cityName: " + mActualCity);
        if(!mActualCity.equals("")) {
            new JSONWeatherForecastTask().execute(mActualCity);
        }
    }
}