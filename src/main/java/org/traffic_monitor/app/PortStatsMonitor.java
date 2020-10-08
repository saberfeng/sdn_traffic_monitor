/*
 * Author: Xingbo Feng
 * 
 */

package org.traffic_monitor.app;

import org.onosproject.net.Device;
import org.onosproject.net.Port;
import org.onosproject.net.device.DeviceService;
import org.onosproject.net.device.PortStatistics;
import org.onosproject.net.PortNumber;
import org.onosproject.net.DeviceId;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.List;
import java.util.HashMap;
import java.util.Map;


public class PortStatsMonitor{

    private DeviceService deviceService;
    private HashMap<String, HashMap<String, PortAccumulatedStats>> portStatsStorage;
    private int interval;

    public PortStatsMonitor(DeviceService deviceService, int interval){
        this.deviceService = deviceService;
        this.portStatsStorage = new HashMap<>();
        this.interval = interval;
    }

    /*
    Port stats format:
    {
        deviceId1:{
            port1:{
                receivingPacketRate:100.34,
                transmittingPacketRate:1.11,
                receivingByteRate:2.22,
                transmittingPacketRate:33.3,
                receivingErrorRate:66.6,
                transmittingErrorRate:7.77,
                droppedReceivingPacketsRate:100.1,
                droppedTransmittingPacketsRate:200.2,
                receivingBytesPercentage:55.4,
                transmittingBytesPercentage:66.7
            }
        }
    }
    */
    public String getStats(){
        HashMap<String, HashMap<String, PortStats>> resultPortStats = new HashMap<>();
        Iterable<Device> devices = deviceService.getDevices();
        for(Device device : devices){
            HashMap<String, PortStats> devicePortStats = new HashMap<String, PortStats>();
            List<Port> ports = deviceService.getPorts(device.id());
            for(Port port : ports){
                if(port.number().toString().equals("LOCAL"))
                    continue;
                PortStatistics portStatistics = deviceService.getStatisticsForPort(device.id(), port.number());
                PortStats calculatedStats = calculateStats(device.id(), port.number(), portStatistics);
                devicePortStats.put(port.number().toString(), calculatedStats);
                saveStats(device.id(), port.number(), portStatistics);
            }
            resultPortStats.put(device.id().toString(), devicePortStats);
        }
        return resultPortStats.toString();
    }

    private void saveStats(DeviceId deviceId, PortNumber portNumber, PortStatistics portStats){
        if(this.portStatsStorage.get(deviceId.toString()) == null){
            portStatsStorage.put(deviceId.toString(), new HashMap<>());
        }
        if (this.portStatsStorage.get(deviceId.toString()).get(portNumber.toString()) == null){
            portStatsStorage.get(deviceId.toString()).put(portNumber.toString(), new PortAccumulatedStats());
        }
        PortAccumulatedStats portAccumulatedStats = portStatsStorage.get(deviceId.toString()).get(portNumber.toString());
        
        portAccumulatedStats.receivingPackets = portStats.packetsReceived();
        portAccumulatedStats.transmittingPackets = portStats.packetsSent();
        portAccumulatedStats.receivingBytes = portStats.bytesReceived();
        portAccumulatedStats.transmittingBytes = portStats.bytesSent();
        portAccumulatedStats.receivingErrors = portStats.packetsRxErrors();
        portAccumulatedStats.transmittingErrors = portStats.packetsTxErrors();
        portAccumulatedStats.droppedReceivingPackets = portStats.packetsRxDropped();
        portAccumulatedStats.droppedTransmittingPackets = portStats.packetsTxDropped();
    }

    private PortStats calculateStats(DeviceId deviceId, PortNumber portNumber, PortStatistics portStats){
        if(this.portStatsStorage.get(deviceId.toString()) == null ||
            this.portStatsStorage.get(deviceId.toString()).get(portNumber.toString()) == null){
                return new PortStats(
                        (portStats.packetsReceived())/interval,
                        (portStats.packetsSent())/interval,
                    portStats.bytesReceived()/interval,
                    portStats.bytesSent()/interval,
                    portStats.packetsRxErrors()/interval,
                    portStats.packetsTxErrors()/interval,
                    0.0,
                    0.0,
                    portStats.packetsRxDropped()/interval,
                    portStats.packetsTxDropped()/interval
                );
        }else{
            Print.print("3.1.2");
            PortAccumulatedStats savedStats = this.portStatsStorage.get(deviceId.toString()).get(portNumber.toString());
            return new PortStats(
                (portStats.packetsReceived()-savedStats.receivingPackets)/interval,
                (portStats.packetsSent()-savedStats.transmittingPackets)/interval,
                (portStats.bytesReceived()-savedStats.receivingBytes)/interval,
                (portStats.bytesSent()-savedStats.transmittingBytes)/interval,
                (portStats.packetsRxErrors()-savedStats.receivingErrors)/interval,
                (portStats.packetsTxErrors()-savedStats.transmittingErrors)/interval,
                0.0,
                0.0,
                (portStats.packetsRxDropped()-savedStats.droppedReceivingPackets)/interval,
                (portStats.packetsTxDropped()-savedStats.droppedTransmittingPackets)/interval
            );
        }
    }
}