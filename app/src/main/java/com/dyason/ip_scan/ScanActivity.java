package com.dyason.ip_scan;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

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
    PostTask scan=null;


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


        //This get's dhcp details
        wifii = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        //Get the SSID via this
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //Pass it to netinfo to get the required information
        //NetworkInfo info = cm.getActiveNetworkInfo();

        //Check if wifi is enabled
        //scaninfo.setText("wifi enabled"+wifii.isWifiEnabled());

        NetInfo getnet = new NetInfo(cm,wifii);
        //Network[] networks = cm.getAllNetworks();
        //LinkProperties temp = cm.getLinkProperties(networks[0]);
        //scaninfo.setText(temp.toString());

        /*for (Network network : networks) {
            LinkProperties linkProperties = cm.getLinkProperties(network);
            List<LinkAddress> addresses = linkProperties.getLinkAddresses();
            for (LinkAddress addr : addresses) {
                //Log.d(TAG, "LP: "+linkProperties.getLinkAddresses().toString());
                //Log.d(TAG, "LPA: "+addr.getAddress().toString());
                Log.d(TAG, "LPB: "+getnet.getIPAddress());
                if (addr.getAddress().toString().contains(getnet.getIPAddress())) {
                    //Log.d(TAG, network.toString());
                    Log.d(TAG, "LPM: "+addr.toString());
                }
            }
        }*/
        //getnet.findNetwork();
        //Log.d(TAG, temp.toString());

        // Maybe check at the previous button?
        /*if (getnet.getSSID() != null ) {
            scaninfo.setText("1"+getnet.toString());
        } else {
            scaninfo.setText("2"+getnet.getSSID());
        }*/

        //loadarp
        //ArpLoad arp = new ArpLoad();

    }



    @Override
    public void onClick(View v) {
        // default method for handling onClick Events..
        switch (v.getId()) {

            case R.id.scanbtn1:
                scan = new PostTask(scanProgresBar1,scaninfo);
                btnScan1.setVisibility(View.GONE);
                btnScan2.setVisibility(View.GONE);
                btnScan3.setVisibility(View.VISIBLE);
                scan.execute("http://feeds.pcworld.com/pcworld/latestnews");
                //ScanHosts myscan = new ScanHosts();
                //myscan.scan(scaninfo);
                break;

            case R.id.scanbtn2:
                Intent i = new Intent(this, AsyncTaskTestActivity.class);
                startActivity(i);
                break;

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
