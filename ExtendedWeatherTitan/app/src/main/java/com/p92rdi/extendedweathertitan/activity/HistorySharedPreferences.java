package com.p92rdi.extendedweathertitan.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class HistorySharedPreferences extends AppCompatActivity{

    public static final String HISTORY = "History";

    private static ArrayList<String> searchedCities = new ArrayList<>();

    @Override
    protected void onPause() {
        super.onPause();
        saveSearchedCityNames();
    }

    /**
     * Saves the set of searched cities into a shared preferences.
     */
    public void saveSearchedCityNames(){
        SharedPreferences.Editor editor = getSharedPreferences(HISTORY, MODE_PRIVATE).edit();
        Set<String> set = new HashSet<>();
        set.addAll(searchedCities);
        editor.putStringSet("mSearchedCities", set);
        editor.apply();
    }
}