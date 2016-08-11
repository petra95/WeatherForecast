package com.p92rdi.extendedweathertitan.originActivities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.p92rdi.extendedweathertitan.R;
import com.p92rdi.extendedweathertitan.activity.HistorySharedPreferences;
import com.p92rdi.extendedweathertitan.helper.FillingListAdapter;
import com.p92rdi.extendedweathertitan.helper.HttpClient;
import com.p92rdi.extendedweathertitan.helper.JSONWeatherParser;
import com.p92rdi.extendedweathertitan.model.DailyForecast;
import com.p92rdi.extendedweathertitan.model.Location;
import com.p92rdi.extendedweathertitan.model.WeatherForecastFiveDays;

import org.json.JSONException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ForecastActivity_origin extends HistorySharedPreferences implements NavigationView.OnNavigationItemSelectedListener {

    private static final String SEARCH_KEY = "CityNameKey";

    private WeatherForecastFiveDays weatherForecastFiveDays;
    private Location location;
    private DailyForecast dailyForecast;
    private List<DailyForecast> fillings = new ArrayList<>();
    private ListView lv_forecast;
    private String mCity = "Budapest";

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        lv_forecast = (ListView) findViewById(R.id.lv_forecast);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {
            mCity = (String) bundle.get(SEARCH_KEY);
            Toast.makeText(this, mCity, Toast.LENGTH_LONG).show();
        }

        if(isNetworkAvailable()) {
            new JSONWeatherForecastTask().execute(mCity);
        } else {
            Toast.makeText(this, "Internet is not available!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(isNetworkAvailable()){
            if (id == R.id.search) {
                //searchDialog();
            } else if (id == R.id.loadCity) {
                //loadCityDialog();
            } else if (id == R.id.saveCity) {
                //saveActualCityDialog();
            } else if (id == R.id.search5) {
                searchDialog();
            } else if (id == R.id.loadCity5) {

            } else if (id == R.id.saveCity5) {

            } else if (id == R.id.settings) {

            } else if (id == R.id.about) {

            }
        } else {
            Toast.makeText(this, "Internet is not available!", Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private class JSONWeatherForecastTask extends AsyncTask<String, Void, WeatherForecastFiveDays> {

        @Override
        protected WeatherForecastFiveDays doInBackground(String... params) {
            WeatherForecastFiveDays weatherForecastFiveDays = new WeatherForecastFiveDays();
            String mRawJson = ((new HttpClient("forecast")).getWeatherData(params[0]));
            Log.d("ServiceHandler", "data: " + mRawJson);
            if(mRawJson != null && !mRawJson.equals("")) {
                try {
                    try {
                        weatherForecastFiveDays = JSONWeatherParser.getWeatherForecastFiveDays(mRawJson);
                        Log.d("ServiceHandler", "weatherForecastFiveDays: " + weatherForecastFiveDays);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("ServiceHandler", "No data received from HTTP request");
                weatherForecastFiveDays = new WeatherForecastFiveDays();
            }
            return weatherForecastFiveDays;
        }

        @Override
        protected void onPostExecute(WeatherForecastFiveDays weatherForecastFiveDays) {
            super.onPostExecute(weatherForecastFiveDays);
            Log.d("ServiceHandler", "weatherForecastFiveDays: " + weatherForecastFiveDays);
            if(weatherForecastFiveDays != null) {
                ForecastActivity_origin.this.weatherForecastFiveDays = weatherForecastFiveDays;
                displayData();
            }
        }
    }

    private void displayData() {
        //header
        TextView tvCity = (TextView) findViewById(R.id.tvCity);
        TextView tvGps = (TextView) findViewById(R.id.tvGPS);
        tvCity.setText(weatherForecastFiveDays.getmLocation().getmCity());
        tvCity.setMovementMethod(new ScrollingMovementMethod());

        String lon = String.valueOf(weatherForecastFiveDays.getmLocation().getmLongitude()).concat(getResources().getString(R.string.degree));
        String lat = String.valueOf(weatherForecastFiveDays.getmLocation().getmLatitude()).concat(getResources().getString(R.string.degree));
        String gps = "( Lon: " + lon + ", Lat: " + lat + " )";

        tvGps.setText(gps);

        //Filling listview
        Log.e("displayData()", weatherForecastFiveDays.getmDays().toString());
        fillings = generateFiveDaysForecastsList(weatherForecastFiveDays, weatherForecastFiveDays.getmDays());
        FillingListAdapter adapter = new FillingListAdapter(this, fillings);
        lv_forecast.setAdapter(adapter);
    }

    public ArrayList<DailyForecast> generateFiveDaysForecastsList(WeatherForecastFiveDays weatherForecastFiveDays, List<Long> mDays){
        ArrayList<DailyForecast> fiveDaysForecastsList = new ArrayList<>();
        for(long date : mDays){
            DailyForecast currentDay = new DailyForecast(weatherForecastFiveDays, date);
            fiveDaysForecastsList.add(currentDay);
        }

        return fiveDaysForecastsList;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(SEARCH_KEY, mCity);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCity = savedInstanceState.getString(SEARCH_KEY);

        if(isNetworkAvailable()){
            Toast.makeText(this, mCity, Toast.LENGTH_SHORT).show();
            new JSONWeatherForecastTask().execute(mCity);
        } else{
            Toast.makeText(this, "Internet is not available!", Toast.LENGTH_LONG).show();
        }
    }

    public void getWeatherForecastFiveDays(){
        mCity = mCity.replace(" ", "");
        Log.d("ServiceHandler", "cityName: " + mCity);
        if(!mCity.equals("")) {
            new JSONWeatherForecastTask().execute(mCity);
            addToSearchedCities(mCity);
        }
    }

    public void searchDialog(){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.search_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editText = (EditText) dialogView.findViewById(R.id.dialog_search);

        dialogBuilder.setTitle("Search");
        dialogBuilder.setMessage("Enter City name");
        dialogBuilder.setButton(AlertDialog.BUTTON_POSITIVE, "Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mCity = editText.getText().toString();
                dialogBuilder.dismiss();
                getWeatherForecastFiveDays();
            }
        });
        dialogBuilder.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogBuilder.dismiss();
            }
        });
        dialogBuilder.setOnKeyListener((new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey (DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    mCity = editText.getText().toString();
                    dialogBuilder.dismiss();
                    return true;
                }
                return false;
            }
        }));
        dialogBuilder.show();
    }
}