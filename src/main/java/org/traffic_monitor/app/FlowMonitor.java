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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.io.FileWriter;
import java.io.IOException;

// import com.google.gson.Gson;

// build and reinstall:
// mci && onos-app localhost reinstall! target/traffic_monitor-1.10.0.oar

public class FlowMonitor {

    private ApplicationService appService;
    private FlowRuleService flowRuleService;
    private Logger log;
    private int lengthRequest;
    private int lengthReply;
    private HashSet<Flow> flowsStorage;
    private ApplicationId appId;

    public FlowMonitor(ApplicationService appService, FlowRuleService flowRuleService, Logger log) {
        log.info("\n\n\n\n\n start monitor flows! \n\n");
        this.appService = appService;
        this.flowRuleService = flowRuleService;
        this.log = log;
        this.appId = appService.getId("org.onosproject.fwd");
        this.flowsStorage = new HashSet<Flow>();
    }

    public String runAndGetStats(){
        Iterable<FlowEntry> flowEntries = flowRuleService.getFlowEntriesById(appId);
        HashSet<Flow> flows = groupEntriesToFlows(flowEntries);
        // print(flows.toString());

        // iterate through flows, map flows into set of switches 
        // (to visualize flows, we need this set of switches and flow selector)
        // pick flow matched packets and pack them into json
        // bits/s -> use flowStatisticService
        String jsonResult = construct_stats_json(flows);
        this.flowsStorage = flows; // update flows storage
        // Print.print("!!!***json:\n" + jsonResult);
        return jsonResult;
    }
    /*
    flows json format
    [
        {
            switches:[
                switch_id_1, 
                switch_id_2
            ],
            packet_rate:100/s
        },
        {
            switches:[
                switch_id_5, 
                switch_id_1
            ],
            packet_rate:600/s
        }
    ]
    */

    public String construct_stats_json(HashSet<Flow> flows){
        LinkedList<FlowStats> flowStats = new LinkedList<>();
        for (Flow flow : flows){
            LinkedList<String> switches = new LinkedList<>();
            for (FlowEntry entry : flow){
                switches.add(entry.deviceId().toString());
            }
            HashMap<String, Double> result = getPacketRateByteRate(flow, 5);
            flowStats.add(
                new FlowStats(
                    switches,
                    result.get("packetRate"),
                    result.get("byteRate")
                )
            );
        }
        // Gson gson = new Gson();
        // return gson.toJson(flowStats);
        return flowStats.toString();
    }

    private Flow retrieveFlowFromSet(HashSet<Flow> flows, Flow flow){
        for (Flow flowInSet : flows){
            if (flowInSet == flow){
                return flowInSet;
            }
        }
        return null;
    }

    private HashMap<String, Double> getPacketRateByteRate(Flow flow, int pollingInterval){
        HashMap<String, Double> result = new HashMap<>();
        Flow flowInLastPolling = retrieveFlowFromSet(this.flowsStorage, flow);
        if (flowInLastPolling == null){
            // this is a new flow since in last polling we didn't find it
            result.put("packetRate", (double)flow.pickOne().packets()/pollingInterval);
            result.put("byteRate", (double)flow.pickOne().bytes()/pollingInterval);
        } else {
            double packetRate = (flow.pickOne().packets() - flowInLastPolling.pickOne().packets())/pollingInterval;
            double byteRate = (flow.pickOne().bytes() - flowInLastPolling.pickOne().bytes())/pollingInterval;
            result.put("packetRate", (double)packetRate);
            result.put("byteRate", (double)byteRate);
        }
        return result;
    }

    private HashSet<Flow> groupEntriesToFlows(Iterable<FlowEntry> flowEntries){
        HashSet<Flow> flows = new HashSet<>();
        for(FlowEntry flowEntry : flowEntries){
            // print(flowEntry.deviceId().toString());

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
}



// Set<Application> apps = appService.getApplications();
// for(Application app : apps){
//     log.info("\n\n id:" + app.id() + " \n\n");
// }
