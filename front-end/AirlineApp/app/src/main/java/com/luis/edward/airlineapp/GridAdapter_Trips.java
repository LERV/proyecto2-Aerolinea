package com.luis.edward.airlineapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class GridAdapter_Trips extends BaseAdapter {
    private ArrayList<String> place_from_origin;
    private ArrayList<String> place_to_destiny;
    private ArrayList<String> array_date;
    private ArrayList<String> array_kilometers;
    private Context context;
    private LayoutInflater inflater;


    public GridAdapter_Trips(Context context,ArrayList<String> place_from_origin, ArrayList<String> place_to_destiny, ArrayList<String> date, ArrayList<String> kilometers){
        this.context = context;
        this.place_from_origin = place_from_origin;
        this.place_to_destiny = place_to_destiny;
        this.array_date = date;
        this.array_kilometers = kilometers;
    }
    @Override
    public int getCount() {
        return place_from_origin.size();
    }

    @Override
    public Object getItem(int i) {
        return place_from_origin.get(i);
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
            gridView = inflater.inflate(R.layout.custom_my_trips,null);
        }

        TextView place_from = (TextView) gridView.findViewById(R.id.textView_from_trips);
        TextView place_to = (TextView) gridView.findViewById(R.id.textView_to_trips);
        TextView tx_date = (TextView) gridView.findViewById(R.id.textView_date_trips);
        TextView tx_kilometers = (TextView) gridView.findViewById(R.id.textView_kilometers_trips);

        place_from.setText(place_from_origin.get(position).toString());
        place_to.setText(place_to_destiny.get(position).toString());
        tx_date.setText(array_date.get(position).toString());
        tx_kilometers.setText(array_kilometers.get(position).toString());

        return gridView;
    }
}
