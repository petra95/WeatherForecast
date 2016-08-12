package com.p92rdi.extendedweathertitan.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.p92rdi.extendedweathertitan.R;
import com.p92rdi.extendedweathertitan.helper.SharedPrefKeys;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends MenuBarActivity {

    ListView historyListView;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadHistory();
        Toast.makeText(this, "searchedCities: " + searchedCities.size(), Toast.LENGTH_LONG).show();
    }

    public void loadHistory(){
        final String[] searchedCities = getSearchedCitiesInArray();
        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, searchedCities[position], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ForecastActivity.class);
                intent.putExtra(SharedPrefKeys.SEARCH_KEY, searchedCities[position]);
                startActivity(intent);
            }
        });
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.history_row,R.id.rowTextView,searchedCities);
        historyListView.setAdapter(adapter);
    }

    public void clearHistoryOnClick(View view) {

        searchedCities.clear();
        saveSearchedCityNames();
        loadHistory();
    }

    public String[] getSearchedCitiesInArray(){
        retrieveSearchedCitiesNames();
        String[] searchedCitiesArray = new String[searchedCities.size()];
        searchedCitiesArray = searchedCities.toArray(searchedCitiesArray);
        return searchedCitiesArray;
    }

    private void retrieveSearchedCitiesNames(){
        SharedPreferences editor = getSharedPreferences(SharedPrefKeys.HISTORY, MODE_PRIVATE);
        Set<String> set = editor.getStringSet("searchedCities", null);
        if (set != null) {
            searchedCities = new ArrayList<>(set);
        }
    }
}