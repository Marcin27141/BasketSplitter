package com.ocado.basket;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BasketSolution {
    private final Map<String, List<String>> deliveries;
    private final int maxGroupSize;

    public BasketSolution(Map<String, List<String>> deliveries, int maxGroupSize) {
        this.deliveries = deliveries;
        this.maxGroupSize = maxGroupSize;
    }

    public Map<String, List<String>> getDeliveries() {
        return deliveries;
    }

    public int getMaxGroupSize() {
        return maxGroupSize;
    }

    private int numberOfGroups() {
        return deliveries.size();
    }
    public boolean isBetterThan(BasketSolution anotherSolution) {
        if (numberOfGroups() != anotherSolution.numberOfGroups())
            return numberOfGroups() < anotherSolution.numberOfGroups();
        return maxGroupSize > anotherSolution.maxGroupSize;
    }

    public static BasketSolution getSolutionWithAdditionalGroup(String delivery, List<String> products, BasketSolution solution) {
        if (!products.isEmpty())
            solution.deliveries.put(delivery, products);
        return new BasketSolution(
                solution.deliveries,
                Math.max(products.size(), solution.maxGroupSize)
        );
    }
}
