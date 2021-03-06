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
			"receivingPacketRate": 601.000000,
			"transmittingPacketRate": 3392.000000,
			"receivingByteRate": 58859.000000,
			"transmittingByteRate": 405092.000000,
			"receivingErrorRate": 0.000000,
			"transmittingErrorRate": 0.000000,
			"receivingBytesPercentage": 0.140858,
			"transmittingBytesPercentage": 0.529603,
			"droppedReceivingPacketsRate": 0.000000,
			"droppedTransmittingPacketsRate": 0.000000,
			"time": 1602308995919
		},
		"2": {
			"receivingPacketRate": 3257.000000,
			"transmittingPacketRate": 3259.000000,
			"receivingByteRate": 359003.000000,
			"transmittingByteRate": 359806.000000,
			"receivingErrorRate": 0.000000,
			"transmittingErrorRate": 0.000000,
			"receivingBytesPercentage": 0.859142,
			"transmittingBytesPercentage": 0.470397,
			"droppedReceivingPacketsRate": 0.000000,
			"droppedTransmittingPacketsRate": 0.000000,
			"time": 1602308995919
		}
	},
	"of:0000000000000002": {
		"1": {
			"receivingPacketRate": 615.000000,
			"transmittingPacketRate": 3408.000000,
			"receivingByteRate": 59481.000000,
			"transmittingByteRate": 406311.000000,
			"receivingErrorRate": 0.000000,
			"transmittingErrorRate": 0.000000,
			"receivingBytesPercentage": 0.141862,
			"transmittingBytesPercentage": 0.530908,
			"droppedReceivingPacketsRate": 0.000000,
			"droppedTransmittingPacketsRate": 0.000000,
			"time": 1602308995925
		},
		"2": {
			"receivingPacketRate": 3259.000000,
			"transmittingPacketRate": 3257.000000,
			"receivingByteRate": 359806.000000,
			"transmittingByteRate": 359003.000000,
			"receivingErrorRate": 0.000000,
			"transmittingErrorRate": 0.000000,
			"receivingBytesPercentage": 0.858138,
			"transmittingBytesPercentage": 0.469092,
			"droppedReceivingPacketsRate": 0.000000,
			"droppedTransmittingPacketsRate": 0.000000,
			"time": 1602308995925
		}
	}
}    
```
##### link stats
```
[{
	"src": "of:0000000000000002",
	"dst": "of:0000000000000001",
	"rate": 49,
	"time": 1602309842827
}, {
	"src": "of:0000000000000001",
	"dst": "of:0000000000000002",
	"rate": 49,
	"time": 1602309842828
}]
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
