package org.traffic_monitor.app;

public class LinkStats {
    public String src;
    public String dst;
    public long rate;
    public long time;

    public LinkStats(String src, String dst, long rate, long time){
        this.src = src;
        this.dst = dst;
        this.rate = rate;
        this.time = time;
    }
    public String toJSON(){
        return String.format("{" +
                "\"src\":\"%s\","+
                "\"dst\":\"%s\","+
                "\"rate\":%d,"+
                "\"time\":%d}",
                src,
                dst,
                rate,
                time);
    }
}
