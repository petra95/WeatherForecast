package com.p92rdi.extendedweathertitan.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.p92rdi.extendedweathertitan.R;
import com.p92rdi.extendedweathertitan.helper.SharedPrefKeys;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends MenuBarActivity implements SensorEventListener {

    ListView historyListView;

    private TextView environmentTV;
    private TextView batteryTV;
    private SensorManager sensorManager;
    private Sensor temperatureSensor;

    private BroadcastReceiver batteryBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            float batteryTemp = (float)(intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0))/10;
            batteryTV.setText(String.format(Locale.getDefault(), "%.1f",batteryTemp) + " °C");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationMenu(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearHistoryOnClick(view);
            }
        });

        historyListView = (ListView) findViewById(R.id.historyListView);

        environmentTV = (TextView) findViewById(R.id.environmentTV);
        batteryTV = (TextView) findViewById(R.id.batteryTV);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if(temperatureSensor == null){
            environmentTV.setText(R.string.not_available);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        registerReceiver(batteryBroadcastReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        loadHistory();
        Toast.makeText(this, "searchedCities: " + searchedCities.size(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
        unregisterReceiver(batteryBroadcastReceiver);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            environmentTV.setText(String.format(Locale.getDefault(), "%.1f", event.values[0]));
        }
    }

    public void loadHistory(){
        final String[] searchedCities = getSearchedCitiesInArray();
        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, searchedCities[position], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ForecastActivity.class);
                intent.putExtra(SharedPrefKeys.SEARCH_KEY, searchedCities[position]);
                startActivity(intent);
            }
        });
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.history_row,R.id.rowTextView,searchedCities);
        historyListView.setAdapter(adapter);
    }

    public void clearHistoryOnClick(View view) {

        searchedCities.clear();
        saveSearchedCityNames();
        loadHistory();
    }

    public String[] getSearchedCitiesInArray(){
        retrieveSearchedCitiesNames();
        String[] searchedCitiesArray = new String[searchedCities.size()];
        searchedCitiesArray = searchedCities.toArray(searchedCitiesArray);
        return searchedCitiesArray;
    }

    private void retrieveSearchedCitiesNames(){
        SharedPreferences editor = getSharedPreferences(SharedPrefKeys.HISTORY, MODE_PRIVATE);
        Set<String> set = editor.getStringSet("searchedCities", null);
        if (set != null) {
            searchedCities = new ArrayList<>(set);
        }
    }
}