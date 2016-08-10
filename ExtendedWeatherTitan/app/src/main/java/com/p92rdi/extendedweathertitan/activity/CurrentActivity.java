package com.p92rdi.extendedweathertitan.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.p92rdi.extendedweathertitan.R;
import com.p92rdi.extendedweathertitan.helper.HttpClient;
import com.p92rdi.extendedweathertitan.helper.JsonParser;
import com.p92rdi.extendedweathertitan.helper.SharedPrefKeys;
import com.p92rdi.extendedweathertitan.model.WeatherForecastOneDay;

public class CurrentActivity extends MenuBarActivity {

    private TableLayout mDataTableLayout;
    private HttpClient mClient;
    private WeatherForecastOneDay mResultWeatherForecastOneDay;
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
            getWeather(mActualCity);
        }
    }

    private void init() {
        tv_city = (TextView) findViewById(R.id.cityTextView);
        tv_degree = (TextView) findViewById(R.id.degreeTextView);
        tv_description = (TextView) findViewById(R.id.descriptionTextView);
        tv_minDeg = (TextView) findViewById(R.id.minDegTextView);
        tv_maxDeg = (TextView) findViewById(R.id.maxDegTextView);
        tv_wind = (TextView) findViewById(R.id.windTextView);
        tv_humidity = (TextView) findViewById(R.id.humidityTextView);
        mClient = new HttpClient("weather");
        mDataTableLayout = (TableLayout) findViewById(R.id.dataTableLayout);
    }

    private void assignWeatherValues(WeatherForecastOneDay weatherForecastOneDayData) {
        if (weatherForecastOneDayData.getmIcon() != null) {
            ImageView mImageView = (ImageView) findViewById(R.id.weatherImageView);
            mImageView.setImageBitmap(weatherForecastOneDayData.getmIcon());
        }

        String city = weatherForecastOneDayData.getmCity().concat(" " + weatherForecastOneDayData.getmCountry());
        String degree = String.valueOf(weatherForecastOneDayData.getmTemperature()) + " °C";
        String description = weatherForecastOneDayData.getmDescription();
        String minDeg = String.valueOf(weatherForecastOneDayData.getmTempMin()) + " °C";
        String maxDeg = String.valueOf(weatherForecastOneDayData.getmTempMax()) + " °C";
        String wind = String.valueOf(weatherForecastOneDayData.getmWind()) + " m/s";
        String humidity = String.valueOf(weatherForecastOneDayData.getmHumidity()) + "%";

        tv_city.setText(city);
        tv_city.setMovementMethod(new ScrollingMovementMethod());
        tv_degree.setText(degree);
        tv_description.setText(description);
        tv_minDeg.setText(minDeg);
        tv_maxDeg.setText(maxDeg);
        tv_wind.setText(wind);
        tv_humidity.setText(humidity);
    }

    private void getWeather(String query) {
        final String mFinalQuery = query.replace(" ", "%20");
        Thread mNetworkThread = new Thread(new Runnable() {
            public void run() {
                JsonParser mWeatherParser = new JsonParser();
                String mRawJson = mClient.getWeatherData(mFinalQuery);
                if(mRawJson != null && !mRawJson.equals("")){
                    mResultWeatherForecastOneDay = mWeatherParser.processWeatherFromJson(mRawJson);
                    Bitmap test = mClient.getImage(mResultWeatherForecastOneDay.getmIconCode());
                    Log.e("ServiceHandler", "mResultWeatherForecastOneDay.getmIconCode(): "+ mResultWeatherForecastOneDay.getmIconCode());
                    mResultWeatherForecastOneDay.setmIcon(test);
                } else {
                    Log.e("ServiceHandler", "No data received from HTTP request");
                    mResultWeatherForecastOneDay = new WeatherForecastOneDay();
                }
            }
        });

        mNetworkThread.start();
        try {
            mNetworkThread.join();
        } catch (InterruptedException e) {
        }
        assignWeatherValues(mResultWeatherForecastOneDay);
        mDataTableLayout.setVisibility(View.VISIBLE);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        mActualCity = savedInstanceState.getString(SharedPrefKeys.SEARCH_KEY);
        getWeather(mActualCity);
    }
    @Override
    public void saveClickAction(){
        saveCityDialog(getSharedPreferences(
                        getString(R.string.preference_file_key_current), Context.MODE_PRIVATE));
    }
}
