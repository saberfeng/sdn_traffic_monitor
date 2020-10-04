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

    public FlowMonitor(ApplicationService appService, FlowRuleService flowRuleService, Logger log) {
        this.appService = appService;
        this.flowRuleService = flowRuleService;
        this.log = log;

        log.info("\n\n\n\n\n start monitor flows! \n\n");

        ApplicationId appId = appService.getId("org.onosproject.fwd");
        print(appId.toString());
        
        Iterable<FlowEntry> flowEntries = flowRuleService.getFlowEntriesById(appId);
        HashSet<HashSet<FlowEntry>> flows = groupEntriesToFlows(flowEntries);
        print(flows.toString());
    }

    private HashSet<HashSet<FlowEntry>> groupEntriesToFlows(Iterable<FlowEntry> flowEntries){
        HashSet<HashSet<FlowEntry>> flows = new HashSet<>();
        for(FlowEntry flowEntry : flowEntries){
            print(flowEntry.deviceId().toString());

            boolean hasBelongingFlow = false;
            for(HashSet<FlowEntry> flow : flows){
                if(flowContains(flow, flowEntry)){
                    flow.add(flowEntry);
                    hasBelongingFlow = true;
                    break;
                }
            }
            if(!hasBelongingFlow){
                HashSet<FlowEntry> flow = new HashSet<FlowEntry>();
                flow.add(flowEntry);
                flows.add(flow);
            }
        }
        return flows;
    }

    private boolean flowContains(HashSet<FlowEntry> flow, FlowEntry entry){
        for(FlowEntry entryInFlow : flow){
            if(flowEntriesEqual(entryInFlow, entry)){
                return true;
            }
        }
        return false;
    }

    private boolean flowEntriesEqual(FlowEntry entry1, FlowEntry entry2){
        Set<Criterion> criteria1 = entry1.selector().criteria();
        Set<Criterion> criteria2 = entry2.selector().criteria();
        Set<Criterion> c1_c2 = getSubtraction(criteria1, criteria2);
        Set<Criterion> c2_c1 = getSubtraction(criteria2, criteria1);
        if(c1_c2.size() != c2_c1.size() || c1_c2.size() > 1 || c2_c1.size() > 1){
            return false;
        } else if(containsPortCriterion(c1_c2) && containsPortCriterion(c2_c1)){
            return true;
        } else {
            return false;
        }
    }
    
    private boolean containsPortCriterion(Set<Criterion> criteria){
        boolean result = false;
        for(Criterion c : criteria){
            if(c instanceof PortCriterion){
                result = true;
            }
        }
        return result;
    }

    private Set<Criterion> getSubtraction(Set<Criterion> A, Set<Criterion> B){
        Set<Criterion> subtraction = new HashSet<Criterion>();
        subtraction.clear();
        subtraction.addAll(A);
        subtraction.removeAll(B);
        return subtraction;
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
