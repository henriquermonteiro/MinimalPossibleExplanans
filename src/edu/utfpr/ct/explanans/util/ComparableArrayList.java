/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utfpr.ct.explanans.util;

import java.util.ArrayList;

/**
 * Class responsible for comparing two array lists contents.
 * It is used to identify if a list is a superset/subset of another.
 * 
 * @author Henrique M R Jasinski
 */
public class ComparableArrayList<T> extends ArrayList<T> implements Comparable<ComparableArrayList<T>>{
    
    /**
     * Basic constructor.
     */
    public ComparableArrayList() {
        super();
    }

    /**
     * Returns the list as a string.
     * Calls each item toString() method.
     * @return 
     */
    @Override
    public String toString() {
        String ret = "[";
        
        for(T obj : this){
            ret += obj.toString() + ", ";
        }
        
        return ret.replaceAll(", $", "") + "]";
    }

    /**
     * Compares the two lists using the default string comparator.
     * 
     * @param o list being compared
     * @return String.compareTo(o).
     */
    @Override
    public int compareTo(ComparableArrayList<T> o) {
        return this.toString().compareTo(o.toString());
    }
}
