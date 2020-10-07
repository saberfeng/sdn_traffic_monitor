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
import java.util.Iterator;

public class Flow implements Iterable<FlowEntry>{
    
    private HashSet<FlowEntry> entries;

    public Flow(){
        this.entries = new HashSet<FlowEntry>();
    }

    @Override
    public Iterator<FlowEntry> iterator(){
        return entries.iterator();
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

    public FlowEntry pickOne(){
        if(this.size() == 0){
            return null;
        } else {
            Iterator<FlowEntry> it = this.entries.iterator();
            return it.next();
        }
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null || obj.getClass() != this.getClass()){
            return false;
        }
        final Flow other = (Flow) obj;
        if (this.size() != other.size()){
            return false;
        }
        if (FlowUtility.flowEntriesEqual(this.pickOne(), other.pickOne())){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return this.entries.toString();
    }

    
}
