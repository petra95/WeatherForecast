package com.p92rdi.extendedweathertitan;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mok2 {
    private String first;
    private String second;

    public Mok2() {}

    Mok2(String first, String second) {
        this.first = first;
        this.second = second;
    }

    public String getSecond() {
        return second;
    }

    public String getFirst() { return first; }

    public ArrayList<Mok2> generateArrayList() {
        ArrayList<Mok2> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            list.add(new Mok2(Integer.toString(random.nextInt()), Integer.toString(random.nextInt())));
        }
        return list;
    }
}
