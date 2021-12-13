/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utfpr.ct.explanans.model;

import java.util.ArrayList;

/**
 * A node of the explanatory graph, representing a condition.
 * Conditions have a name(String), a set of events to which it is connected, 
 * a set of other condition nodes that are supersets of this node (with respect to the event adjacence), and a set of condition nodes that are subsets of this node.
 * 
 * @author Henrique M R Jasinski
 */
public class ConditionNode implements Comparable<ConditionNode>{
    public final String name;
    public final ArrayList<EventNode> events = new ArrayList<>();
    public final ArrayList<ConditionNode> subsetOf = new ArrayList<>();
    public final ArrayList<ConditionNode> supersetOf = new ArrayList<>();
    
    /**
     * Basic constructor.
     * 
     * @param name 
     */
    public ConditionNode(String name) {
        this.name = name;
    }

    /**
     * Returns the node name.
     * @return name
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Includes the event node nE to the list of adjacent events, if not already in the list.
     * @param nE The event node.
     */
    public void add(EventNode nE) {
        if(events.contains(nE)) return;
        
        events.add(nE);
        nE.conditions.add(this);
    }

    /**
     * Includes the condition node superSet to the list of supersetOf, if not already in the list.
     * Also includes this node on the subsetOf list of the superSet node.
     * The use of MinimalistPossibleExplanas.calculateSubsetOf(conditions) is recommended.
     * 
     * @param superSet The condition node that is superset of this node.
     */
    public void addSubsetOf(ConditionNode superSet) {
        if(subsetOf.contains(superSet)) return;
        
        subsetOf.add(superSet);
        superSet.supersetOf.add(this);
    }

    /**
     * Compares two conditions nodes with the standard String.compareTo methos, using the names.
     * @param o Node being compared to.
     * @return 
     */
    @Override
    public int compareTo(ConditionNode o) {
        return this.name.compareTo(o.name);
    }
    
}
