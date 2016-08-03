package com.p92rdi.extendedweathertitan.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.p92rdi.extendedweathertitan.R;
import com.p92rdi.extendedweathertitan.helper.HttpClient;
import com.p92rdi.extendedweathertitan.helper.JsonParser;
import com.p92rdi.extendedweathertitan.model.Weather;

public class CurrentActivity extends AppCompatActivity {
    private static final String SAVE_CITY_NAME_KEY = "CityName";
    private static final String SAVESLOT1CITYID = "Slot1CityID";
    private static final String SAVESLOT2CITYID = "Slot2CityID";
    private static final String SAVESLOT3CITYID = "Slot3CityID";

    private Menu menu;
    private TableLayout mDataTableLayout;
    private SharedPreferences mSlot1CityNameSharedPreference;
    private String mActualCityNameString = "";
    private String mSlot1TitleString = "";
    private String mSlot2TitleString = "";
    private String mSlot3TitleString = "";
    private HttpClient mClient;
    private Weather mResultWeather;
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

        mClient = new HttpClient();
        mDataTableLayout = (TableLayout) findViewById(R.id.dataTableLayout);
        mSlot1CityNameSharedPreference = getSharedPreferences(SAVESLOT1CITYID, 0);
        mSlot1CityNameSharedPreference = getSharedPreferences(SAVESLOT2CITYID, 0);
        mSlot1CityNameSharedPreference = getSharedPreferences(SAVESLOT3CITYID, 0);

        init();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            String cityName = (String) bundle.get("TEXT");
            Log.e("onCreate", cityName);
            getWeather(cityName);
        }
        //startActivity();
    }

    private void init() {
        tv_city = (TextView) findViewById(R.id.cityTextView);
        tv_degree = (TextView) findViewById(R.id.degreeTextView);
        tv_description = (TextView) findViewById(R.id.descriptionTextView);
        tv_minDeg = (TextView) findViewById(R.id.minDegTextView);
        tv_maxDeg = (TextView) findViewById(R.id.maxDegTextView);
        tv_wind = (TextView) findViewById(R.id.windTextView);
        tv_humidity = (TextView) findViewById(R.id.humidityTextView);
    }

    private void startActivity() {
        mActualCityNameString = getSaveSharedPreference(SAVESLOT1CITYID);
        getWeather(mActualCityNameString);
        mSlot1TitleString = getSaveSharedPreference(SAVESLOT1CITYID);
        mSlot2TitleString = getSaveSharedPreference(SAVESLOT2CITYID);
        mSlot3TitleString = getSaveSharedPreference(SAVESLOT3CITYID);
    }

    private String getSaveSharedPreference(String key) {
        return mSlot1CityNameSharedPreference.getString(key, "");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(SAVE_CITY_NAME_KEY, mActualCityNameString);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mSlot1TitleString = mSlot1CityNameSharedPreference.getString(SAVESLOT1CITYID, "");
        mSlot2TitleString = mSlot1CityNameSharedPreference.getString(SAVESLOT2CITYID, "");
        mSlot3TitleString = mSlot1CityNameSharedPreference.getString(SAVESLOT3CITYID, "");
        getWeather(savedInstanceState.getString(SAVE_CITY_NAME_KEY));
    }

    private void assignWeatherValues(Weather weatherData) {
        if (weatherData.getmIcon() != null) {
            ImageView mImageView = (ImageView) findViewById(R.id.weatherImageView);
            mImageView.setImageBitmap(weatherData.getmIcon());
        }
        String city = weatherData.getmCity().concat(" " + weatherData.getmCountry());
        String degree = String.valueOf(weatherData.getmTemperature() - 273) + " C°";
        String description = weatherData.getmDescription();
        String minDeg = String.valueOf(weatherData.getmTempMin() - 273) + " C°";
        String maxDeg = String.valueOf(weatherData.getmTempMax() - 273) + " C°";
        String wind = String.valueOf(weatherData.getmWind()) + " m/s";
        String huminity = String.valueOf(weatherData.getmHumidity()) + "%";

        tv_city.setText(city);
        tv_degree.setText(degree);
        tv_description.setText(description);
        tv_minDeg.setText(minDeg);
        tv_maxDeg.setText(maxDeg);
        tv_wind.setText(wind);
        tv_humidity.setText(huminity);
    }

    private void saveCityName(SharedPreferences actualSharedPreferenc, String query, String keyString) {
        SharedPreferences.Editor editor1 = actualSharedPreferenc.edit();
        editor1.putString(keyString, query);
        editor1.apply();
    }

    private void getWeather(String query) {
        final String mFinalQuery = query;
        Thread mNetworkThread = new Thread(new Runnable() {
            public void run() {
                JsonParser mWeatherParser = new JsonParser();
                String mRawJson = mClient.getWeatherData(mFinalQuery);
                if(mRawJson != null && !mRawJson.equals("")){
                    mResultWeather = mWeatherParser.processWeatherFromJson(mRawJson);
                    Bitmap test = mClient.getImage(mResultWeather.getmIconCode());
                    mResultWeather.setmIcon(test);
                } else {
                    Log.e("ServiceHandler", "No data received from HTTP request");
                    mResultWeather = new Weather();
                }
            }
        });
        mNetworkThread.start();
        try {
            mNetworkThread.join();
        } catch (InterruptedException e) {
        }
        mActualCityNameString = mResultWeather.getmCity();
        assignWeatherValues(mResultWeather);
        mDataTableLayout.setVisibility(View.VISIBLE);
    }

}
