/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utfpr.ct.explanans.model;

import java.util.ArrayList;

/**
 * A node of the explanatory graph, representing an event, and a list of conditions to which this node is connected.
 * Events have only a name(String).
 * @author Henrique M R Jasinski
 */
public class EventNode implements Comparable<EventNode> {
    public final String name;
    public final ArrayList<ConditionNode> conditions = new ArrayList<>();

    /**
     * Basic constructor.
     * @param name Event name/identifier
     */
    public EventNode(String name) {
        this.name = name;
    }
    
    /**
     * Compares two event nodes using the standard String.compareTo methos, using the names.
     * @param o The event node being compared to
     * @return 
     */
    @Override
    public int compareTo(EventNode o) {
         return this.conditions.size() - o.conditions.size();
    }
    
}
