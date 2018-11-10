package com.rperazzo.weatherapp.Model;

import com.rperazzo.weatherapp.Util.IConnectivityUtil;

public class Connectivity implements IConnectivityUtil {
    @Override
    public boolean isDeviceConnected() {
        return false;
    }
}
