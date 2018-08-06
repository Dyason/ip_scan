package com.dyason.ip_scan;

import android.util.Log;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ArpLoad {

    //var
    private String path="/proc/net/arp";
    public static final String TAG = "ARPLOAD";
    private ArrayList<Device> arplist = new ArrayList<Device>();

    //con
    public ArpLoad() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(path)));
            String total = "";
            String line;
            while((line = br.readLine()) != null) {
                String[] split = line.split("\\s+");
                if (!line.contains("00:00:00:00:00:00") && !line.contains("HW address") && split[3].length()==17) {
                    //total += line + "\n";
                    //String[] split = line.split("\\s+");
                    arplist.add(new Device(split[0].trim(),split[3].trim()));
                    //Log.d(TAG, "LineContains: " + line);
                    //Log.d(TAG, "IPAddress: " + split[0]+" MACAddress: "+split[3]);
                } else {
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //meth
    public ArrayList<Device> getArplist() {
        return arplist;
    }

    public String getMacAddress(String ip) {
        String macaddress = "None found";
        for(Device d : arplist){
            if(d.getIpaddress() != null && d.getIpaddress().matches(ip)) {
                //Log.d(TAG, "IP: "+ip+"IPAddress: " + d.getIpaddress()+" MACAddress: "+d.getMacaddress());
                macaddress = d.getMacaddress();
            }
            //Log.d(TAG, "None Matching IP: "+ip+"IPAddress: " + d.getIpaddress()+" MACAddress: "+d.getMacaddress());
        }
        return macaddress;
    }
}
