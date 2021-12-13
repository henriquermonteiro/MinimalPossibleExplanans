/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utfpr.ct.explanans.funtion;

import edu.utfpr.ct.explanans.model.ConditionNode;
import edu.utfpr.ct.explanans.model.EventNode;
import edu.utfpr.ct.explanans.util.ComparableArrayList;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Funtions to calculate the possible explanans.
 *
 * initializaAndCalculateMPE is the recommended function to be used.
 *
 * @author Henrique M R Jasinski
 */
public class MinimalistPossibleExplanans {

    /**
     * Calculates the set of minimalist possible explanans. A set is minimalist
     * iff. there is no two elements a and b such that a is superset of b.
     *
     * @param possibleExplanans The set of resulting possible explanans. Should
     * be empty on the function call.
     * @param covered Auxiliary set of nodes that where checked. Should be an
     * empty set on the function call.
     * @param list The set of all the existing EventNodes on the graph.
     * @param index The index of the node evaluation. Should be 0 on the
     * function call.
     * @param base_cover Auxiliary set of node currently being evaluated. Should
     * be an empty set on function call.
     * @return The minimalist set of possible explanans.
     */
    // N_e * N_c * N_c * N_e -> (N_e * N_c)²
    public static ArrayList<ComparableArrayList<ConditionNode>> calculateMinimalistPE(ArrayList<ComparableArrayList<ConditionNode>> possibleExplanans, ArrayList<EventNode> covered, ArrayList<EventNode> list, int index, ComparableArrayList<ConditionNode> base_cover) {
        // If the index is > list.size the all event nodes were checked, return.
        if (list.size() <= index) { // const
            possibleExplanans.add(base_cover); // const
            return possibleExplanans;
        }

        // Is the base_cover is already in the possibleExplanans set, the any further addidion will result in a superset, as such not being minimal.
        if (possibleExplanans.contains(base_cover)) {
            return possibleExplanans;
        }

        // Gets the current event being checked
        EventNode curr = list.get(index); // const

        // If the event is already convered by the conditions selected, skips to the next event.
        if (covered.contains(curr)) { // const
            return calculateMinimalistPE(possibleExplanans, covered, list, index + 1, base_cover); // const
        }

        // For every condition of the current event
        for (ConditionNode ax_c : curr.conditions) { // N  | Branching factor N_c
            // if the condition only connects to already covered events, skip to next condition.
            if (covered.containsAll(ax_c.events)) { // N² -> optimally n_e/8
                continue;
            }
            
            ArrayList<EventNode> covered_copy = (ArrayList<EventNode>) covered.clone(); // N_e
            covered_copy.addAll(ax_c.events); // N

            ComparableArrayList<ConditionNode> base_cover_copy = (ComparableArrayList<ConditionNode>) base_cover.clone(); // N_c
            base_cover_copy.add(ax_c); // const

            // If the base_cover is using a condition that is a proper subset of the condition being evaluated, remove the subset condition and replaces with the superset one.
            boolean isSuperset = false;
            for (ConditionNode subset : ax_c.supersetOf) { // N
                if (base_cover.contains(subset)) { // N -> optimally N_c*n_e/8
                    base_cover.remove(subset);
                    isSuperset = true;
                }
            }

            // If a superset swap was performed, includes every new event covered.
            if (isSuperset) {
                base_cover.add(ax_c);
                
                for (EventNode ax_e : ax_c.events) { // N -> optimally n_e/8
                    if (!covered.contains(ax_e)) { // N
                        covered.add(ax_e);
                    }
                }

                calculateMinimalistPE(possibleExplanans, covered, list, index + 1, base_cover);
                
            } else { // If no superset swap was performed, creates a copy of covered and adds all events connected to the evaluated condition, also creates a copy of base_cover and adds the current condition.


                // Branches to every condition connected to the event.
                calculateMinimalistPE(possibleExplanans, covered_copy, list, index + 1, base_cover_copy); // const
            }
        }

        return possibleExplanans;
    }

    /**
     * Auxiliary function that initialize the superset/subset values of the
     * ConditionNode of the graph.
     *
     * @param conditions The list of ConditionNode
     */
    public static void calculateSubsetOf(ArrayList<ConditionNode> conditions) {
        for (int i = 0; i < conditions.size(); i++) {
            ConditionNode n1 = conditions.get(i);

            for (int j = i + 1; j < conditions.size(); j++) {
                ConditionNode n2 = conditions.get(j);

                if (n2.events.containsAll(n1.events)) {
                    n1.addSubsetOf(n2);
                }else if(n1.events.containsAll(n2.events)){
                    n2.addSubsetOf(n1);
                }
            }
        }
    }

    /**
     * Auxiliary function that receives the list of CondionNode and EventNode,
     * with the respective connections (edges). It initilizes the sets by: i)
     * ordering the set of events; ii) calculating the subsets/supersets of the
     * conditions Properly calls the calculateMinimalistPE function.
     *
     * @param conditions The list of conditions
     * @param events The list of events
     * @return The set of resulting Minimalist Possible Explanans
     */
    public static ArrayList<ComparableArrayList<ConditionNode>> initializeAndCalculateMPE(ArrayList<ConditionNode> conditions, ArrayList<EventNode> events) {
        Collections.sort(events); // n*log(n)
        calculateSubsetOf(conditions); // CN^2*EN^2

        ArrayList<ComparableArrayList<ConditionNode>> possibleExplanans = new ArrayList<>();

        return calculateMinimalistPE(possibleExplanans, new ArrayList<>(), events, 0, new ComparableArrayList<>()); // (EN*CN)^2
    }
}
