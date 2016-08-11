package com.p92rdi.extendedweathertitan.activity;

import android.content.Intent;
import android.os.Bundle;
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

    //retrieveSearchedCitiesNames(); kellene előtte
    private final String[] searchedCities = getSearchedCitiesInArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationMenu(this);

        Toast.makeText(this, "searchedCities: " + searchedCities.length, Toast.LENGTH_LONG).show();

        button = (Button) findViewById(R.id.historyButton);
        historyListView = (ListView) findViewById(R.id.historyListView);

        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, searchedCities[position], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ForecastActivity.class);
                intent.putExtra(SharedPrefKeys.SEARCH_KEY, searchedCities[position]);
                startActivity(intent);
            }
        });
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