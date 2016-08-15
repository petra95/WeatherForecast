package com.p92rdi.extendedweathertitan.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.p92rdi.extendedweathertitan.R;
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

    public void saveSearchedCityNames(){
        SharedPreferences.Editor editor = getSharedPreferences(HISTORY, MODE_PRIVATE).edit();
        Set<String> set = new HashSet<>();
        set.addAll(searchedCities);
        editor.putStringSet("mSearchedCities", set);
        editor.apply();
    }

    public void retrieveSearchedCitiesNames(){
        SharedPreferences editor = getSharedPreferences(HISTORY, MODE_PRIVATE);
        Set<String> set = editor.getStringSet("mSearchedCities", null);
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