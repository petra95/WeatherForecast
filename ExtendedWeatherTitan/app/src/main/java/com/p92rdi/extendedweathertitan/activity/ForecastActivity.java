package com.p92rdi.extendedweathertitan.activity;

import android.content.Intent;
import android.content.SharedPreferences;
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

    private WeatherForecastFiveDays mWeatherForecastFiveDays;
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
            Log.d("ServiceHandler", "JSONWeatherForecastTask: " + mActualCity);
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
                ForecastActivity.this.mWeatherForecastFiveDays = weatherForecastFiveDays;
                displayData();
                addToSearchedCities(weatherForecastFiveDays.getmLocation().getmCity().concat(" " + weatherForecastFiveDays.getmLocation().getmCountry()));
            }else{
                displayNotFoundCity();
            }
        }
    }
    /**
     * Sets the city's text view to city not found.
     */
    private void displayNotFoundCity() {
        TextView tvCity = (TextView) findViewById(R.id.tvCity);
        tvCity.setText(R.string.city_not_found);
    }
    /**
     * Sets the displayed text views' texts to the values from a WeatherForeCastFiveDay object.
     */
    private void displayData() {
        TextView tv_City = (TextView) findViewById(R.id.tvCity);
        TextView tv_GpsCoords = (TextView) findViewById(R.id.tvGPS);
        tv_City.setText(mWeatherForecastFiveDays.getmLocation().getmCity().concat(" " + mWeatherForecastFiveDays.getmLocation().getmCountry()));
        tv_City.setMovementMethod(new ScrollingMovementMethod());

        String lon = String.valueOf(mWeatherForecastFiveDays.getmLocation().getmLongitude()).concat(getResources().getString(R.string.degree));
        String lat = String.valueOf(mWeatherForecastFiveDays.getmLocation().getmLatitude()).concat(getResources().getString(R.string.degree));
        String gpsCoords = "( Lon: " + lon + ", Lat: " + lat + " )";

        tv_GpsCoords.setText(gpsCoords);
        fillings = generateFiveDaysForecastsList(mWeatherForecastFiveDays, mWeatherForecastFiveDays.getmDays());
        FillingListAdapter adapter = new FillingListAdapter(this, fillings);
        lv_forecast.setAdapter(adapter);
    }

    /**
     * Fills an array list with the appropriate datas gathered from a WeatherForeCastFiveDays object.
     * @param weatherForecastFiveDays The object from which the datas are gathered.
     * @param mDays All the days gathered from the JSON parsing.
     * @return The filled array list.
     */
    public ArrayList<DailyForecast> generateFiveDaysForecastsList(WeatherForecastFiveDays weatherForecastFiveDays, List<Long> mDays){
        ArrayList<DailyForecast> fiveDaysForecastsList = new ArrayList<>();
        for(long date : mDays){
            DailyForecast currentDay = new DailyForecast(weatherForecastFiveDays, date);
            fiveDaysForecastsList.add(currentDay);
        }
        return fiveDaysForecastsList;
    }

    /**
     * Formats the input string with replacing " " to %20 and
     * starts the http calls.
     */
    public void getWeatherForecastFiveDays(){
        mActualCity = mActualCity.replace(" ", "%20");
        if(!mActualCity.equals("")) {
            new JSONWeatherForecastTask().execute(mActualCity);
        }
    }

    @Override
    public void saveCity(final int index, final SharedPreferences sharedPref) {
        SharedPreferences.Editor editor = sharedPref.edit();
        String cityAndCountry = mWeatherForecastFiveDays.getmLocation().getmCity().concat(" " + mWeatherForecastFiveDays.getmLocation().getmCountry());
        switch (index) {
            case 0:
                editor.putString(SharedPrefKeys.SLOT1_KEY, cityAndCountry);
                break;
            case 1:
                editor.putString(SharedPrefKeys.SLOT2_KEY, cityAndCountry);
                break;
            case 2:
                editor.putString(SharedPrefKeys.SLOT3_KEY, cityAndCountry);
                break;
        }
        editor.apply();
    }
}