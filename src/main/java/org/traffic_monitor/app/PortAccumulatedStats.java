/*
 * Author: Xingbo Feng
 * 
 */

package org.traffic_monitor.app;

public class PortAccumulatedStats{
    public long receivingPackets;
    public long transmittingPackets;
    public long receivingBytes;
    public long transmittingBytes;
    public long receivingErrors;
    public long transmittingErrors;
    public long droppedReceivingPackets;
    public long droppedTransmittingPackets;

    // public PortStats(
    //     double receivingPacketRate,
    //     double transmittingPacketRate,
    //     double receivingByteRate,
    //     double transmittingByteRate,
    //     double receivingErrorRate,
    //     double transmittingErrorRate,
    //     double receivingBytesPercentage,
    //     double transmittingBytesPercentage,
    //     long droppedReceivingPackets,
    //     long droppedTransmittingPackets
    //     ){
    //     this.receivingPacketRate = receivingPacketRate;
    //     this.transmittingPacketRate = transmittingPacketRate;
    //     this.receivingByteRate = receivingByteRate;
    //     this.transmittingByteRate = transmittingByteRate;
    //     this.receivingErrorRate = receivingErrorRate;
    //     this.transmittingErrorRate = transmittingErrorRate;
    //     this.receivingBytesPercentage = receivingBytesPercentage;
    //     this.transmittingBytesPercentage = transmittingBytesPercentage;
    //     this.droppedReceivingPackets = droppedReceivingPackets;
    //     this.droppedTransmittingPackets = droppedTransmittingPackets;
    // }
}