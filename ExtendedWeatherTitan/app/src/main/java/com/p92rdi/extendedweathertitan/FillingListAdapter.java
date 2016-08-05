package com.p92rdi.extendedweathertitan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.p92rdi.extendedweathertitan.model.DailyForecast;

import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by antalicsp on 2016. 08. 03..
 */
public class FillingListAdapter extends ArrayAdapter<DailyForecast> {

    private List<DailyForecast> fillings;
    private LayoutInflater inflater;

    public FillingListAdapter(Context context, List<DailyForecast> fillings) {
        super(context, R.layout.list_forecast, fillings);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.fillings = fillings;
    }

    @Override
    public int getCount() {
        return fillings.size();
    }

    @Override
    public DailyForecast getItem(int position) {
        return fillings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_forecast, parent, false);
            holder = new Holder();

            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);

            holder.nightTempTextView5 = (TextView) convertView.findViewById(R.id.nightDegTextView5);
            holder.nightImageView = (ImageView) convertView.findViewById(R.id.nightImageView);
            holder.tvNightDescription = (TextView) convertView.findViewById(R.id.tvNightDescription);
            holder.dayTempTextView5 = (TextView) convertView.findViewById(R.id.dayDegTextView5);
            holder.dayImageView = (ImageView) convertView.findViewById(R.id.dayImageView);
            holder.tvDayDescription = (TextView) convertView.findViewById(R.id.tvDayDescription);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        DailyForecast newItem = getItem(position);

        holder.tvDate.setText(DateFormat.getDateTimeInstance().format(new Date(newItem.getmDate())));
        holder.nightTempTextView5.setText(String.valueOf(newItem.getmTemperatureNight()));
        holder.dayTempTextView5.setText(String.valueOf(newItem.getmTemperatureDay()));
        holder.tvNightDescription.setText(String.valueOf(newItem.getmDescriptionNight()));
        holder.tvDayDescription.setText(String.valueOf(newItem.getmDescriptionDay()));

        /*if (weather.iconData != null && weather.iconData.length > 0) {
            Bitmap img = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length);
            holder.nightImageView.setImageBitmap(img);
        }*/

        if (newItem.getmIconDay() != null ) {
            holder.dayImageView.setImageBitmap(newItem.getmIconDay());
        } else {
            Log.d("ExtendedWeatherTitan","No day icon");
        }

        if (newItem.getmIconNight() != null ) {
            holder.nightImageView.setImageBitmap(newItem.getmIconNight());
        } else {
            Log.d("ExtendedWeatherTitan","No night icon");
        }

        return convertView;
    }

    public class Holder {
        TextView tvDate;
        ImageView nightImageView;
        TextView nightTempTextView5;
        TextView dayTempTextView5;
        ImageView dayImageView;
        TextView tvNightDescription;
        TextView tvDayDescription;
    }
}
