package com.luis.edward.airlineapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by Nelson on 8/6/2018.
 */

public class GridAdapter extends BaseAdapter {

    private ArrayList<String> dollar_price;
    private ArrayList<String> place_from_origin;
    private ArrayList<String> place_to_destiny;
    private ArrayList<String> departure_hour_array;
    private ArrayList<String> arrival_hour_array;
    private Context context;
    private LayoutInflater inflater;


    public GridAdapter(Context context, ArrayList<String> dollar_price, ArrayList<String> place_from_origin,ArrayList<String> place_to_destiny, ArrayList<String> departure_hour, ArrayList<String> arrival_hour){
        this.context = context;
        this.dollar_price = dollar_price;
        this.place_from_origin = place_from_origin;
        this.place_to_destiny = place_to_destiny;
        this.departure_hour_array = departure_hour;
        this.arrival_hour_array = arrival_hour;
    }
    @Override
    public int getCount() {
        return dollar_price.size();
    }

    @Override
    public Object getItem(int i) {
        return dollar_price.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView = convertView;
        if(convertView==null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.custom_layout,null);
        }

        TextView price = (TextView) gridView.findViewById(R.id.textView_price);
        TextView place_from = (TextView) gridView.findViewById(R.id.textView_from_trips);
        TextView place_to = (TextView) gridView.findViewById(R.id.textView_to);
        TextView depart_hour = (TextView) gridView.findViewById(R.id.textView_date_trips);
        TextView arrival_hour = (TextView) gridView.findViewById(R.id.textView_hour_arrival);

        price.setText(dollar_price.get(position).toString());
        place_from.setText(place_from_origin.get(position).toString());
        place_to.setText(place_to_destiny.get(position).toString());
        depart_hour.setText(departure_hour_array.get(position).toString());
        arrival_hour.setText(arrival_hour_array.get(position).toString());

        return gridView;
    }
}
