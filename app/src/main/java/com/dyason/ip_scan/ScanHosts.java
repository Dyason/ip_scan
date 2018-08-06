package com.dyason.ip_scan;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.IOException;
import java.util.ArrayList;

public class ScanHosts extends AsyncTask<String, Integer, String> {


    ArrayList<Device> devices;
    ArrayList<String> devices_ip;
    int hostcount;
    final String TAG = "SCANHOSTS";
    ProgressBar progressBar;
    TextView textView;
    String myip;
    String mymac;

    //con
    public ScanHosts() {
        super();

    }

    public ScanHosts(ProgressBar progressBar, TextView textView, String myip, String mymac) {
        super();
        this.progressBar = progressBar;
        this.textView = textView;
        this.myip=myip;
        this.mymac=mymac;
        this.devices=devices;
    }

    public ScanHosts(ProgressBar progressBar, TextView textView, String myip, String mymac, String ssid) {
        super();
        this.progressBar = progressBar;
        this.textView = textView;
        this.myip=myip;
        this.mymac = mymac;
        this.devices=devices;

        //Load devices already labelled
    }

    //methods
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        displayProgressBar();
    }

    @Override
    protected String doInBackground(String... params) {

        int timeout;
        String url = params[0];
        ArrayList<String> hosts;
        timeout = 200;
        int hostsfound = 0;

        try {

            devices_ip = new ArrayList<String>();
            IPconv network = new IPconv(url);
            hosts = network.getHosts();
            //info.setText("Scanning: Starting");
            //int hostcount = hosts.size();
            int hostcount = 10;
            //Just do 10 for now then change back to host.size();
            for (int i = 0; i < hostcount; i++) {
                if (isCancelled())
                    break;
                if (InetAddress.getByName(hosts.get(i)).isReachable(timeout)) {
                    hostsfound++;
                    devices_ip.add(hosts.get(i));
                    publishProgress(i,hostsfound,hostcount);
                    //Log.d(TAG, "checkHosts() :: " + hosts.get(i) + " is reachable");
                } else {
                    publishProgress(i,hostsfound,hostcount);
                    //Log.d(TAG, "checkHosts() :: " + hosts.get(i) + " is unreachable");
                }
            }
            // Get them and put them in a table with MAC addresses now
            ArpLoad lookup = new ArpLoad();
            for (int i=0; i < devices_ip.size();i++) {
                //Won't have it's own macaddress so use info we have
                //Log.d(TAG, "MyIPAddress: " + devices_ip.get(i)+" Mine: "+myip+"MyMac"+mymac);
                if (devices_ip.get(i).matches(myip)) {
                    devices.add(new Device(devices_ip.get(i),mymac.toLowerCase()));
                    Log.d(TAG, "IPAddress: " + devices_ip.get(i)+" MACAddress: "+mymac.toLowerCase());
                }
                if (lookup.getMacAddress(devices_ip.get(i))!= null && !devices_ip.get(i).matches(myip)) {
                    devices.add(new Device(devices_ip.get(i),mymac.toLowerCase()));
                    Log.d(TAG, "IPAddress: " + devices_ip.get(i)+" MACAddress: "+lookup.getMacAddress(devices_ip.get(i)));

                }
            }
        } catch (UnknownHostException e) {
            Log.d(TAG, "checkHosts() :: UnknownHostException e : " + e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, "checkHosts() :: IOException e : " + e);
            e.printStackTrace();
        }
        return "All Done!";

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        double hostpercent = 100 / (double) values[2];
        double progress = hostpercent * (double) values[0];
        //Log.d(TAG, "Devices found: "+values[0]);
        //Log.d(TAG, "Devices found: "+progress);
        updateProgressBar((int) progress);
        textView.setText("Devices found: "+values[1]);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        dismissProgressBar();
    }

    protected void displayProgressBar(){
        //ProgressBar bar=(ProgressBar)findViewById(R.id.scanprogressBar1);
        progressBar.setVisibility(View.VISIBLE); //View.INVISIBLE, or View.GONE to hide it.
    }

    protected void updateProgressBar(Integer... values){
        //ProgressBar bar=(ProgressBar)findViewById(R.id.scanprogressBar1);
        progressBar.setProgress(values[0]);
    }

    protected void dismissProgressBar(){
        //ProgressBar bar=(ProgressBar)findViewById(R.id.scanprogressBar1);
        progressBar.setVisibility(View.INVISIBLE); //View.INVISIBLE, or View.GONE to hide it.
    }
}



