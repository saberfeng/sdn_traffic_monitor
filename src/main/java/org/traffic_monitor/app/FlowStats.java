/*
 * Author: Xingbo Feng
 * 
 */


package org.traffic_monitor.app;

import java.util.*;  

public class FlowStats{
    LinkedList<String> switches;
    double packetRate;
    double byteRate;

    public FlowStats(LinkedList<String> switches, double packetRate, double byteRate){
        this.switches = switches;
        this.packetRate = packetRate;
        this.byteRate = byteRate;
    }

    @Override
    public String toString(){
        return "{switches:\n" + this.switches.toString() + 
            "\npacketRate:" + this.packetRate + 
            "\nbyteRate:" + this.byteRate + "}\n";
    }
}
