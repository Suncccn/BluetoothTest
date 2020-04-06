package com.example.bluetoothtest.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.bluetoothtest.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DevicesListActivity extends AppCompatActivity {
    ListView lvDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_list);
        initView();
        initData();
    }

    public void initView(){
        lvDevice = findViewById(R.id.lv_list);
    }

    public void initData(){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null){
            Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();   //获取已配对的蓝牙设备
            List<HashMap<String,String>> list = new ArrayList<>();
            for (BluetoothDevice item : bondedDevices){
                HashMap<String,String> map = new HashMap<>();
                map.put("name",item.getName());
                map.put("address",item.getAddress());
                list.add(map);
            }
            SimpleAdapter simpleAdapter = new SimpleAdapter(this,list,R.layout.item_device,new String[]{"name","address"},new int[]{R.id.device_name,R.id.device_address});
            lvDevice.setAdapter(simpleAdapter);
        }
    }
}
