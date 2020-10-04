/*
 * Author: Xingbo Feng
 * 
 */

package org.traffic_monitor.app;

import org.onosproject.core.Application;
import org.onosproject.app.ApplicationService;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.onosproject.core.ApplicationId;
import org.onosproject.net.flow.FlowRuleService;
import org.onosproject.net.flow.FlowEntry;
import org.onosproject.net.flow.TrafficSelector;
import org.onosproject.net.flow.criteria.Criterion;
import org.onosproject.net.flow.criteria.PortCriterion;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.Set;
import java.util.HashSet;
import java.io.FileWriter;
import java.io.IOException;

// build and reinstall:
// mci && onos-app localhost reinstall! target/traffic_monitor-1.10.0.oar

public class FlowMonitor {

    private ApplicationService appService;
    private FlowRuleService flowRuleService;
    private Logger log;
    private int lengthRequest;
    private int lengthReply;

    public FlowMonitor(ApplicationService appService, FlowRuleService flowRuleService, Logger log) {
        this.appService = appService;
        this.flowRuleService = flowRuleService;
        this.log = log;

        log.info("\n\n\n\n\n start monitor flows! \n\n");

        ApplicationId appId = appService.getId("org.onosproject.fwd");
        print(appId.toString());
        
        Iterable<FlowEntry> flowEntries = flowRuleService.getFlowEntriesById(appId);
        HashSet<Flow> flows = groupEntriesToFlows(flowEntries);
        print(flows.toString());
    }

    private HashSet<Flow> groupEntriesToFlows(Iterable<FlowEntry> flowEntries){
        HashSet<Flow> flows = new HashSet<>();
        for(FlowEntry flowEntry : flowEntries){
            print(flowEntry.deviceId().toString());

            boolean hasBelongingFlow = false;
            for(Flow flow : flows){
                if(flow.contains(flowEntry)){
                    flow.add(flowEntry);
                    hasBelongingFlow = true;
                    break;
                }
            }
            if(!hasBelongingFlow){
                Flow flow = new Flow();
                flow.add(flowEntry);
                flows.add(flow);
            }
        }
        return flows;
    }


    private void print(String s){
        try{
            FileWriter writer = new FileWriter("/home/sdn/sdn_traffic_monitor/output", true);
            writer.write(s + "\n");
            writer.close();
        } catch (IOException e) {
            log.info("\n\nerror!\n\n");
        }
    }
}



// Set<Application> apps = appService.getApplications();
// for(Application app : apps){
//     log.info("\n\n id:" + app.id() + " \n\n");
// }
