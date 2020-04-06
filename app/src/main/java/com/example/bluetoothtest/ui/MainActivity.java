package com.example.bluetoothtest.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.bluetoothtest.R;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "scc";
    public static final int REQUEST_ENABLE_CODE = 0x001;
    public static final int REQUEST_DISCOVERABLE_CODE = 0x002;
    private BluetoothAdapter defaultAdapter;

    //监听蓝牙状态，分为 当前状态和之前状态
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
            int previousState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_STATE, 0);
//            Toast.makeText(MainActivity.this, "State: " + state + "Previous State:" + previousState, Toast.LENGTH_LONG).show();
        }
    };

    BroadcastReceiver receiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            /**
             * SCAN_MODE_CONNECTABLE_DISCOVERABLE(可检测到模式)
             * SCAN_MODE_CONNECTABLE(未处于可检测模式但可以接受连接)
             * SCAN_MODE_NOE(未处于可检测到模式并且无法连接)
             * */
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE,0);
            int previousState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_SCAN_MODE,0);
            Toast.makeText(MainActivity.this, "Discoverable_State: " + state + "Previous State:" + previousState, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取蓝牙状态
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(receiver, intentFilter);

        //获取可检测模式变化
        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        registerReceiver(receiver1,intentFilter1);

        //获取蓝牙适配器
        defaultAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    //初始化蓝牙
    public void initBluetooth(View v) {
        if (defaultAdapter == null) {
            Log.d(TAG, "无蓝牙设备");
        } else {
            if (!defaultAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_CODE);    //请求打开蓝牙
            }
        }
    }

    //获取已配对的蓝牙列表
    public void getDeviceList(View v){
        if (defaultAdapter == null){
            Log.d(TAG, "无蓝牙设备");
        }else{
            if (defaultAdapter.isEnabled()){
                Intent intent = new Intent(this, DevicesListActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(MainActivity.this, "蓝牙设备未打开", Toast.LENGTH_LONG).show();
            }
        }
    }

    //获取未配对的蓝牙列表
    public void getUnbindDevices(View view){
        Intent intent = new Intent(this, UnbindDevicesActivity.class);
        startActivity(intent);

    }

    //打开系统蓝牙界面
    public void openBluetoothOfSetting(View v){
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_BLUETOOTH_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try{
            startActivity(intent);
        } catch(ActivityNotFoundException ex){
            ex.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //启用可检测性
    //如果没有打开蓝牙，设备会自动启动蓝牙
    public void openDiscoverable(View v){
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,300);//设置可检测时间，120s～3600s
        startActivityForResult(discoverableIntent,REQUEST_DISCOVERABLE_CODE);//结果代码 RESULT_CANCELED(0)
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_CODE) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        unregisterReceiver(receiver1);
    }
}
