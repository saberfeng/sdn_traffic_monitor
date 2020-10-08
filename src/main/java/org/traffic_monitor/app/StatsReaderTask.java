/*
 * Author: Xingbo Feng
 * 
 */

package org.traffic_monitor.app;

import java.util.Timer;
import java.util.TimerTask;


public class StatsReaderTask{

    private FlowMonitor flowMonitor;

    public StatsReaderTask(FlowMonitor flowMonitor){
        this.flowMonitor = flowMonitor;
    }

    class Task extends TimerTask{

        @Override
        public void run(){
            String flowStats = flowMonitor.runAndGetStats();

        }
    }

    
}