package com.p92rdi.extendedweathertitan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ForecastActivity_old extends AppCompatActivity {
    private Mok2 mok;
    private List<Mok2> fillings = new ArrayList<>();
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

    private void displayData() {
        mok = new Mok2();
        fillings = mok.generateArrayList();
        FillingListAdapter adapter = new FillingListAdapter(this, fillings);
        lv_forecast.setAdapter(adapter);
    }
}
