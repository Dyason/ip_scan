package com.dyason.ip_scan;

import android.net.LinkProperties;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.io.IOException;

public class ScanHosts extends AsyncTask<String, Integer, String> {

    //var

    private String subnet = "10.0.120";
    public static final String TAG = "YOUR-TAG-NAME";
    private int hostsfound;
    private int timeout;

    //con
    public ScanHosts() {
        hostsfound = 0;
        timeout = 50;
    }

    @Override
    protected String doInBackground(String... params) {
        String url = params[0];

        try {

            //info.setText("Scanning: Starting");
            for (int i = 1; i < 255; i++) {
                String host = subnet + "." + i;
                if (InetAddress.getByName(host).isReachable(timeout)) {
                    hostsfound++;
                    //info.setText("Scanning: " + hostsfound + " devices found");
                    Log.d(TAG, "checkHosts() :: " + host + " is reachable");
                } else {
                    //info.setText("Scanning: Something not right " + i);
                    Log.d(TAG, "checkHosts() :: " + host + " is unreachable");
                }
                //info.setText("Finished: Devices found "+hostsfound);
            }
        } catch (UnknownHostException e) {
            Log.d(TAG, "checkHosts() :: UnknownHostException e : " + e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, "checkHosts() :: IOException e : " + e);
            e.printStackTrace();
        }
        // Dummy code
        /*for (int i = 0; i <= 100; i += 5) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(i);
        }*/
        return "All Done!";

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
                    Log.d(TAG, "checkHosts() :: " + host + " is unreachable");
                }
                info.setText("Finished: Devices found " + hostsfound);
            }
        } catch (UnknownHostException e) {
            Log.d(TAG, "checkHosts() :: UnknownHostException e : " + e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, "checkHosts() :: IOException e : " + e);
            e.printStackTrace();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        //updateProgressBar(values[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //dismissProgressBar();
    }

    /*protected void displayProgressBar(){
        ProgressBar bar=(ProgressBar)findViewById(R.id.progressBar2);
        bar.setVisibility(View.VISIBLE); //View.INVISIBLE, or View.GONE to hide it.
    }

    protected void updateProgressBar(Integer... values){
        ProgressBar bar=(ProgressBar)findViewById(R.id.progressBar2);
        bar.setProgress(values[0]);
    }

    protected void dismissProgressBar(){
        ProgressBar bar=(ProgressBar)findViewById(R.id.progressBar2);
        bar.setVisibility(View.VISIBLE); //View.INVISIBLE, or View.GONE to hide it.
    }
    public int getHostsfound() {
        return hostsfound;*/
}
//meth


