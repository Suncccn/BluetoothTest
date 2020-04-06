package com.example.bluetoothtest.thread;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;

import com.example.bluetoothtest.GenenralUtils;

import java.io.IOException;
import java.util.UUID;

public class ClinetThread extends Thread {
    private BluetoothDevice mDevice;
    private BluetoothSocket mScoket;
    private UUID uuid;

    public ClinetThread(BluetoothDevice device) {
        this.mDevice = device;
        ParcelUuid[] uuids = mDevice.getUuids();
        if (uuids.length != 0) {
            uuid = uuids[0].getUuid();
            try {
                mScoket = mDevice.createRfcommSocketToServiceRecord(uuid);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        super.run();
        BluetoothAdapter.getDefaultAdapter().cancelDiscovery(); //在连接前应始终调用该方法
        try {
            mScoket.connect();
        } catch (IOException e) {
            try{
                mScoket.close();
            }catch (IOException closeException){
                closeException.printStackTrace();
                return;
            }
        }
        //定义管理蓝牙套接字的集合
        GenenralUtils.managerBluetoothSocket(uuid.toString(),mScoket);
    }

    public void cancle(){
        try {
            mScoket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
