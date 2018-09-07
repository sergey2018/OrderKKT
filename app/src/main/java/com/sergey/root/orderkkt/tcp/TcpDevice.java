package com.sergey.root.orderkkt.tcp;

public class TcpDevice {
    public final String serialNumber;
    public final String address;
    public final String port;

    TcpDevice(String serialNumber, String address,String port) {
        this.serialNumber = serialNumber;
        this.address = address;
        this.port = port;
    }
    public String getTCPAPIPort(){
        return address+":"+port;
    }
}
