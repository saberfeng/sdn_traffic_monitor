/*
 * Author: Xingbo Feng
 * 
 */


package org.traffic_monitor.app;

import java.util.*;  

public class FlowStats{
    public LinkedList<String> switches;
    public double packetRate;
    public double byteRate;

    public FlowStats(LinkedList<String> switches, double packetRate, double byteRate){
        this.switches = switches;
        this.packetRate = packetRate;
        this.byteRate = byteRate;
    }

    @Override
    public String toString(){
        return "\n{switches:\n" + this.switches.toString() + 
            "\npacketRate:" + this.packetRate + 
            "\nbyteRate:" + this.byteRate + "}\n";
    }

    public String toJSON(){
        StringBuilder switchesJSONBuilder = new StringBuilder("[");
        for(String switch_id: switches){
            switchesJSONBuilder.append("\""+switch_id+"\"").append(",");
        }
        switchesJSONBuilder.deleteCharAt(switchesJSONBuilder.length()-1);
        switchesJSONBuilder.append("]");
        return String.format(
                "{\"switches\":%s, \"packetRate\":%f, \"byteRate\":%f, \"time\":%d}",
                switchesJSONBuilder.toString(),
                packetRate,
                byteRate,
                System.currentTimeMillis());
    }
}
