# sdn_traffic_monitor

## backend tasks
* Polling various types of data
* Flow polling algorithm
* Aggeragation
* Converting data format

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
