/*
 * Author: Xingbo Feng
 * 
 */

package org.traffic_monitor.app;

import java.util.Timer;
import java.util.TimerTask;


public class StatsReaderTask{

    private FlowMonitor flowMonitor;
    private Timer timer;
    private WorkerTask workerTask;

    public StatsReaderTask(FlowMonitor flowMonitor){
        this.flowMonitor = flowMonitor;
        this.timer = new Timer();
        this.workerTask = new WorkerTask();
    }

    class WorkerTask extends TimerTask{

        @Override
        public void run(){
            String flowStats = flowMonitor.runAndGetStats();
            Print.print(flowStats);
        }
    }

    public void schedule(){
        this.timer.schedule(workerTask, 0, 1000*5);
    }
}