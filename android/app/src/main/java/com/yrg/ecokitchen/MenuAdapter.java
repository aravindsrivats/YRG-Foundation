package com.yrg.ecokitchen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yrg.ecokitchen.models.MenuList;

import java.util.ArrayList;

public class MenuAdapter extends ArrayAdapter<MenuList> {

    private final Context context;
    private final ArrayList<MenuList> items;

    public MenuAdapter(Context context, ArrayList<MenuList> items) {
        super(context, R.layout.list_item, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = null;
        if(!items.get(position).isGroupHeader) {
            rowView = inflater.inflate(R.layout.list_item, parent, false);
            TextView titleView = (TextView) rowView.findViewById(R.id.item_title);
            titleView.setText(items.get(position).title);
        }
        else {
            rowView = inflater.inflate(R.layout.list_divider, parent, false);
            TextView titleView = (TextView) rowView.findViewById(R.id.header);
            titleView.setText(items.get(position).title);

        }
        return rowView;
    }
}

