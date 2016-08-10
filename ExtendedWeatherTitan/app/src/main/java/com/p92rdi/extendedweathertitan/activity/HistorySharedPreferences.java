package com.p92rdi.extendedweathertitan.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.p92rdi.extendedweathertitan.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by totha on 2016. 08. 10..
 */
public class HistorySharedPreferences extends AppCompatActivity{

    public static final String HISTORY = "History";

    private static ArrayList<String> searchedCities = new ArrayList<>();

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
        ListView listView = (ListView) findViewById(R.id.historyListView);
        listView.setAdapter(adapter);
    }

    public void clearHistory(){
        searchedCities.clear();
    }

    public ArrayList<String> getSearchedCities(){
        return searchedCities;
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveSearchedCityNames();
    }
}
