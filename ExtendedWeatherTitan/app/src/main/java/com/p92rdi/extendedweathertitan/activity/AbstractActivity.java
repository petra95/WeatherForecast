package com.p92rdi.extendedweathertitan.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.p92rdi.extendedweathertitan.R;
import com.p92rdi.extendedweathertitan.helper.SharedPrefKeys;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected static ArrayList<String> mSearchedCities = new ArrayList<>();
    protected String mActualCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationMenu(this);
    }

    protected void navigationMenu(AbstractActivity activity) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(activity);
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

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        boolean netWorkAccess = isNetworkAvailable();
        switch(id) {
            case R.id.home:
                Intent intentM = new Intent(this, MainActivity.class);
                startActivity(intentM);
                break;
            case R.id.search:
                if (!netWorkAccess) {
                    Toast.makeText(this, getResources().getString(R.string.internet), Toast.LENGTH_LONG).show();
                } else {
                    Intent intentC = new Intent(this, CurrentActivity.class);
                    searchDialog(intentC);
                }
                break;
            case R.id.loadCity:
                if (!netWorkAccess) {
                    Toast.makeText(this, getResources().getString(R.string.internet), Toast.LENGTH_LONG).show();
                } else {
                    loadCityDialog(new Intent(this, CurrentActivity.class), getSharedPreferences(
                            getString(R.string.preference_file_key_current), Context.MODE_PRIVATE));
                }
                break;
            case R.id.saveCity:
                if (this.getClass().equals(CurrentActivity.class)) {
                    saveCityDialog(getSharedPreferences(getString(R.string.preference_file_key_current), Context.MODE_PRIVATE));
                } else {
                    Toast.makeText(this, getResources().getString(R.string.no_save), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.search5:
                if (!netWorkAccess) {
                    Toast.makeText(this, getResources().getString(R.string.internet), Toast.LENGTH_LONG).show();
                } else {
                    Intent intentF = new Intent(this, ForecastActivity.class);
                    searchDialog(intentF);
                }
                break;
            case R.id.loadCity5:
                if (!netWorkAccess) {
                    Toast.makeText(this, getResources().getString(R.string.internet), Toast.LENGTH_LONG).show();
                } else {
                    loadCityDialog(new Intent(this, ForecastActivity.class), getSharedPreferences(
                            getString(R.string.preference_file_key_forecast), Context.MODE_PRIVATE));
                }
                break;
            case R.id.saveCity5:
                if (this.getClass().equals(ForecastActivity.class)) {
                    saveCityDialog(getSharedPreferences(getString(R.string.preference_file_key_forecast), Context.MODE_PRIVATE));
                } else {
                    Toast.makeText(this, getResources().getString(R.string.no_save), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.settings:
                Toast.makeText(this, getResources().getString(R.string.setting_text), Toast.LENGTH_LONG).show();
                break;
            case R.id.about:
                Toast.makeText(this, getResources().getString(R.string.about_text), Toast.LENGTH_LONG).show();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void searchDialog(final Intent intent){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        final View dialogView = this.getLayoutInflater().inflate(R.layout.search_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editText = (EditText) dialogView.findViewById(R.id.dialog_search);

        dialogBuilder.setTitle(getResources().getString(R.string.search));
        dialogBuilder.setMessage(getResources().getString(R.string.add_city));
        dialogBuilder.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.cap_search), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mActualCity = editText.getText().toString();
                Log.d("ServiceHandler", "searchDialog/ mActualCity: " + mActualCity);
                if(!mActualCity.equals("")){
                    intent.putExtra(SharedPrefKeys.SEARCH_KEY, mActualCity);
                    startActivity(intent);
                }
                dialogBuilder.dismiss();
            }

        });
        dialogBuilder.setButton(AlertDialog.BUTTON_NEGATIVE, getResources().getString(R.string.cancel), new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogBuilder.dismiss();
            }
        });
        dialogBuilder.show();
    }


    public void loadCityDialog(final Intent intent, final SharedPreferences sharedPref) {
        final String[] savedCities = new String[]{sharedPref.getString(SharedPrefKeys.SLOT1_KEY,
                getResources().getString(R.string.empty)), sharedPref.getString(SharedPrefKeys.SLOT2_KEY,
                getResources().getString(R.string.empty)), sharedPref.getString(SharedPrefKeys.SLOT3_KEY,
                getResources().getString(R.string.empty))};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.load_dialog_title).setItems(savedCities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mActualCity = savedCities[i];
                if(!mActualCity.equals(getString(R.string.empty))){
                    String cityName = removeLastWord(mActualCity);
                    intent.putExtra(SharedPrefKeys.SEARCH_KEY, cityName);
                    startActivity(intent);
                }else{
                    Toast.makeText(AbstractActivity.this, R.string.cant_load, Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.create();
        builder.show();
    }

    public String removeLastWord(String text) {
        return text.substring(0, text.lastIndexOf(" "));
    }

    public void openSaveDialog(final int index, final SharedPreferences sharedPref) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getResources().getString(R.string.warning));
        alertDialog.setMessage(getResources().getString(R.string.dialog_message));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        saveCity(index, sharedPref);
                        dialog.dismiss();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public abstract void saveCity(final int index, final SharedPreferences sharedPref);

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(SharedPrefKeys.SEARCH_KEY, mActualCity);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        mActualCity = savedInstanceState.getString(SharedPrefKeys.SEARCH_KEY);
    }

    public void saveCityDialog(final SharedPreferences sharedPref){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.save_dialog_title).setItems(new String[]{sharedPref.getString(SharedPrefKeys.SLOT1_KEY,
                getResources().getString(R.string.empty)), sharedPref.getString(SharedPrefKeys.SLOT2_KEY,
                getResources().getString(R.string.empty)), sharedPref.getString(SharedPrefKeys.SLOT3_KEY,
                getResources().getString(R.string.empty))}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int index) {
                openSaveDialog(index, sharedPref);
            }
        });

        builder.create();
        builder.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveSearchedCityNames();
    }

    public void saveSearchedCityNames(){
        SharedPreferences.Editor editor = getSharedPreferences(SharedPrefKeys.HISTORY, MODE_PRIVATE).edit();
        Set<String> cityNamesSet = new HashSet<>();
        cityNamesSet.addAll(mSearchedCities);
        editor.putStringSet("SearchedCities", cityNamesSet);
        editor.apply();
    }

    public void addToSearchedCities(String newCity){
        if(!mSearchedCities.contains(newCity)) {
            mSearchedCities.add(newCity);
        }
    }
}
