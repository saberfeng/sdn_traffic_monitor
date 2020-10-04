/*
 * Author: Xingbo Feng
 * 
 */

package org.traffic_monitor.app;

import org.onosproject.net.flow.FlowEntry;
import org.onosproject.net.flow.criteria.Criterion;
import org.onosproject.net.flow.criteria.PortCriterion;

import java.util.HashSet;
import java.util.Set;

public class Flow {
    
    private HashSet<FlowEntry> entries;

    public Flow(){
        this.entries = new HashSet<FlowEntry>();
    }

    public void setEntries(HashSet<FlowEntry> entries){
        this.entries = entries;
    }

    public boolean add(FlowEntry entry){
        return this.entries.add(entry);
    }

    public int size(){
        return this.entries.size();
    }

    public void clear(){
        this.entries.clear();
    }

    public boolean contains(FlowEntry entry){
        for(FlowEntry entryInFlow : this.entries){
            if(FlowUtility.flowEntriesEqual(entryInFlow, entry)){
                return true;
            }
        }
        return false;
    }

    // @Override
    // public boolean equals(Object obj){
    //     if (obj == null || obj.getClass() != this.getClass()){
    //         return false;
    //     }
    //     final Flow other = (Flow) obj;

    // }

    @Override
    public String toString(){
        return this.entries.toString();
    }

    
}
