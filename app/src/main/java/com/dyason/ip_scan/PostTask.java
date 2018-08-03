package com.dyason.ip_scan;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class PostTask extends AsyncTask<String, Integer, String> {

    int hostcount;
    final String TAG = "POSTTASK";
    ProgressBar progressBar;
    TextView textView;

    //con
    public PostTask() {
        super();

    }

    public PostTask(ProgressBar progressBar, TextView textView) {
        super();
        this.progressBar = progressBar;
        this.textView = textView;
    }

    //methods
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        displayProgressBar();
    }

    @Override
    protected String doInBackground(String... params) {

        String subnet = "10.0.120";

        int hostsfound;
        int timeout;
        String url = params[0];
        hostsfound = 0;
        timeout = 50;

        try {

            //info.setText("Scanning: Starting");
            for (int i = 1; i < 255; i++) {
                if (isCancelled())
                    break;
                else {
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
        return "All Done!";

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        hostcount = 255;
        double hostpercent = 0.39;
        double progress = hostpercent * (double) values[0];
        Log.d(TAG, "Devices found: "+values[0]);
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
