package org.traffic_monitor.app;

public class TotalBytesRxTx {
    public long totalReceivingBytes;
    public long totalTransmittingBytes;

    public TotalBytesRxTx(long totalReceivingBytes, long totalTransmittingBytes){
        this.totalReceivingBytes = totalReceivingBytes;
        this.totalTransmittingBytes = totalTransmittingBytes;
    }
}
