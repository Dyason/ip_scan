package com.dyason.ip_scan;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.dyason.ip_scan.R;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

public class ScanNI extends AppCompatActivity {

    private Button btnScan;
    private ListView listViewNI;

    ArrayList<String> niList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan);

        btnScan = (Button)findViewById(R.id.scan);
        listViewNI = (ListView)findViewById(R.id.listviewni);


        niList = new ArrayList();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, niList);
        listViewNI.setAdapter(adapter);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ScanNITask().execute();
            }
        });
    }


    private class ScanNITask extends AsyncTask<Void, String, Void> {

        @Override
        protected void onPreExecute() {
            niList.clear();
            adapter.notifyDataSetInvalidated();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {

                    NetworkInterface thisInterface = networkInterfaces.nextElement();
                    Enumeration<InetAddress> inetAddresses = thisInterface.getInetAddresses();
                    if (inetAddresses.hasMoreElements()) {
                        String niInfo = thisInterface.getDisplayName() + "\n";

                        while (inetAddresses.hasMoreElements()) {
                            InetAddress thisAddress = inetAddresses.nextElement();
                            niInfo += "---\n";
                            niInfo += "Address: " + thisAddress.getAddress() + "\n";
                            niInfo += "CanonicalHostName: " + thisAddress.getCanonicalHostName() + "\n";
                            niInfo += "HostAddress: " + thisAddress.getHostAddress() + "\n";
                            niInfo += "Subnet: " + thisAddress.getHostName() + "\n";

                        }

                        publishProgress(niInfo);
                    }


                }
            } catch (SocketException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            niList.add(values[0]);
            adapter.notifyDataSetInvalidated();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(ScanNI.this, "Done", Toast.LENGTH_LONG).show();
        }
    }
}