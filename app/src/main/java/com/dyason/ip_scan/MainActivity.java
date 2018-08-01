package com.dyason.ip_scan;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.net.*;
import android.net.wifi.WifiManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "YOUR-TAG-NAME";
    TextView info;
    private DhcpInfo d;
    private WifiManager wifii;
    private ConnectivityManager cm;
    private Button btnMine;
    private Button btnAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //final ScanHosts myscan = new ScanHosts();
        /*btnMine.hasOnClickListeners() {
            //@Override
            public void onClick () {
                myscan.scan(info);
            }
        });*/

        setContentView(R.layout.activity_main);

        btnMine = (Button) findViewById(R.id.mybutton);
        btnMine.setOnClickListener(this);

        btnAsync = (Button) findViewById(R.id.btn2);
        btnAsync.setOnClickListener(this);


        info = (TextView) findViewById(R.id.dinfo);
        int num1 = 5;
        float num2 = 2;
        float test = num1 / num2;
        info.setText("Testing"+(int) test);

        //This get's dhcp details
        wifii = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        //Get the SSID via this
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetInfo getnet = new NetInfo(cm, wifii);

        //Get MAC Addresses
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("/proc/net/arp")));
            String total = "";
            String line;
            while((line = br.readLine()) != null) {
                if (!line.contains("00:00:00:00:00:00") && !line.contains("HW address")) {
                    //total += line + "\n";
                    String[] split = line.split("\\s+");
                    //Log.d(TAG, "LineContains: " + line);
                    Log.d(TAG, "IPAddress: " + split[0]+" MACAddress: "+split[3]);
                } else {
                }

            }
            //System.out.println(total);
            //Log.d(TAG, "Total: " + total);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private class PostTask extends AsyncTask<String, Integer, String> {

        int hostcount;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            displayProgressBar();
        }

        @Override
        protected String doInBackground(String... params) {

            String subnet = "10.0.120";
            final String TAG = "YOUR-TAG-NAME";
            int hostsfound;
            int timeout;
            String url = params[0];
            hostsfound = 0;
            timeout = 50;

            try {

                //info.setText("Scanning: Starting");
                for (int i = 1; i < 255; i++) {
                    String host = subnet + "." + i;
                    if (InetAddress.getByName(host).isReachable(timeout)) {
                        hostsfound++;
                        publishProgress(i,hostsfound);
                        //info.setText("Scanning: " + hostsfound + " devices found");
                        Log.d(TAG, "checkHosts() :: " + host + " is reachable");
                    } else {
                        //info.setText("Scanning: Something not right " + i);
                        publishProgress(i,hostsfound);
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
        /*protected String doInBackground(String... params) {
            String url=params[0];

            // Dummy code
            for (int i = 0; i <= 100; i += 5) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress("Currently:" +i);
            }
            return "All Done!";
        }*/
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            hostcount = 255;
            double hostpercent = 0.39;
            double progress = hostpercent * (double) values[0];
            Log.d(TAG, "Progress: "+hostpercent+" Host: "+values[0]);
            updateProgressBar((int) progress);
            info.setText("Progress: "+hostpercent+"Hosts: "+values[1]);
        }
        /*protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            if (values != null && values.length > 0) {
                //Your View attribute object in the activity
                // already initialized in the onCreate!
                info.setText(values[0]);
            }
        }*/

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dismissProgressBar();
        }

        protected void displayProgressBar(){
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
    }

    @Override
    public void onClick(View v) {
        // default method for handling onClick Events..
        switch (v.getId()) {

            case R.id.mybutton:
                new PostTask().execute("http://feeds.pcworld.com/pcworld/latestnews");
                //ScanHosts myscan = new ScanHosts();
                //myscan.scan(info);
                break;

            case R.id.btn2:
                Intent i = new Intent(this, AsyncTaskTestActivity.class);
                startActivity(i);

            default:
                break;
        }
    }
}
