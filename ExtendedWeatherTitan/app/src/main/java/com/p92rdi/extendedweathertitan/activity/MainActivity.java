package com.p92rdi.extendedweathertitan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.p92rdi.extendedweathertitan.R;
import com.p92rdi.extendedweathertitan.helper.SharedPrefKeys;

public class MainActivity extends MenuBarActivity {

    private Button button;
    ListView historyListView;

    private final String[] searchedCities = getSearchedCitiesInArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationMenu(this);

        //Toast.makeText(this, getResources().getString(R.string.searched) + searchedCities.length, Toast.LENGTH_LONG).show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               clearHistory(view);
            }
        });

        historyListView = (ListView) findViewById(R.id.historyListView);

        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, searchedCities[position], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ForecastActivity.class);
                intent.putExtra(SharedPrefKeys.SEARCH_KEY, searchedCities[position]);
                startActivity(intent);
            }
        });
    }

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