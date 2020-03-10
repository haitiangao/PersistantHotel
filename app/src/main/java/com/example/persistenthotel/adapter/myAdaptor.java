package com.example.persistenthotel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.persistenthotel.R;
import com.example.persistenthotel.model.Guest;

import java.util.List;

public class myAdaptor extends BaseAdapter {

    private List<Guest> name;

    public myAdaptor(List<Guest> name){
        this.name = name;
    }


    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int position) {
        return name.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.guest_item_layout, parent, false);


        ((TextView) view.findViewById(R.id.prefix_textview)).setText(
                name.get(position).getPrefix());

        ((TextView) view.findViewById(R.id.name_textview)).setText(
                name.get(position).getActualName());
        return view;

    }


}
