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
    private static String IMG_URL = "http://openweathermap.org/img/w/";

    public HttpClient(){}

    public HttpClient(String url_day_type){
        BASE_URL_NAME += url_day_type + "?units=metric&q=";
    }

    /**
     * Gets the queried weather data through a http request for the specified city name.
     * @param cityName The name of the city from which we need to gather the weather data.
     * @return Return the data in string in a format of JSON.
     */
    public String getWeatherData(String cityName) {
        HttpURLConnection mConnection = null;
        InputStream mInputStream = null;
        Log.d("Servicehandler", "BASE_URL_NAME + cityName + API_KEY: " + BASE_URL_NAME + cityName + API_KEY);
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

    /**
     * Gets an image through a http request.
     * @param code The url code of the image.
     * @return An image as a bitmap.
     */
    public Bitmap getImage(String code) {
        code += ".png";
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