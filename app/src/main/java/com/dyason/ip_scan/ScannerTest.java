package com.dyason.ip_scan;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;



public class ScannerTest {

    public static void checkHosts(String subnet) throws UnknownHostException, IOException{
        int timeout=10;
        int devicesfound=0;



        for (int i=1;i<255;i++){
            String host=subnet + "." + i;
            System.out.println("Scanning " + i + " of 255");
            if (InetAddress.getByName(host).isReachable(timeout)){
                devicesfound++;
                //System.out.println(host + " is reachable");
            }
        }
        //System.out.println("Devices found: " + devicesfound);
    }

}