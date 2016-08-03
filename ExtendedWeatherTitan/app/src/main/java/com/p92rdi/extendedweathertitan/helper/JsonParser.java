package com.p92rdi.extendedweathertitan.helper;


import com.p92rdi.extendedweathertitan.model.Weather;

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

    public Weather processWeatherFromJson(String mRawJson) {
            try {
                JSONObject jObj = new JSONObject(mRawJson);
                Weather mProducedWeather = new Weather();

                mProducedWeather.setmCityId(jObj.getInt(TAG_ID));
                mProducedWeather.setmCountry(jObj.getJSONObject(TAG_SYS).getString(TAG_COUNTRY));
                mProducedWeather.setmDescription(jObj.getJSONArray(TAG_WEATHER).getJSONObject(0).getString(TAG_DESCRIPTION));
                mProducedWeather.setmIconCode(jObj.getJSONArray(TAG_WEATHER).getJSONObject(0).getString(TAG_ICON).concat(".png"));
                mProducedWeather.setmTemperature(jObj.getJSONObject(TAG_MAIN).getInt(TAG_TEMPERATURE));
                mProducedWeather.setmHumidity(jObj.getJSONObject(TAG_MAIN).getInt(TAG_HUMIDITY));
                mProducedWeather.setmTempMin(jObj.getJSONObject(TAG_MAIN).getInt(TAG_TEMP_MIN));
                mProducedWeather.setmTempMax(jObj.getJSONObject(TAG_MAIN).getInt(TAG_TEMP_MAX));
                mProducedWeather.setmWind(jObj.getJSONObject(TAG_WIND).getInt(TAG_WIND_SPEED));
                mProducedWeather.setmCity(jObj.getString(TAG_NAME));

                return mProducedWeather;
            }
            catch (JSONException e) {
                e.printStackTrace();
                return new Weather();
            }
        }
    }
