/*
 * Author: Xingbo Feng
 * 
 */

package org.traffic_monitor.app;

import java.util.Timer;
import java.util.TimerTask;


public class StatsReaderTask{

    private FlowMonitor flowMonitor;
    private PortStatsMonitor portStatsMonitor;
    private LinkStatsMonitor linkStatsMonitor;

    private Timer timer;
    private WorkerTask workerTask;

    private int interval;

    public StatsReaderTask(
            FlowMonitor flowMonitor,
            PortStatsMonitor portStatsMonitor,
            LinkStatsMonitor linkStatsMonitor,
            int interval){
        this.flowMonitor = flowMonitor;
        this.portStatsMonitor = portStatsMonitor;
        this.linkStatsMonitor = linkStatsMonitor;

        this.timer = new Timer();
        this.workerTask = new WorkerTask();
        this.interval = interval;
    }

    class WorkerTask extends TimerTask{

        @Override
        public void run(){
            Print.print("start!");
            String flowStats = flowMonitor.runAndGetStats();
            Print.print(flowStats);
//            String portStats = portStatsMonitor.getStats();
//            Print.print(portStats);
            // String linkStats = linkStatsMonitor.getStats();
            // Print.print(linkStats);
        }
    }

    public void schedule(){
        this.timer.schedule(workerTask, 0, 1000*interval);
    }

    public void cancel(){
        this.workerTask.cancel();
        this.timer.cancel();
    }
}