package com.dyason.ip_scan;

public class Device {

    //var
    private String ipaddress;
    private String macaddress;
    private boolean identified;

    //con
    public Device() {
        this.ipaddress="";
        this.macaddress="";
        this.identified=false;
    }

    public Device(String ipaddress, String macaddress) {
        this.ipaddress = ipaddress;
        this.macaddress = macaddress;
        this.identified=false;
    }

    public Device(String ipaddress, String macaddress, boolean identified) {
        this.ipaddress = ipaddress;
        this.macaddress = macaddress;
        this.identified=identified;
    }


    //meth
    public String getIpaddress() {
        return ipaddress;
    }

    public String getMacaddress() {
        return macaddress;
    }

    public boolean getIdentified() { return identified;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public void setMacaddress(String macaddress) {
        this.macaddress = macaddress;
    }

    public void setMacaddress(boolean identified) {
        this.identified = identified;
    }

}
