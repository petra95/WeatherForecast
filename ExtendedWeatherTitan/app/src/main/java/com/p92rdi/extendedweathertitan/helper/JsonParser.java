package com.p92rdi.extendedweathertitan.helper;


import com.p92rdi.extendedweathertitan.model.WeatherForecastOneDay;

import org.json.JSONObject;
import org.json.JSONException;

public class JsonParser {
    private static final String TAG_ID = "id";
    private static final String TAG_SYS = "sys";
    private static final String TAG_COUNTRY = "country";
    private static final String TAG_WEATHER = "weather";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_ICON = "icon";
    private static final String TAG_MAIN = "main";
    private static final String TAG_TEMPERATURE ="temp";
    private static final String TAG_HUMIDITY = "humidity";
    private static final String TAG_TEMP_MIN = "temp_min";
    private static final String TAG_TEMP_MAX= "temp_max";
    private static final String TAG_WIND = "wind";
    private static final String TAG_WIND_SPEED = "speed";
    private static final String TAG_NAME = "name";

    public WeatherForecastOneDay processWeatherFromJson(String mRawJson) {
            try {
                JSONObject jObj = new JSONObject(mRawJson);
                WeatherForecastOneDay mProducedWeatherForecastOneDay = new WeatherForecastOneDay();

                mProducedWeatherForecastOneDay.setmCityId(jObj.getInt(TAG_ID));
                mProducedWeatherForecastOneDay.setmCountry(jObj.getJSONObject(TAG_SYS).getString(TAG_COUNTRY));
                mProducedWeatherForecastOneDay.setmDescription(jObj.getJSONArray(TAG_WEATHER).getJSONObject(0).getString(TAG_DESCRIPTION));
                mProducedWeatherForecastOneDay.setmIconCode(jObj.getJSONArray(TAG_WEATHER).getJSONObject(0).getString(TAG_ICON));
                mProducedWeatherForecastOneDay.setmTemperature(jObj.getJSONObject(TAG_MAIN).getInt(TAG_TEMPERATURE));
                mProducedWeatherForecastOneDay.setmHumidity(jObj.getJSONObject(TAG_MAIN).getInt(TAG_HUMIDITY));
                mProducedWeatherForecastOneDay.setmTempMin(jObj.getJSONObject(TAG_MAIN).getInt(TAG_TEMP_MIN));
                mProducedWeatherForecastOneDay.setmTempMax(jObj.getJSONObject(TAG_MAIN).getInt(TAG_TEMP_MAX));
                mProducedWeatherForecastOneDay.setmWind(jObj.getJSONObject(TAG_WIND).getInt(TAG_WIND_SPEED));
                mProducedWeatherForecastOneDay.setmCity(jObj.getString(TAG_NAME));

                return mProducedWeatherForecastOneDay;
            }
            catch (JSONException e) {
                e.printStackTrace();
                return new WeatherForecastOneDay();
            }
    }
}
