package org.example.optimizer;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CartOptimizer {
    private final String cartFilepath;
    private final String deliveryConfigPath;
    private List<String> cartProducts;
    private Map<String, List<String>> deliveryForProducts;

    public CartOptimizer(String cartFilepath, String deliveryConfigPath) {
        this.cartFilepath = cartFilepath;
        this.deliveryConfigPath = deliveryConfigPath;
    }

    public Solution getOptimizedSolution() {
        cartProducts = ConfigurationReader.getProductsFromCart(cartFilepath);
        deliveryForProducts = ConfigurationReader.getDeliveryForProducts(deliveryConfigPath);
        var deliveryMethods = deliveryForProducts.values().stream().flatMap(List::stream).collect(Collectors.toSet());
        return getBestSolutionForDeliveries(deliveryMethods, cartProducts);
    }

    private Solution getBestSolutionForDeliveries(Set<String> deliveries, List<String> products) {
        if (deliveries.size() == 1) {
            Map<String, List<String>> map = new HashMap<>();
            if (!products.isEmpty()) {
                map = deliveries.stream().collect(Collectors.groupingBy(Function.identity()));
            }
            return new Solution(map, products.size()); //TODO filter products for given delivery?
        }
        Solution bestScore = null;
        for (var deliveryMethod : deliveries) {
            var remainingDeliveries = deliveries.stream().filter(d -> !d.equals(deliveryMethod)).collect(Collectors.toSet());
            var productsBelongingToDelivery = products.stream().collect(Collectors.partitioningBy(p -> deliveryForProducts.get(p).contains(deliveryMethod)));
            Solution bestRemainingScore = getBestSolutionForDeliveries(remainingDeliveries, productsBelongingToDelivery.get(false));
            Solution bestLocalScore = Solution.getSolutionWithAdditionalGroup(deliveryMethod, productsBelongingToDelivery.get(true), bestRemainingScore);
            if (bestScore == null || bestLocalScore.isBetterThan(bestScore)) {
                bestScore = bestLocalScore;
            }
        }
        return bestScore;
    }
}
