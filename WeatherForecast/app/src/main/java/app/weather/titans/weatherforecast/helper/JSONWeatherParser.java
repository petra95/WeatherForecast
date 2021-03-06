package app.weather.titans.weatherforecast.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.util.ArrayList;
import app.weather.titans.weatherforecast.model.Clouds;
import app.weather.titans.weatherforecast.model.Forecast;
import app.weather.titans.weatherforecast.model.Location;
import app.weather.titans.weatherforecast.model.WeatherCondition;
import app.weather.titans.weatherforecast.model.WeatherForecast;
import app.weather.titans.weatherforecast.model.Wind;

/**
 * Created by totha on 2016. 08. 03..
 */
public class JSONWeatherParser {

    public static WeatherForecast getWeather(String data) throws JSONException, ParseException {

        JSONObject jRoot = new JSONObject(data);

        WeatherForecast weatherForecast = new WeatherForecast();
        Location location = new Location();
        ArrayList<Forecast> forecasts = new ArrayList<>();

        JSONObject jCity = getObject("city", jRoot);
        JSONObject jCoord = getObject("coord", jCity);

        location.setmLatitude(getFloat("lat", jCoord));
        location.setmLongitude(getFloat("lon", jCoord));
        location.setmCountry(getString("country", jCity));
        location.setmCity(getString("name", jCity));
        location.setmCityId(getInt("id", jCity));

        JSONArray lists = jRoot.getJSONArray("list");

        for(int i = 0; i < lists.length(); ++i)
        {
            JSONObject jForecast = lists.getJSONObject(i);
            //if notnull jForecast
            Forecast forecast = new Forecast();
            WeatherCondition weatherCondition = new WeatherCondition();
            Clouds clouds = new Clouds();
            Wind wind = new Wind();

            JSONObject jDetails = getObject("main", jForecast);
            JSONArray jWeatherList = jForecast.getJSONArray("weather");

            if(!jForecast.isNull("rain")) {
                JSONObject jRain = getObject("rain", jForecast);
                weatherCondition.setmRain(getFloat("3h", jRain));
            }

            JSONObject jClouds = getObject("clouds", jForecast);
            JSONObject jWind = getObject("wind", jForecast);

            weatherCondition.setmTemperature(getFloat("temp", jDetails));
            weatherCondition.setmTempMax(getFloat("temp_max", jDetails));
            weatherCondition.setmTempMin(getFloat("temp_min", jDetails));
            weatherCondition.setmPressure(getFloat("pressure", jDetails));
            weatherCondition.setmHumidity(getInt("humidity", jDetails));

            JSONObject jWeatherO = jWeatherList.getJSONObject(0);
            weatherCondition.setmDescription(getString("description", jWeatherO));
            weatherCondition.setmIconCode(getString("icon", jWeatherO));


            clouds.setmPercent(getInt("all", jClouds));

            wind.setmSpeed(getFloat("speed", jWind));
            wind.setmDeg(getFloat("deg", jWind));

            forecast.setmDate((long) (getFloat("dt", jForecast)));
            forecast.setmWeatherCondition(weatherCondition);
            forecast.setmClouds(clouds);
            forecast.setmWind(wind);


            forecasts.add(forecast);
        }

        weatherForecast.setmLocation(location);
        weatherForecast.setmForecastsList(forecasts);

        return weatherForecast;
    }


    private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        try{
            return jObj.getString(tagName);
        }catch(Exception e){
            return "";
        }

    }

    private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
        try{
            return (float) jObj.getDouble(tagName);
        }catch(Exception e){
            return 0;
        }
    }

    private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
        try{
            return jObj.getInt(tagName);
        }catch(Exception e){
            return 0;
        }
    }

}
