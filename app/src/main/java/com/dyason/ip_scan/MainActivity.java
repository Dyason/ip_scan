package com.dyason.ip_scan;

import android.content.Context;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.net.*;
import android.net.wifi.WifiManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class MainActivity extends AppCompatActivity  {

    TextView info;
    private DhcpInfo d;
    private WifiManager wifii;
    private ConnectivityManager cm;

    private Button btnMine;

    public static final String TAG = "YOUR-TAG-NAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btnMine = (Button)findViewById(R.id.mybutton);
        info = (TextView) findViewById(R.id.dinfo);

        //final ScanHosts myscan = new ScanHosts();
        /*btnMine.hasOnClickListeners() {
            //@Override
            public void onClick() {
                myscan.scan(info);
            }
        });*/

        setContentView(R.layout.activity_main);

        //This get's dhcp details
        wifii = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        //Get the SSID via this
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetInfo getnet = new NetInfo(cm,wifii);


        /*try {
            if (InetAddress.getByName("10.0.120.1").isReachable(5)) {
                info.setText("True");
            } else {
                info.setText("False");
            }
        } catch (UnknownHostException e) {
            Log.d(TAG, "checkHosts() :: UnknownHostException e : " + e);
        } catch (IOException e) {
            Log.d(TAG, "checkHosts() :: IOException e : " + e);
        }*/

        //ScannerTest scan;
        //checkHosts("10.0.120");

    }
}
