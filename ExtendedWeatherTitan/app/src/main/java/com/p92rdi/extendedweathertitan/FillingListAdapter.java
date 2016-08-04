package com.p92rdi.extendedweathertitan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by antalicsp on 2016. 08. 03..
 */
public class FillingListAdapter extends ArrayAdapter<Mok2> {

    private List<Mok2> fillings;
    private LayoutInflater inflater;

    public FillingListAdapter(Context context, List<Mok2> fillings) {
        super(context, R.layout.list_forecast, fillings);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.fillings = fillings;
    }

    @Override
    public int getCount() {
        return fillings.size();
    }

    @Override
    public Mok2 getItem(int position) {
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
            holder.minImageView = (ImageView) convertView.findViewById(R.id.minImageView);
            holder.minDegTextView5 = (TextView) convertView.findViewById(R.id.minDegTextView5);
            holder.maxDegTextView5 = (TextView) convertView.findViewById(R.id.maxDegTextView5);
            holder.maxImageView = (ImageView) convertView.findViewById(R.id.maxImageView);
            holder.tvminDescription = (TextView) convertView.findViewById(R.id.tvMinDescription);
            holder.tvmaxDescription = (TextView) convertView.findViewById(R.id.tvMaxDescription);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        Mok2 newItem = getItem(position);

        holder.tvDate.setText(String.valueOf(newItem.getFirst()));
        holder.minDegTextView5.setText(String.valueOf(newItem.getSecond()));
        holder.maxDegTextView5.setText(String.valueOf(newItem.getFirst()));
        holder.tvminDescription.setText(String.valueOf(newItem.getSecond()));
        holder.tvmaxDescription.setText(String.valueOf(newItem.getSecond()));

        return convertView;
    }

    public class Holder {
        TextView tvDate;
        ImageView minImageView;
        TextView minDegTextView5;
        TextView maxDegTextView5;
        ImageView maxImageView;
        TextView tvminDescription;
        TextView tvmaxDescription;
    }

}
