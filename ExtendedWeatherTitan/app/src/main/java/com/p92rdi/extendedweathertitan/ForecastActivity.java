package com.p92rdi.extendedweathertitan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.p92rdi.extendedweathertitan.model.DailyForecast;

import java.util.ArrayList;
import java.util.List;

public class ForecastActivity extends AppCompatActivity {
    private DailyForecast dailyForecast;
    private List<DailyForecast> fillings = new ArrayList<>();
    private ListView lv_forecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        lv_forecast = (ListView) findViewById(R.id.lv_forecast);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayData();
    }

    //weatherForecast honnan ???
    private void displayData() {
        dailyForecast = new DailyForecast(weatherForecast, date);
        fillings = dailyForecast.generateFiveDaysForecastsList(weatherForecast, weatherForecast.getmDays());
        FillingListAdapter adapter = new FillingListAdapter(this, fillings);
        lv_forecast.setAdapter(adapter);
    }
}
