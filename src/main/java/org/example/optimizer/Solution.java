package org.example.optimizer;

import java.util.List;
import java.util.Map;

public class Solution {
    private Map<String, List<String>> deliveries;
    private int maxGroupSize;

    public Solution(Map<String, List<String>> deliveries, int maxGroupSize) {
        this.deliveries = deliveries;
        this.maxGroupSize = maxGroupSize;
    }

    private int numberOfGroups() {
        return deliveries.size();
    }
    public boolean isBetterThan(Solution anotherSolution) {
        if (numberOfGroups() != anotherSolution.numberOfGroups())
            return numberOfGroups() < anotherSolution.numberOfGroups();
        return maxGroupSize > anotherSolution.maxGroupSize;
    }

    public static Solution getSolutionWithAdditionalGroup(String delivery, List<String> products, Solution solution) {
        if (!products.isEmpty())
            solution.deliveries.put(delivery, products);
        return new Solution(
                solution.deliveries,
                Math.max(products.size(), solution.maxGroupSize)
        );
    }

    public void presentSolution() {
        System.out.format("Number of groups: %d, max group size: %d\n", deliveries.values().stream().filter(l -> !l.isEmpty()).count(), maxGroupSize);
        for (var entry : deliveries.entrySet()) {
            System.out.print(entry.getKey() + ": ");
            for (var product : entry.getValue()) {
                System.out.print(product + ", ");
            }
            System.out.println();
        }
    }
}
