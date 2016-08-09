package com.p92rdi.extendedweathertitan.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.p92rdi.extendedweathertitan.R;
import com.p92rdi.extendedweathertitan.helper.HttpClient;
import com.p92rdi.extendedweathertitan.helper.JsonParser;
import com.p92rdi.extendedweathertitan.model.Weather;

public class CurrentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences mSharedPreferences;
    private static final String FILE_NAME = "FileName";
    private static final String SLOT1_KEY = "Slot1";
    private static final String SLOT2_KEY = "Slot2";
    private static final String SLOT3_KEY = "Slot3";
    private static final String SEARCH_KEY = "CityNameKey";

    private String[] mSavedCities = new String[3];
    private String mActualCity;
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
            mActualCity = (String) bundle.get(SEARCH_KEY);
            if(isNetworkAvailable()) {
                getWeather(mActualCity);
            } else {
                Toast.makeText(this, "Internet is not available!", Toast.LENGTH_LONG).show();
            }
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
        mClient = new HttpClient("weather");
        mDataTableLayout = (TableLayout) findViewById(R.id.dataTableLayout);
        mSharedPreferences = getSharedPreferences(FILE_NAME, 0);
        mSavedCities[0] = mSharedPreferences.getString(SLOT1_KEY, "slot1");
        mSavedCities[1] = mSharedPreferences.getString(SLOT2_KEY, "slot2");
        mSavedCities[2] = mSharedPreferences.getString(SLOT3_KEY, "slot3");
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
        String humidity = String.valueOf(weatherData.getmHumidity()) + "%";

        tv_city.setText(city);
        tv_city.setMovementMethod(new ScrollingMovementMethod());
        tv_degree.setText(degree);
        tv_description.setText(description);
        tv_minDeg.setText(minDeg);
        tv_maxDeg.setText(maxDeg);
        tv_wind.setText(wind);
        tv_humidity.setText(humidity);
    }

    private void getWeather(String query) {
        final String mFinalQuery = query.replace(" ", "%20");
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
        if(isNetworkAvailable()){
            if (id == R.id.search) {
                searchDialog();
            } else if (id == R.id.loadCity) {
                loadCityDialog();
            } else if (id == R.id.saveCity) {
                saveActualCityDialog();
            } else if (id == R.id.search5) {
                //searchDialog();
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
                mActualCity = mCityName;
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
        builder.setTitle(R.string.save_dialog_title).setItems(mSavedCities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int index) {
                openDialog(index);
            }
        });

        builder.create();
        builder.show();
    }

    public void loadCityDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.load_dialog_title).setItems(mSavedCities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!mSavedCities[i].equals("slot" + (i+1))){
                    Log.e("Belep", mSavedCities[i]);
                    mActualCity = mSavedCities[i];
                    getWeather(mActualCity);
                }
            }
        });

        builder.create();
        builder.show();
    }

    public void openDialog(final int index) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("Do you want to save this city?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SAVE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mSavedCities[index] = mActualCity;
                        saveActualCity(index);
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

    public void saveActualCity(int index) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        switch (index) {
            case 0:
                editor.putString(SLOT1_KEY, mActualCity);
                break;
            case 1:
                editor.putString(SLOT2_KEY, mActualCity);
                break;
            case 2:
                editor.putString(SLOT3_KEY, mActualCity);
                break;
        }
        editor.apply();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(SEARCH_KEY,mActualCity);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        mActualCity = savedInstanceState.getString(SEARCH_KEY);
        if(isNetworkAvailable()) {
            getWeather(mActualCity);
        } else {
            Toast.makeText(this, "Internet is not available!", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
