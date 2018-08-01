package com.dyason.ip_scan;

import android.net.LinkProperties;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.net.SocketException;


public class NetInfo {

        //var
    private String   s_gateway;
    private String   s_ipAddress;
    private String   s_netmask;
    private String   s_ssid;

    public DhcpInfo d;
    private WifiManager wifii;
    private ConnectivityManager cm;
    public static final String TAG = "YOUR-TAG-NAME";

    private String  export;

        //con
    public NetInfo(ConnectivityManager cm,WifiManager wifii)  {

        /*try {
            for (Enumeration<NetworkInterface> en =
                 NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                // Iterate over all IP addresses in each network interface.
                for (Enumeration<InetAddress> enumIPAddr =
                     intf.getInetAddresses(); enumIPAddr.hasMoreElements(); ) {
                    InetAddress iNetAddress = enumIPAddr.nextElement();
                    // Loop back address (127.0.0.1) doesn't count as an in-use IP address.
                    if (!iNetAddress.isLoopbackAddress()) {
                        String sLocalIP = iNetAddress.getHostAddress().toString();
                        String sInterfaceName = intf.getName();
                        s_netmask = intf.getInetAddresses().toString();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }*/
        /*try {
            InetAddress inetaddress = InetAddress;
            NetworkInterface networkInterface = NetworkInterface.getByName("wlan0");
            for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
                //short netPrefix = address.getNetworkPrefixLength();
                //Log.d(TAG, address.toString());
                s_netmask= address.getBroadcast().toString();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }*/
        d=wifii.getDhcpInfo();
        //s_dns1="DNS 1: "+intToIp(d.dns1);
        //s_dns2="DNS 2: "+intToIp(d.dns2);
        s_gateway="Default Gateway: "+intToIp(d.gateway);
        s_ipAddress="IP Address: "+intToIp(d.ipAddress);
        //s_leaseDuration="Lease Time: "+String.valueOf(d.leaseDuration);
        //s_netmask="Subnet Mask: "+String.valueOf(d.netmask);
        //s_serverAddress="Server IP: "+intToIp(d.serverAddress);


        //Do a test to check actually on wifi
        NetworkInfo ninfo = cm.getActiveNetworkInfo();
        if(ninfo!=null && ninfo.isConnected()) {
            String s_ssid = ninfo.getExtraInfo();
        }
        s_ssid="SSID: "+cm.getActiveNetworkInfo().getExtraInfo();
        /*try {
            InetAddress inetAddress = InetAddress.getByAddress(d.ipAddress);
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(inetAddress);
            for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
                //short netPrefix = address.getNetworkPrefixLength();
                //Log.d(TAG, address.toString());
                s_netmask=address.toString();
            }
        } catch (SocketException e) {
            Log.e(TAG, e.getMessage());
        }*/

    }


    public String getInfo() {
        //export="Network Info\\n\"+s_dns1+\"\\n\"+s_dns2+\"\\n\"+s_gateway+\"\\n\"+s_ipAddress+\"\\n\"+s_leaseDuration+\"\\n\"+s_netmask+\"\\n\"+s_serverAddress";
        export=s_ssid;
        return s_netmask;
    }

    //Converts to a pretty ip address
    public String intToIp(int addr) {
        return  ((addr & 0xFF) + "." +
                ((addr >>>= 8) & 0xFF) + "." +
                ((addr >>>= 8) & 0xFF) + "." +
                ((addr >>>= 8) & 0xFF));
    }
        //meth
        // Helper method to extract bytes from byte array.
        /*private static byte[] extractBytes(byte[] scanRecord, int start, int length) {
            byte[] bytes = new byte[length];
            System.arraycopy(scanRecord, start, bytes, 0, length);
            return bytes;
        }*/

        public String toString() {
            return s_gateway + s_ipAddress + s_netmask + s_ssid;
        }
}
