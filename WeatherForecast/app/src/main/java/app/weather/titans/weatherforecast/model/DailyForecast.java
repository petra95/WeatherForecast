package app.weather.titans.weatherforecast.model;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by totha on 2016. 08. 03..
 */
public class DailyForecast {

    private long mDate;
    private String mIconCodeDay;
    private String mIconCodeNight;
    private String mDescriptionDay;
    private String mDescriptionNight;
    private float mTemperatureDay;
    private float mTemperatureNight;

    public DailyForecast(WeatherForecast weatherForecast, long date){
        ArrayList<Forecast> forecastsList = new ArrayList<>(weatherForecast.getmForecastsList());
        mDate = date;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        for(Forecast forecast : forecastsList){

            calendar.setTimeInMillis(forecast.getmDate());
            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
            int currentHour = calendar.get(Calendar.HOUR);

            if( currentDay == day){
                if(currentHour == 3){
                    mIconCodeNight = forecast.getmWeatherCondition().getmIconCode();
                    mDescriptionNight = forecast.getmWeatherCondition().getmDescription();
                    mTemperatureNight = forecast.getmWeatherCondition().getmTemperature();
                }
                else if(currentHour == 15) {
                    mIconCodeDay = forecast.getmWeatherCondition().getmIconCode();
                    mDescriptionDay = forecast.getmWeatherCondition().getmDescription();
                    mTemperatureDay = forecast.getmWeatherCondition().getmTemperature();
                }
            }
        }
    }

    public long getmDate() {
        return mDate;
    }

    public String getmIconCodeDay() {
        return mIconCodeDay;
    }

    public String getmIconCodeNight() {
        return mIconCodeNight;
    }

    public String getmDescriptionDay() {
        return mDescriptionDay;
    }

    public String getmDescriptionNight() {
        return mDescriptionNight;
    }

    public float getmTemperatureDay() {
        return mTemperatureDay;
    }

    public float getmTemperatureNight() {
        return mTemperatureNight;
    }

}
