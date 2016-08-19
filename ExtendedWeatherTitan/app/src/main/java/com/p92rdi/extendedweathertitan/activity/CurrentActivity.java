package com.p92rdi.extendedweathertitan.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import com.p92rdi.extendedweathertitan.R;
import com.p92rdi.extendedweathertitan.helper.HttpClient;
import com.p92rdi.extendedweathertitan.helper.JSONWeatherParser;
import com.p92rdi.extendedweathertitan.model.WeatherForecastOneDay;
import org.json.JSONException;
import java.text.ParseException;
import com.p92rdi.extendedweathertitan.helper.SharedPrefKeys;

public class CurrentActivity extends AbstractActivity {

    private TableLayout mDataTableLayout;
    protected WeatherForecastOneDay mWeatherForecastOneDay;
    private TextView tv_city;
    private TextView tv_degree;
    private TextView tv_description;
    private TextView tv_minDeg;
    private TextView tv_maxDeg;
    private TextView tv_wind;
    private TextView tv_humidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current);
        navigationMenu(this);
        init();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            mActualCity = (String) bundle.get(SharedPrefKeys.SEARCH_KEY);
            getWeatherForecastOneDay();
        }
    }

    /**
     * Initializes the layout and the text views in which the datas
     * will be displayed describing the current weather.
     */
    private void init() {
        tv_city = (TextView) findViewById(R.id.cityTextView);
        tv_degree = (TextView) findViewById(R.id.degreeTextView);
        tv_description = (TextView) findViewById(R.id.descriptionTextView);
        tv_minDeg = (TextView) findViewById(R.id.minDegTextView);
        tv_maxDeg = (TextView) findViewById(R.id.maxDegTextView);
        tv_wind = (TextView) findViewById(R.id.windTextView);
        tv_humidity = (TextView) findViewById(R.id.humidityTextView);
        mDataTableLayout = (TableLayout) findViewById(R.id.dataTableLayout);
    }

    /**
     * Sets the displayed text views' texts to the values from a WeatherForeCastOneDay object.
     * @param weatherForecastOneDay The object from which the values are assigned.
     */
    private void assignWeatherValues(WeatherForecastOneDay weatherForecastOneDay) {
        if (weatherForecastOneDay.getmIcon() != null) {
            ImageView mImageView = (ImageView) findViewById(R.id.weatherImageView);
            mImageView.setImageBitmap(weatherForecastOneDay.getmIcon());
        }

        String city = weatherForecastOneDay.getmCity().concat(" " + weatherForecastOneDay.getmCountry());
        String degree = String.valueOf(weatherForecastOneDay.getmTemperature()) + getResources().getString(R.string.celsius);
        String description = weatherForecastOneDay.getmDescription();
        String minDeg = String.valueOf(weatherForecastOneDay.getmTempMin()) + getResources().getString(R.string.celsius);
        String maxDeg = String.valueOf(weatherForecastOneDay.getmTempMax()) + getResources().getString(R.string.celsius);
        String wind = String.valueOf(weatherForecastOneDay.getmWind()) + getResources().getString(R.string.windms);
        String humidity = String.valueOf(weatherForecastOneDay.getmHumidity()) + getResources().getString(R.string.percent);

        tv_city.setText(city);
        tv_city.setMovementMethod(new ScrollingMovementMethod());
        tv_degree.setText(degree);
        tv_description.setText(description);
        tv_minDeg.setText(minDeg);
        tv_maxDeg.setText(maxDeg);
        tv_wind.setText(wind);
        tv_humidity.setText(humidity);
    }

    private class JSONWeatherForecastTask extends AsyncTask<String, Void, WeatherForecastOneDay> {

        @Override
        protected WeatherForecastOneDay doInBackground(String... params) {
            WeatherForecastOneDay weatherForecastOneDay = new WeatherForecastOneDay();
            HttpClient httpClient = new HttpClient("weather");
            String mRawJson = httpClient.getWeatherData(params[0]);
            if(mRawJson != null && !mRawJson.equals("")) {
                try {
                    try {
                        weatherForecastOneDay = JSONWeatherParser.getWeatherForecastOneDay(mRawJson);
                        if(weatherForecastOneDay != null) {
                            Bitmap test = httpClient.getImage(weatherForecastOneDay.getmIconCode());
                            weatherForecastOneDay.setmIcon(test);
                        }
                        Log.d("ServiceHandler", "weatherForecastFiveDays: " + weatherForecastOneDay);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("ServiceHandler", "No data received from HTTP request");
                weatherForecastOneDay = new WeatherForecastOneDay();
            }
            return weatherForecastOneDay;
        }

        @Override
        protected void onPostExecute(WeatherForecastOneDay weatherForecastOneDay) {
            super.onPostExecute(weatherForecastOneDay);
            if(weatherForecastOneDay != null) {
                CurrentActivity.this.mWeatherForecastOneDay = weatherForecastOneDay;
                assignWeatherValues(CurrentActivity.this.mWeatherForecastOneDay);
                mDataTableLayout.setVisibility(View.VISIBLE);
                addToSearchedCities(weatherForecastOneDay.getmCity().concat(" " + weatherForecastOneDay.getmCountry()));
            }else{
                displayNotFoundCity();
            }
        }
    }

    /**
     * Sets the city's text view to city not found.
     */
    private void displayNotFoundCity() {
        tv_city.setText(R.string.city_not_found);
    }

    /**
     * Formats the input string with replacing " " to %20 and
     * starts the http calls.
     */
    public void getWeatherForecastOneDay(){
        mActualCity = mActualCity.replace(" ", "%20");
        if(!mActualCity.equals("")) {
            new JSONWeatherForecastTask().execute(mActualCity);
        }
    }

    @Override
    public void saveCity(final int index, final SharedPreferences sharedPref) {
        SharedPreferences.Editor editor = sharedPref.edit();
        String cityAndCountry = mWeatherForecastOneDay.getmCity().concat(" " + mWeatherForecastOneDay.getmCountry());
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
