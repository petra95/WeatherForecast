package com.p92rdi.extendedweathertitan.helper;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

    private String BASE_URL_NAME = "http://api.openweathermap.org/data/2.5/";
    private static String API_KEY = "&appid=6400cc1cfebfb4e0cab17b0eb34472da";
    private static String BASE_URL_ID = "http://api.openweathermap.org/data/2.5/forecast?id=";
    private static String IMG_URL = "http://openweathermap.org/img/w/";

    public HttpClient(String url_day_type){
        BASE_URL_NAME += url_day_type + "?q=";
    }

    public String getWeatherData(String cityName) {
        HttpURLConnection mConnection = null;
        InputStream mInputStream = null;

        try {
            mConnection = (HttpURLConnection) (new URL(BASE_URL_NAME + cityName + API_KEY)).openConnection();
            mConnection.setRequestMethod("GET");
            mConnection.setDoInput(true);
            mConnection.setDoOutput(true);
            mConnection.connect();

            StringBuilder mStringBuilder = new StringBuilder();
            mInputStream = mConnection.getInputStream();
            BufferedReader mBufferedReader = new BufferedReader(new InputStreamReader(mInputStream));
            String mNewLine = null;
            while ( (mNewLine = mBufferedReader.readLine()) != null )
                mStringBuilder.append(mNewLine).append("\n");

            mInputStream.close();
            mConnection.disconnect();
            return mStringBuilder.toString();
        }
        catch(Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    public String getWeatherData(int cityId) {
        HttpURLConnection mConnection = null;
        InputStream mInputStream = null;

        try {
            mConnection = (HttpURLConnection) (new URL(BASE_URL_ID + cityId + API_KEY)).openConnection();
            mConnection.setRequestMethod("GET");
            mConnection.setDoInput(true);
            mConnection.setDoOutput(true);
            mConnection.connect();

            StringBuilder mStringBuilder = new StringBuilder();
            mInputStream = mConnection.getInputStream();
            BufferedReader mBufferedReader = new BufferedReader(new InputStreamReader(mInputStream));
            String mNewLine = null;
            while ( (mNewLine = mBufferedReader.readLine()) != null )
                mStringBuilder.append(mNewLine).append("\n");

            mInputStream.close();
            mConnection.disconnect();
            return mStringBuilder.toString();
        }
        catch(Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    public Bitmap getImage(String code) {
        HttpURLConnection con = null ;
        InputStream is = null;

        try {
            con = (HttpURLConnection) ( new URL(IMG_URL + code)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.connect();

            is = con.getInputStream();

            return BitmapFactory.decodeStream(is);
        }
        catch(Throwable t) {
            t.printStackTrace();
            return null;
        }
    }
}