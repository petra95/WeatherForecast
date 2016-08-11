package com.p92rdi.extendedweathertitan.originActivities;

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
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.p92rdi.extendedweathertitan.R;
import com.p92rdi.extendedweathertitan.activity.HistorySharedPreferences;

public class MainActivity_origin extends HistorySharedPreferences
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences mSharedPreferences;
    private static final String FILE_NAME = "FileName";
    private static final String SHARED_PREFERENCES = "SharedPreferences";
    private static final String SLOT1_KEY = "Empty slot";
    private static final String SLOT2_KEY = "Empty slot";
    private static final String SLOT3_KEY = "Empty slot";
    private static final String SEARCH_KEY = "CityNameKey";

    private String[] mSavedCities = new String[3];
    private String[] mSavedCities5 = new String[3];
    private String mActualCity;

    private Button button;
    ListView historyListView;

    //retrieveSearchedCitiesNames(); kellene előtte
    private final String[] searchedCities = getSearchedCitiesInArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mSharedPreferences = getSharedPreferences(FILE_NAME, 0);
        mSavedCities[0] = mSharedPreferences.getString(SLOT1_KEY, "");
        mSavedCities[1] = mSharedPreferences.getString(SLOT2_KEY, "Empty slot");
        mSavedCities[2] = mSharedPreferences.getString(SLOT3_KEY, "Empty slot");
        /*
        mSavedCities5[0] = mSharedPreferences.getString(SLOT1_KEY, "Empty slot");
        mSavedCities5[1] = mSharedPreferences.getString(SLOT2_KEY, "Empty slot");
        mSavedCities5[2] = mSharedPreferences.getString(SLOT3_KEY, "Empty slot");
        */

        //ExtendedWeatherTitanConstans.SharedPrefKeys.SLOT1;

        String locale = this.getResources().getConfiguration().locale.getDisplayName();


        Toast.makeText(this, "searchedCities: " + searchedCities.length, Toast.LENGTH_LONG).show();

        //button = (Button) findViewById(R.id.historyButton);
        historyListView = (ListView) findViewById(R.id.historyListView);

        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity_origin.this, searchedCities[position], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity_origin.this, ForecastActivity_origin.class);
                intent.putExtra(SEARCH_KEY, searchedCities[position]);
                startActivity(intent);
            }
        });
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(isNetworkAvailable()){
            if (id == R.id.search) {
                Intent intent = new Intent(this, CurrentActivity_origin.class);
                searchDialog(intent);
            } else if (id == R.id.loadCity) {
                loadCityDialog();
            } else if (id == R.id.saveCity) {
                Toast.makeText(this, "Nothing to save!", Toast.LENGTH_LONG).show();
            } else if (id == R.id.search5) {
                Intent intent = new Intent(MainActivity_origin.this, ForecastActivity_origin.class);
                searchDialog(intent);
            } else if (id == R.id.loadCity5) {
                //loadCityDialog5();
            } else if (id == R.id.saveCity5) {
                Toast.makeText(this, "Nothing to save!", Toast.LENGTH_LONG).show();
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
                String mCityName = editText.getText().toString();
                mActualCity = mCityName;
                if(!mActualCity.equals("")){
                    intent.putExtra(SEARCH_KEY, mCityName);
                    startActivity(intent);
                }
                dialogBuilder.dismiss();
                addToSearchedCities(mActualCity);
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
                    String mCityName = editText.getText().toString();
                    mActualCity = mCityName;
                    if(!mActualCity.equals("")){
                        intent.putExtra(SEARCH_KEY, mCityName);
                        startActivity(intent);
                    }
                    dialogBuilder.dismiss();
                    return true;
                }
                return false;
            }
        }));
        dialogBuilder.show();
    }
/*
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
*/
    public void loadCityDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.load_dialog_title).setItems(mSavedCities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mActualCity = mSavedCities[i];
                Intent intent = new Intent(MainActivity_origin.this, CurrentActivity_origin.class);
                intent.putExtra(SEARCH_KEY, mActualCity);
                startActivity(intent);
            }
        });
        builder.create();
        builder.show();
    }
    /*public void loadCityDialog5() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.load_dialog_title).setItems(mSavedCities5, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mActualCity = mSavedCities5[i];
                Intent intent = new Intent(MainActivity.this, ForecastActivity.class);
                intent.putExtra(SEARCH_KEY, mActualCity);
                startActivity(intent);
            }
        });
        builder.create();
        builder.show();
    }*/
    /*
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
    }*/
/*
    public void saveActualCity(int index){
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
*/
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    //  KELL
    @Override
    protected void onResume() {
        super.onResume();
        retrieveSearchedCitiesNames();
        loadHistory();
    }

    public void clearHistory(View view) {
        super.clearHistory();
        super.loadHistory();
    }

}

