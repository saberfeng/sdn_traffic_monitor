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

public class FlowUtility {

    public static boolean flowEntriesEqual(FlowEntry entry1, FlowEntry entry2){
        Set<Criterion> criteria1 = entry1.selector().criteria();
        Set<Criterion> criteria2 = entry2.selector().criteria();
        Set<Criterion> c1_c2 = FlowUtility.getSubtraction(criteria1, criteria2);
        Set<Criterion> c2_c1 = FlowUtility.getSubtraction(criteria2, criteria1);
        if(c1_c2.size() != c2_c1.size() || c1_c2.size() > 1 || c2_c1.size() > 1){
            return false;
        } else if(FlowUtility.containsPortCriterion(c1_c2) && FlowUtility.containsPortCriterion(c2_c1)){
            return true;
        } else {
            return false;
        }
    }

    public static boolean containsPortCriterion(Set<Criterion> criteria){
        boolean result = false;
        for(Criterion c : criteria){
            if(c instanceof PortCriterion){
                result = true;
            }
        }
        return result;
    }

    public static Set<Criterion> getSubtraction(Set<Criterion> A, Set<Criterion> B){
        Set<Criterion> subtraction = new HashSet<Criterion>();
        subtraction.clear();
        subtraction.addAll(A);
        subtraction.removeAll(B);
        return subtraction;
    }
}