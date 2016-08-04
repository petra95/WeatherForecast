package com.p92rdi.extendedweathertitan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.p92rdi.extendedweathertitan.model.DailyForecast;

import java.util.List;

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
            holder.nightDegTextView5 = (TextView) convertView.findViewById(R.id.nightDegTextView5);
            holder.nightImageView = (ImageView) convertView.findViewById(R.id.nightImageView);
            holder.tvNightDescription = (TextView) convertView.findViewById(R.id.tvNightDescription);
            holder.dayDegTextView5 = (TextView) convertView.findViewById(R.id.dayDegTextView5);
            holder.dayImageView = (ImageView) convertView.findViewById(R.id.dayImageView);
            holder.tvDayDescription = (TextView) convertView.findViewById(R.id.tvDayDescription);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        DailyForecast newItem = getItem(position);

        holder.tvDate.setText(String.valueOf(newItem.getmDate()));
        holder.nightDegTextView5.setText(String.valueOf(newItem.getmTemperatureNight()));
        holder.dayDegTextView5.setText(String.valueOf(newItem.getmTemperatureDay()));
        holder.tvNightDescription.setText(String.valueOf(newItem.getmDescriptionNight()));
        holder.tvDayDescription.setText(String.valueOf(newItem.getmDescriptionDay()));

        return convertView;
    }

    public class Holder {
        TextView tvDate;
        ImageView nightImageView;
        TextView nightDegTextView5;
        TextView dayDegTextView5;
        ImageView dayImageView;
        TextView tvNightDescription;
        TextView tvDayDescription;
    }

}
