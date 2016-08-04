package com.p92rdi.extendedweathertitan;

import android.app.ListActivity;
import android.os.Bundle;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by antalicsp on 2016. 08. 04..
 */
public class ListDaysActivity extends ListActivity{

    private Mok2 mok;
    private List<Mok2> fillings = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mok = new Mok2();
        fillings = mok.generateArrayList();
        setListAdapter(new FillingListAdapter(this, fillings));
    }
}
