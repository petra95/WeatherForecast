package com.p92rdi.extendedweathertitan.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.p92rdi.extendedweathertitan.R;
import com.p92rdi.extendedweathertitan.helper.HttpClient;
import com.p92rdi.extendedweathertitan.helper.JsonParser;
import com.p92rdi.extendedweathertitan.model.Weather;

public class CurrentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String SAVE_CITY_NAME_KEY = "CityName";
    private static final String SEARCH_KEY = "CityNameKey";
    private static final String[] SLOTS = new String[]{"slot1", "slot2", "slot3"};

    private TableLayout mDataTableLayout;
    private HttpClient mClient;
    private Weather mResultWeather;
    private TextView tv_city;
    private TextView tv_degree;
    private TextView tv_description;
    private TextView tv_minDeg;
    private TextView tv_maxDeg;
    private TextView tv_wind;
    private TextView tv_humidity;
    private String mActualCityNameString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        init();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            String cityName = (String) bundle.get(SEARCH_KEY);
            getWeather(cityName);
        }
    }

    private void init() {
        tv_city = (TextView) findViewById(R.id.cityTextView);
        tv_degree = (TextView) findViewById(R.id.degreeTextView);
        tv_description = (TextView) findViewById(R.id.descriptionTextView);
        tv_minDeg = (TextView) findViewById(R.id.minDegTextView);
        tv_maxDeg = (TextView) findViewById(R.id.maxDegTextView);
        tv_wind = (TextView) findViewById(R.id.windTextView);
        tv_humidity = (TextView) findViewById(R.id.humidityTextView);
        mClient = new HttpClient();
        mDataTableLayout = (TableLayout) findViewById(R.id.dataTableLayout);
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

        if (id == R.id.search) {
            searchDialog();
        } else if (id == R.id.loadCity) {
            loadCityDialog();
        } else if (id == R.id.saveCity) {
            saveActualCityDialog();
        } else if (id == R.id.search5) {
            searchDialog();
        } else if (id == R.id.loadCity5) {

        } else if (id == R.id.saveCity5) {

        } else if (id == R.id.settings) {

        } else if (id == R.id.about) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(CurrentActivity.this).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("Do you want to save this city?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SAVE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
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
                String mCityName = editText.getText().toString();
                getWeather(mCityName);
                dialogBuilder.dismiss();
            }
        });
        dialogBuilder.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.show();

    }

    public void saveActualCityDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.save_dialog_title).setItems(SLOTS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                openDialog();
            }
        });
        builder.create();
        builder.show();
    }

    public void loadCityDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.load_dialog_title).setItems(SLOTS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create();
        builder.show();
    }
}
