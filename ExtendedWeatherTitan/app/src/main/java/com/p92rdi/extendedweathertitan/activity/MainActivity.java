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
import java.util.Collections;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AbstractActivity implements SensorEventListener {

    ListView historyListView;

    private TextView tv_environment;
    private TextView tv_battery;
    private SensorManager sensorManager;
    private Sensor temperatureSensor;

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

        tv_environment = (TextView) findViewById(R.id.environmentTV);
        tv_battery = (TextView) findViewById(R.id.batteryTV);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if(temperatureSensor == null){
            tv_environment.setText(getResources().getString(R.string.environment_temp).concat(" " + getResources().getString(R.string.not_available)));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        registerReceiver(batteryBroadcastReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        loadHistory();
    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
        unregisterReceiver(batteryBroadcastReceiver);
    }

    /**
     * Queries and sets the battery's text view to the appropriate data.
     */
    private BroadcastReceiver batteryBroadcastReceiver =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            float batteryTemp = (float)(intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0))/10;
            tv_battery.setText(getResources().getString(R.string.battery_temp).concat(" " + String.format(Locale.getDefault(), "%.1f",batteryTemp) + getResources().getString(R.string.celsius)));
        }
    };

    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    /**
     * Sets the environment temperature's text view.
     * @param event The sensonr's event
     */
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            tv_environment.setText(getResources().getString(R.string.environment_temp).concat(" " + String.format(Locale.getDefault(), "%.1f", event.values[0]) + getResources().getString(R.string.celsius)));
        }
    }

    /**
     * Creates the load history with click listener and fills its view
     * with the searched cities.
     */
    public void loadHistory(){
        final String[] searchedCities = getSearchedCitiesInArray();
        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isNetworkAvailable()) {
                    Intent intent = new Intent(MainActivity.this, ForecastActivity.class);
                    String cityName = removeLastWord(searchedCities[position]);
                    intent.putExtra(SharedPrefKeys.SEARCH_KEY, cityName);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.internet), Toast.LENGTH_LONG).show();
                }
            }
        });
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.history_row,R.id.rowTextView,searchedCities);
        historyListView.setAdapter(adapter);
    }

    /**
     * Clears the browser history when clicked.
     * @param view The clickable button.
     */
    public void clearHistoryOnClick(View view) {
        mSearchedCities.clear();
        saveSearchedCityNames();
        loadHistory();
    }

    /**
     * Retrieves the searched cities in an array.
     * @return A string array with the searched cities.
     */
    public String[] getSearchedCitiesInArray(){
        retrieveSearchedCitiesNames();
        Collections.sort(mSearchedCities);
        String[] searchedCitiesArray = new String[mSearchedCities.size()];
        searchedCitiesArray = mSearchedCities.toArray(searchedCitiesArray);
        return searchedCitiesArray;
    }

    /**
     * Sets the searched cities from the shared preferences.
     */
    private void retrieveSearchedCitiesNames(){
        SharedPreferences editor = getSharedPreferences(SharedPrefKeys.HISTORY, MODE_PRIVATE);
        Set<String> set = editor.getStringSet("SearchedCities", null);
        if (set != null) {
            mSearchedCities = new ArrayList<>(set);
        }
    }

    @Override
    public void saveCity(int index, SharedPreferences sharedPref) {}
}