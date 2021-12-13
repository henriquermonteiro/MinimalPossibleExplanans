/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utfpr.ct.explanans.test;

import edu.utfpr.ct.explanans.funtion.MinimalistPossibleExplanans;
import edu.utfpr.ct.explanans.model.ConditionNode;
import edu.utfpr.ct.explanans.model.EventNode;
import edu.utfpr.ct.explanans.util.ComparableArrayList;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Test case for the library.
 * The rigth result is: "[[1, 4, 5], [1, 4, 6], [2, 3, 5], [2, 3, 6], [2, 5, 7], [2, 6, 7], [3, 4, 5], [3, 4, 6], [4, 5, 7], [4, 6, 7], [5, 8], [6, 8]]"
 * 
 * @author Henrique M R Jasinski
 */
public class Test1 {
    /**
     * Test case 1. The correctness of the test is printed out in the console.
     * 
     * @return the resulting possible explanans set
     */
    public static ArrayList<ComparableArrayList<ConditionNode>> test1(){
        // Initialize the graph
        ArrayList<ConditionNode> conditions = new ArrayList<>();
        ArrayList<EventNode> events = new ArrayList<>();

        EventNode eA = new EventNode("a");
        EventNode eB = new EventNode("b");
        EventNode eC = new EventNode("c");
        EventNode eD = new EventNode("d");
        EventNode eE = new EventNode("e");
        EventNode eF = new EventNode("f");

        events.add(eA);
        events.add(eB);
        events.add(eC);
        events.add(eD);
        events.add(eE);
        events.add(eF);

        ConditionNode c1 = new ConditionNode("1");
        ConditionNode c2 = new ConditionNode("2");
        ConditionNode c3 = new ConditionNode("3");
        ConditionNode c4 = new ConditionNode("4");
        ConditionNode c5 = new ConditionNode("5");
        ConditionNode c6 = new ConditionNode("6");
        ConditionNode c7 = new ConditionNode("7");
        ConditionNode c8 = new ConditionNode("8");

        conditions.add(c1);
        conditions.add(c2);
        conditions.add(c3);
        conditions.add(c4);
        conditions.add(c5);
        conditions.add(c6);
        conditions.add(c7);
        conditions.add(c8);

        c1.add(eB);

        c2.add(eA);

        c3.add(eB);
        c3.add(eC);
        c3.add(eD);

        c4.add(eA);
        c4.add(eC);
        c4.add(eD);

        c5.add(eE);
        c5.add(eF);

        c6.add(eE);
        c6.add(eF);

        c7.add(eB);
        c7.add(eC);
        c7.add(eD);
        
        c8.add(eA);
        c8.add(eB);
        c8.add(eC);
        c8.add(eD);
        
        // Calculate the possible explanans

        ArrayList<ComparableArrayList<ConditionNode>> possibleExplanans = MinimalistPossibleExplanans.initializeAndCalculateMPE(conditions, events);
        
        // Order the resulting set to evaluate the result correctness
        for(ArrayList<ConditionNode> cover : possibleExplanans){
            Collections.sort(cover);
        }
        
        Collections.sort(possibleExplanans);
        
        // Right result
        String rightResult = "[[1, 4, 5], [1, 4, 6], [2, 3, 5], [2, 3, 6], [2, 5, 7], [2, 6, 7], ";
        rightResult += "[3, 4, 5], [3, 4, 6], [4, 5, 7], [4, 6, 7], [5, 8], [6, 8]]";
        
        System.out.println(possibleExplanans.toString());
        
        // Print the result.
        if(possibleExplanans.toString().equals(rightResult)){
            System.out.println("Test 1 : OK");
        }else{
            System.err.println("Test 1 : FAILED");
        }
        
        return possibleExplanans;
    }
    
    public static void main(String ... args){
        test1();
    }
}
