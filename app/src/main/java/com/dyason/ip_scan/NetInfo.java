package com.dyason.ip_scan;

import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.net.wifi.WifiInfo;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.net.SocketException;
import java.util.List;


public class NetInfo {

        //var
    private String   s_gateway;
    private String   s_ipAddress;
    private String   s_netmask;
    private String   s_ssid;
    private String   s_macaddress;

    public DhcpInfo d;
    private WifiManager wifii;
    private ConnectivityManager cm;
    public static final String TAG = "NETINFO";

        //con
    public NetInfo(ConnectivityManager cm,WifiManager wifii)  {
        this.cm = cm;
        this.wifii = wifii;

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
        s_gateway="Default Gateway: "+intToIp(d.gateway).trim();
        s_ipAddress=intToIp(d.ipAddress).trim();
        //s_leaseDuration="Lease Time: "+String.valueOf(d.leaseDuration);
        //s_netmask="Subnet Mask: "+String.valueOf(d.netmask);
        //s_serverAddress="Server IP: "+intToIp(d.serverAddress);


        //Do a test to check actually on wifi
        NetworkInfo ninfo = cm.getActiveNetworkInfo();
        if(ninfo!=null && ninfo.isConnected()) {
            String s_ssid = ninfo.getExtraInfo();
        }
        //s_ssid="SSID: "+cm.getActiveNetworkInfo().getExtraInfo();
        WifiInfo wifiInfo = wifii.getConnectionInfo();
        s_macaddress=wifiInfo.getMacAddress();
        s_ssid=wifiInfo.getSSID();
    }


    public String getIPAddress() {
        return s_ipAddress;
    }

    public String getInfo() {
        //export="Network Info\\n\"+s_dns1+\"\\n\"+s_dns2+\"\\n\"+s_gateway+\"\\n\"+s_ipAddress+\"\\n\"+s_leaseDuration+\"\\n\"+s_netmask+\"\\n\"+s_serverAddress";
        return s_netmask;
    }

    public String getSSID() {
        return s_ssid;
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

    public String findNetwork() {
        String netaddr;
        netaddr = "";
        //NetInfo getnet = new NetInfo(cm, wifii);
        Network[] networks = cm.getAllNetworks();
        for (Network network : networks) {
            LinkProperties linkProperties = cm.getLinkProperties(network);
            List<LinkAddress> addresses = linkProperties.getLinkAddresses();
            for (LinkAddress addr : addresses) {
                //Log.d(TAG, "LP: "+linkProperties.getLinkAddresses().toString());
                //Log.d(TAG, "LPA: "+addr.getAddress().toString());
                //Log.d(TAG, "LPB: " + getIPAddress());
                if (addr.getAddress().toString().contains(getIPAddress())) {
                    //Log.d(TAG, network.toString());
                    netaddr = addr.toString();
                    Log.d(TAG, "LPM: " + netaddr);

                    //String example = netaddr;
                    //System.out.println(example.substring(example.lastIndexOf("/") + 1));
                }
            }
        }
        s_netmask = netaddr.substring(netaddr.lastIndexOf("/") + 1);
        return netaddr;
    }

    public String findMACAddress() {
        String macAddress;
        macAddress = "";
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    macAddress = "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    //res1.append(Integer.toHexString(b & 0xFF) + ":");
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                macAddress = res1.toString();
            }
        } catch (Exception ex) {
            //Log.d("DEBUG", ex.getMessage());
        }
        return macAddress;
    }

    public String get_macaddress() {
        return s_macaddress;
    }
        public String toString() {
            return s_gateway + s_ipAddress + s_netmask + s_ssid;
        }
    }
