/*
 * Copyright 2020-present Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.traffic_monitor.app;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.Service;
import org.onosproject.net.Device;
import org.onosproject.net.Port;
import org.onosproject.net.device.DeviceService;
import org.onosproject.net.device.PortStatistics;
import org.onosproject.app.ApplicationService;
import org.onosproject.net.flow.FlowRuleService;
import org.onosproject.net.statistic.FlowStatisticService;
import org.onosproject.net.link.LinkService;
import org.onosproject.net.statistic.StatisticService;
import org.onosproject.net.statistic.PollInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;


@Component(immediate = true)
public class AppComponent {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected DeviceService deviceService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected ApplicationService applicationService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected FlowRuleService flowRuleService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected FlowStatisticService flowStatisticService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected LinkService linkService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected StatisticService statisticService;

    private int onosInterval;
    private int appInterval;

    private StatsReaderTask statsReaderTask;

    @Activate
	protected void activate() {
        log.info("Started");
        this.onosInterval = 6;
        this.appInterval = 5;
        
        // set poll interval to 5 seconds
        PollInterval pollIntervalInstance = PollInterval.getInstance();
        pollIntervalInstance.setPollInterval(this.onosInterval);

        FlowMonitor flowMonitor = new FlowMonitor(applicationService, flowRuleService, log);
        PortStatsMonitor portStatsMonitor = new PortStatsMonitor(deviceService, this.appInterval);
        LinkStatsMonitor linkStatsMonitor = new LinkStatsMonitor(linkService, statisticService);

        statsReaderTask = new StatsReaderTask(flowMonitor, portStatsMonitor, linkStatsMonitor, appInterval);
        statsReaderTask.schedule();
        // flowMonitor.runAndGetStats();
		// Iterable<Device> devices = deviceService.getDevices();

		// for(Device d : devices)
        // {
        //     log.info("#### [viswa] Device id " + d.id().toString());

        //     List<Port> ports = deviceService.getPorts(d.id());
        //     for(Port port : ports) {
        //         log.info("\n\n-----Getting info for port" + port.number() + "-----\n");
        //         PortStatistics portstat = deviceService.getStatisticsForPort(d.id(), port.number());
        //         PortStatistics portdeltastat = deviceService.getDeltaStatisticsForPort(d.id(), port.number());
        //         if(portstat != null)
        //             log.info("\n\n----------portstat bytes recieved" + portstat.bytesReceived() + "-----\n");
        //         else
        //             log.info("\n\n----------Unable to read portStats" + "-----\n");

        //         if(portdeltastat != null)
        //             log.info("\n\n-----portdeltastat bytes recieved" + portdeltastat.bytesReceived() + "-----\n");
        //         else
        //             log.info("\n\n-----Unable to read portDeltaStats" + "-----\n");
        //     }
        // }
	}

    @Deactivate
    protected void deactivate() {
        statsReaderTask.cancel();
        log.info("Stopped");
    }

}