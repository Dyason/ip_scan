package com.dyason.ip_scan;

public class Device {

    //var
    private String ipaddress;
    private String macaddress;

    //con
    public Device() {
        this.ipaddress="";
        this.macaddress="";
    }

    public Device(String ipaddress, String macaddress) {
        this.ipaddress = ipaddress;
        this.macaddress = macaddress;
    }

    //meth
    public String getIpaddress() {
        return ipaddress;
    }

    public String getMacaddress() {
        return macaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public void setMacaddress(String macaddress) {
        this.macaddress = macaddress;
    }

}
