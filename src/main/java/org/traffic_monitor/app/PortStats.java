/*
 * Author: Xingbo Feng
 * 
 */

package org.traffic_monitor.app;

public class PortStats{
    public double receivingPacketRate;
    public double transmittingPacketRate;
    public double receivingByteRate;
    public double transmittingByteRate;
    public double receivingErrorRate;
    public double transmittingErrorRate;
    public double receivingBytesPercentage;
    public double transmittingBytesPercentage;
    public double droppedReceivingPacketsRate;
    public double droppedTransmittingPacketsRate;

    public PortStats(
        double receivingPacketRate,
        double transmittingPacketRate,
        double receivingByteRate,
        double transmittingByteRate,
        double receivingErrorRate,
        double transmittingErrorRate,
        double receivingBytesPercentage,
        double transmittingBytesPercentage,
        double droppedReceivingPacketsRate,
        double droppedTransmittingPacketsRate
        ){
        this.receivingPacketRate = receivingPacketRate;
        this.transmittingPacketRate = transmittingPacketRate;
        this.receivingByteRate = receivingByteRate;
        this.transmittingByteRate = transmittingByteRate;
        this.receivingErrorRate = receivingErrorRate;
        this.transmittingErrorRate = transmittingErrorRate;
        this.receivingBytesPercentage = receivingBytesPercentage;
        this.transmittingBytesPercentage = transmittingBytesPercentage;
        this.droppedReceivingPacketsRate = droppedReceivingPacketsRate;
        this.droppedTransmittingPacketsRate = droppedTransmittingPacketsRate;
    }
}