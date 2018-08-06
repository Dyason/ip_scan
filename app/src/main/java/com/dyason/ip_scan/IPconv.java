package com.dyason.ip_scan;

import java.util.ArrayList;

public class IPconv {


    int baseIPnumeric;
    int netmaskNumeric;

    public IPconv(String ipaddress) {

        String[] st = ipaddress.split("\\/");
        String symbolicIP = st[0];
        String symbolicCIDR = st[1];

        Integer numericCIDR = new Integer(symbolicCIDR);

        /* IP */
        st = symbolicIP.split("\\.");

        int i = 24;
        baseIPnumeric = 0;

        for (int n = 0; n < st.length; n++) {

            int value = Integer.parseInt(st[n]);

            baseIPnumeric += value << i;
            i -= 8;

        }

        netmaskNumeric = 0xffffffff;
        netmaskNumeric = netmaskNumeric << (32 - numericCIDR);
    }

    public String getNetwork() {
        Integer baseIP = baseIPnumeric & netmaskNumeric;
        return convertNumericIpToSymbolic(baseIP);

    }
    // Count hosts
    public long getNumberOfHosts() {


        int numberOfBits;

        for (numberOfBits = 0; numberOfBits < 32; numberOfBits++) {

            if ((netmaskNumeric << numberOfBits) == 0)
                break;

        }

        Double x = Math.pow(2, (32 - numberOfBits));

        if (x == -1)
            x = 1D;

        long numhosts;
        numhosts = x.longValue();

        return numhosts;
    }

    public ArrayList<String> getHosts() {

        ArrayList<String> hosts = new ArrayList<String>();
        int addhost;
        //Remove 2 to strip the network and broadcast addresses
        for (addhost = 0; addhost < (getNumberOfHosts()-2); addhost++) {
            //Add 1 to skip the network address
            hosts.add(convertNumericIpToSymbolic((baseIPnumeric & netmaskNumeric)+1+addhost));
        }
        //remove network and broadcast or never add them?
        return hosts;
    }
    public String getIP() {
        return convertNumericIpToSymbolic(baseIPnumeric);

    }

    private String convertNumericIpToSymbolic(Integer ip) {
        StringBuffer sb = new StringBuffer(15);

        for (int shift = 24; shift > 0; shift -= 8) {

            // process 3 bytes, from high order byte down.
            sb.append(Integer.toString((ip >>> shift) & 0xff));

            sb.append('.');
        }
        sb.append(Integer.toString(ip & 0xff));

        return sb.toString();
    }
}

