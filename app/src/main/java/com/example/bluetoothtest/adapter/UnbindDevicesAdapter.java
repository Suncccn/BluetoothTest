package com.example.bluetoothtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bluetoothtest.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UnbindDevicesAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, String>> data;
    int layout;

    public UnbindDevicesAdapter(Context context, ArrayList<HashMap<String, String>> data, int layout) {
        this.context = context;
        this.data = data;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return data == null ? null : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data == null ? null : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View inflate = LayoutInflater.from(context).inflate(layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        myViewHolder.name.setText(data.get(position).get("name"));
        myViewHolder.address.setText(data.get(position).get("address"));
        return inflate;
    }

    public static class MyViewHolder{
        TextView name,address;
        public MyViewHolder(View v){
            name = v.findViewById(R.id.device_name);
            address = v.findViewById(R.id.device_address);
        }
    }
}
