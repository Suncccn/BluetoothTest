package com.example.bluetoothtest;

import android.bluetooth.BluetoothSocket;

import java.util.HashMap;

public class GenenralUtils {
    private static HashMap<String, BluetoothSocket> socketHashMap = new HashMap<>();

    public static void managerBluetoothSocket(String key, BluetoothSocket socket) {
        if (isNotNull(socket) && isNotNullOrZeroLength(key)) {
            socketHashMap.put(key, socket);
        }
    }

    /**
     * if null , 未找到对应的套接字
     * */
    public static BluetoothSocket getBluetoothSocket(String key) {
        if (socketHashMap.containsKey(key)) {
            return socketHashMap.get(key);
        }
        return null;
    }

    public static boolean isNotNull(Object object) {
        if (object != null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotNullOrZeroLength(String str) {
        if (str != null) {
            if (str.length() != 0) {
                return true;
            }
        }
        return false;
    }
}
