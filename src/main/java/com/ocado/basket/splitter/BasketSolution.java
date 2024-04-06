package com.ocado.basket.splitter;

import java.util.List;
import java.util.Map;

public record BasketSolution(Map<String, List<String>> deliveries, int maxGroupSize) {

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
