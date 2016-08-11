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
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.p92rdi.extendedweathertitan.R;
import com.p92rdi.extendedweathertitan.helper.SharedPrefKeys;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MenuBarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String HISTORY = "History";
    private static ArrayList<String> searchedCities = new ArrayList<>();

    protected String mActualCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationMenu(this);
    }

    protected void navigationMenu(MenuBarActivity activity) {
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
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(isNetworkAvailable()){
            if (id == R.id.search) {
                Intent intent = new Intent(this, CurrentActivity.class);
                searchDialog(intent);
            } else if (id == R.id.loadCity) {
                loadCityDialog(new Intent(this, CurrentActivity.class), getSharedPreferences(
                        getString(R.string.preference_file_key_current), Context.MODE_PRIVATE));
            } else if (id == R.id.saveCity) {
                saveClickAction();
            } else if (id == R.id.search5) {
                Intent intent = new Intent(this, ForecastActivity.class);
                searchDialog(intent);
            } else if (id == R.id.loadCity5) {
                loadCityDialog(new Intent(this, ForecastActivity.class), getSharedPreferences(
                        getString(R.string.preference_file_key_forecast), Context.MODE_PRIVATE));
            } else if (id == R.id.saveCity5) {
                saveClickAction();
            } else if (id == R.id.settings) {
                Toast.makeText(this, "There are no settings yet! lol", Toast.LENGTH_LONG).show();
            } else if (id == R.id.about) {
                Toast.makeText(this, "I'm ABOUT to finish this app.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Internet is not available!", Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void searchDialog(final Intent intent){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        final View dialogView = this.getLayoutInflater().inflate(R.layout.search_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editText = (EditText) dialogView.findViewById(R.id.dialog_search);

        dialogBuilder.setTitle("Search");
        dialogBuilder.setMessage("Enter City name");
        dialogBuilder.setButton(AlertDialog.BUTTON_POSITIVE, "Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mActualCity = editText.getText().toString();
                if(!mActualCity.equals("")){
                    intent.putExtra(SharedPrefKeys.SEARCH_KEY, mActualCity);
                    startActivity(intent);
                }
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
                intent.putExtra(SharedPrefKeys.SEARCH_KEY, mActualCity);
                startActivity(intent);
            }
        });
        builder.create();
        builder.show();
    }
    public void openSaveDialog(final int index, final SharedPreferences sharedPref) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("Do you want to save this city?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SAVE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        saveCity(index, sharedPref);
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

    public void saveCity(final int index, final SharedPreferences sharedPref) {
        SharedPreferences.Editor editor = sharedPref.edit();
        switch (index) {
            case 0:
                editor.putString(SharedPrefKeys.SLOT1_KEY, mActualCity);
                break;
            case 1:
                editor.putString(SharedPrefKeys.SLOT2_KEY, mActualCity);
                break;
            case 2:
                editor.putString(SharedPrefKeys.SLOT3_KEY, mActualCity);
                break;
        }
        editor.apply();
    }

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
    public void saveClickAction(){
        Toast.makeText(this, "Nothing to save!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveSearchedCityNames();
    }

    public void saveSearchedCityNames(){
        SharedPreferences.Editor editor = getSharedPreferences(HISTORY, MODE_PRIVATE).edit();
        Set<String> set = new HashSet<>();
        set.addAll(searchedCities);
        editor.putStringSet("searchedCities", set);
        editor.apply();
    }

    public void retrieveSearchedCitiesNames(){
        SharedPreferences editor = getSharedPreferences(HISTORY, MODE_PRIVATE);
        Set<String> set = editor.getStringSet("searchedCities", null);
        if (set != null) {
            searchedCities = new ArrayList<>(set);
        }
    }

    public void addToSearchedCities(String newCity){
        searchedCities.add(newCity);
    }

    public void loadHistory(){
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.history_row,R.id.rowTextView,searchedCities);
        ListView historyListView = (ListView) findViewById(R.id.historyListView);
        historyListView.setAdapter(adapter);
    }

    public void clearHistory(){
        searchedCities.clear();
    }

    public String[] getSearchedCitiesInArray(){
        String[] searchedCitiesArray = new String[searchedCities.size()];
        searchedCitiesArray = searchedCities.toArray(searchedCitiesArray);
        return searchedCitiesArray;
    }
}
