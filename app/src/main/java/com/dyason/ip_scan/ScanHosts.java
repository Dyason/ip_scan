package com.dyason.ip_scan;

import android.net.LinkProperties;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.TextView;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.io.IOException;

public class ScanHosts {

    //var

    private String subnet = "10.0.120";
    public static final String TAG = "YOUR-TAG-NAME";
    private int hostsfound;
    private int timeout;
    //con
    public ScanHosts() {
        hostsfound = 0;
        timeout = 1;
    }

    public void scan(TextView info) {
        try {

            info.setText("Scanning: Starting");
            for (int i = 1; i < 255; i++) {
                String host = subnet + "." + i;
                if (InetAddress.getByName(host).isReachable(timeout)) {
                    hostsfound++;
                    info.setText("Scanning: " + hostsfound + " devices found");
                    Log.d(TAG, "checkHosts() :: " + host + " is reachable");
                } else {
                    info.setText("Scanning: Something not right " + i);
                }
            }
        } catch (UnknownHostException e) {
            Log.d(TAG, "checkHosts() :: UnknownHostException e : " + e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, "checkHosts() :: IOException e : " + e);
            e.printStackTrace();
        }
    }

    public int getHostsfound() {
        return hostsfound;
    }
    //meth
}

