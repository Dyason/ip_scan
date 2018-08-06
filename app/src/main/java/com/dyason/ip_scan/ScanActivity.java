package com.dyason.ip_scan;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ScanActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "SCANACTIVITY";
    TextView scaninfo;
    private DhcpInfo d;
    private WifiManager wifii;
    private ConnectivityManager cm;
    private Button btnScan1;
    private Button btnScan2;
    private Button btnScan3;
    private ProgressBar scanProgresBar1;
    ScanHosts scan=null;
    ArrayList<Device> devicelist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_scan);

        //buttons
        btnScan1 = (Button) findViewById(R.id.scanbtn1);
        btnScan1.setOnClickListener(this);

        btnScan2 = (Button) findViewById(R.id.scanbtn2);
        btnScan2.setOnClickListener(this);

        btnScan3 = (Button) findViewById(R.id.scanbtn3);
        btnScan3.setOnClickListener(this);


        //Progress bar
        scanProgresBar1 = (ProgressBar) findViewById(R.id.scanprogressBar1);

        //text
        scaninfo = (TextView) findViewById(R.id.scaninfo);


        //Initiate context info to pass to Network Module
        //DHCP info here
        wifii = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        //Get the SSID via this
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //Check if wifi is enabled
        //scaninfo.setText("wifi enabled"+wifii.isWifiEnabled());

        NetInfo getnet = new NetInfo(cm,wifii);

        scaninfo.setText(getnet.getSSID());

        getnet.findNetwork();
        //Log.d(TAG, cm.getAllNetworks().toString());

        // Maybe check at the previous button?
        /*if (getnet.getSSID() != null ) {
            scaninfo.setText("1"+getnet.toString());
        } else {
            scaninfo.setText("2"+getnet.getSSID());
        }*/

        //loadarp
        ArpLoad arp = new ArpLoad();

    }

    @Override
    public void onClick(View v) {

        //DHCP info here
        wifii = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        //Get the SSID via this
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetInfo getnet = new NetInfo(cm,wifii);

        // default method for handling onClick Events..
        switch (v.getId()) {

            case R.id.scanbtn1:
                //scan = new ScanHosts(scanProgresBar1,scaninfo);
                scan = new ScanHosts(scanProgresBar1,scaninfo,getnet.getIPAddress(),getnet.findMACAddress());
                btnScan1.setVisibility(View.GONE);
                btnScan2.setVisibility(View.GONE);
                btnScan3.setVisibility(View.VISIBLE);


                scan.execute(getnet.findNetwork());
                //ScanHosts myscan = new ScanHosts();
                //myscan.scan(scaninfo);
                break;

            case R.id.scanbtn2:
                scan = new ScanHosts(scanProgresBar1,scaninfo,getnet.getIPAddress(),getnet.get_macaddress() ,getnet.getSSID());
                btnScan1.setVisibility(View.GONE);
                btnScan2.setVisibility(View.GONE);
                btnScan3.setVisibility(View.VISIBLE);
                break;
                /*Intent i = new Intent(this, AsyncTaskTestActivity.class);
                startActivity(i);
                break;*/

            case R.id.scanbtn3:
                scan.cancel(true);
                scanProgresBar1.setVisibility(View.GONE);
                btnScan3.setVisibility(View.GONE);
                btnScan1.setVisibility(View.VISIBLE);
                btnScan2.setVisibility(View.VISIBLE);
                break;

            default:
                break;
        }
    }
}
