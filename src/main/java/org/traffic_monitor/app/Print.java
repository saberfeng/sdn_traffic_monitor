package org.traffic_monitor.app;

import java.io.FileWriter;
import java.io.IOException;

public class Print{
    public static void print(String s){
        try{
            FileWriter writer = new FileWriter("/home/sdn/sdn_traffic_monitor/output", true);
            writer.write(s + "\n");
            writer.close();
        } catch (IOException e) {
            int i = 1; // do nothing
        }
    }
}