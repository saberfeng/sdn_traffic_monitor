# sdn_traffic_monitor

## backend tasks
* Polling various types of data
* Flow polling algorithm
* Aggeragation
* Converting data format

## stats JSON format

##### flow stats
```
[{
	"switches": ["of:0000000000000001", "of:0000000000000002"],
	"packetRate": 38.200000,
	"byteRate": 3743.600000,
	"time": 1602306189615
}, {
	"switches": ["of:0000000000000002", "of:0000000000000001"],
	"packetRate": 38.000000,
	"byteRate": 3724.000000,
	"time": 1602306189615
}]
```
##### port stats
```
{
	"of:0000000000000001": {
		"1": {
			"receivingPacketRate": 518.000000,
			"transmittingPacketRate": 3240.000000,
			"receivingByteRate": 50725.000000,
			"transmittingByteRate": 389191.000000,
			"receivingErrorRate": 0.000000,
			"transmittingErrorRate": 0.000000,
			"receivingBytesPercentage": 0.128533,
			"transmittingBytesPercentage": 0.530343,
			"droppedReceivingPacketsRate": 0.000000,
			"droppedTransmittingPacketsRate": 0.000000
		},
        "2": {
			"receivingPacketRate": 3107.000000,
			"transmittingPacketRate": 3109.000000,
			"receivingByteRate": 343922.000000,
			"transmittingByteRate": 344657.000000,
			"receivingErrorRate": 0.000000,
			"transmittingErrorRate": 0.000000,
			"receivingBytesPercentage": 0.871467,
			"transmittingBytesPercentage": 0.469657,
			"droppedReceivingPacketsRate": 0.000000,
			"droppedTransmittingPacketsRate": 0.000000
		}
	},
	"of:0000000000000002": {
		"1": {
			"receivingPacketRate": 530.000000,
			"transmittingPacketRate": 3253.000000,
			"receivingByteRate": 51254.000000,
			"transmittingByteRate": 390044.000000,
			"receivingErrorRate": 0.000000,
			"transmittingErrorRate": 0.000000,
			"receivingBytesPercentage": 0.129458,
			"transmittingBytesPercentage": 0.531420,
			"droppedReceivingPacketsRate": 0.000000,
			"droppedTransmittingPacketsRate": 0.000000
		},    
        "2": {
			"receivingPacketRate": 3109.000000,
			"transmittingPacketRate": 3107.000000,
			"receivingByteRate": 344657.000000,
			"transmittingByteRate": 343922.000000,
			"receivingErrorRate": 0.000000,
			"transmittingErrorRate": 0.000000,
			"receivingBytesPercentage": 0.870542,
			"transmittingBytesPercentage": 0.468580,
			"droppedReceivingPacketsRate": 0.000000,
			"droppedTransmittingPacketsRate": 0.000000
		}
	}
}    
```


## types of data to visualize:
| type |  sub type                |  source |
| ---- | ------------------------ | ------------------------------------------------------------ |
| Port | Receiving pakcet rate    |org.onosproject.net.device.DeviceService.getStatisticsForPort.packetsReceived()  |
|      | Transmitting packet rate |  org.onosproject.net.device.DeviceService.getStatisticsForPort.packetsSent()  |
|      | Receiving byte rate |   org.onosproject.net.device.DeviceService.getStatisticsForPort.bytesReceived()   |
|      | Transmitting byte rate  |  org.onosproject.net.device.DeviceService.getStatisticsForPort.bytesSent()   |
|      | Receiving error packets rate  |  org.onosproject.net.device.DeviceService.getStatisticsForPort.packetsRxErrors()   |
|      | Transmitting error packets rate  | org.onosproject.net.device.DeviceService.getStatisticsForPort.packetsTxErrors() |
|      | Dropped recieving packets | org.onosproject.net.device.DeviceService.getStatisticsForPort.packetsRxDropped() |
|      | Dropped transmitting packets | org.onosproject.net.device.DeviceService.getStatisticsForPort.packetsTxDropped() |
|      | highest hitter (the flow rule generating the most load) | org.onosproject.net.statistic.StatisticService.highestHitter() |
|      | Receiving bytes percentage |  calculated  |
|      | Transmitting bytes percentage | calculated |
| Flow | Packet rate | org.onosproject.net.flow.FlowRuleService.getFlowEntries(), FlowEntry.packets()|
|      | Byte rate | org.onosproject.net.flow.FlowRuleService.getFlowEntries(), FlowEntry.packets(), org.onosproject.net.statistic.FlowStatisticService.loadAllByType() |
|Swtich| Installed flow rules | org.onosproject.net.flow.FlowRuleService.getFlowEntries() | 
|      | flow life ( how long it has been applied) | org.onosproject.net.flow.FlowRuleService.getFlowEntries(), FlowEntry.life() |
|      | flow tables and entries    |org.onosproject.net.flow.FlowRuleService.getFlowTableStatistics(), TableStatisticsEntry.activeFlowEntries()|
|      | flow table matched packets |org.onosproject.net.flow.FlowRuleService.getFlowTableStatistics(), TableStatisticsEntry.packetsMatched() |
|      | Packet rate |calculated |
|      | Byte rate   |calculated |
| link | most loaded link along a path | org.onosproject.net.statistic.StatisticService.max() |
|      | least loaded link along a path | org.onosproject.net.statistic.StatisticService.min() |
|      | load(bytes/s) of a link | org.onosproject.net.statistic.StatisticService.load() |
