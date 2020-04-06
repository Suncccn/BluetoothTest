package com.example.bluetoothtest.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.bluetoothtest.R;
import com.example.bluetoothtest.adapter.UnbindDevicesAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class UnbindDevicesActivity extends AppCompatActivity {
    ListView lvUbind;
    private BluetoothAdapter defaultAdapter;
    public static final String TAG = "scc";
    ArrayList<HashMap<String, String>> list = new ArrayList<>();
    UnbindDevicesAdapter adapter = new UnbindDevicesAdapter(this, list, R.layout.item_device);

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // 从 Intent 中获取发现的 BluetoothDevice
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);   //蓝牙设备信息用于蓝牙连接用
                ParcelUuid[] uuids = device.getUuids();
                // 将名字和地址放入要显示的适配器中
                HashMap<String, String> map = new HashMap<>();
                map.put("name", device.getName());
                map.put("address", device.getAddress());
                list.add(map);
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unbind_devices);
        initView();
        initReceiver();
        startSearchBuletooth();

    }

    private void initView() {
        lvUbind = findViewById(R.id.lv_unbind);
        lvUbind.setAdapter(adapter);
    }

    public void initReceiver() {
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, intentFilter);
    }

    public void startSearchBuletooth() {
        defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            Log.d(TAG, "无蓝牙设备");
        } else {
            if (defaultAdapter.isEnabled()) {
                if (Build.VERSION.SDK_INT >= 23) {  //android6.0以上需要动态获取位置权限
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0x0001);
                    }else{
                        boolean b = defaultAdapter.startDiscovery();
                        Toast.makeText(this, b + "", Toast.LENGTH_LONG).show();
                    }
                }else{
                    boolean b = defaultAdapter.startDiscovery();
                    Toast.makeText(this, b + "", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "蓝牙设备未打开", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void cancleSearch(View v) {
        boolean b = defaultAdapter.cancelDiscovery();
        Toast.makeText(this, b + "", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (defaultAdapter.isDiscovering())
            defaultAdapter.cancelDiscovery();
        unregisterReceiver(receiver);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 0x0001:
                boolean b = defaultAdapter.startDiscovery();
                Toast.makeText(this, b + "", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
