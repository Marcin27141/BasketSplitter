package com.ocado.basket.split;

import com.ocado.basket.BasketException;
import com.ocado.basket.BasketFileReader;
import com.ocado.basket.splitter.BasketSplitter;
import com.ocado.basket.splitter.exceptions.ConfigurationException;
import com.ocado.basket.splitter.exceptions.IncompleteConfigurationException;
import org.junit.jupiter.api.Test;
import java.util.Comparator;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasketSplitterSplitTests {

    @Test
    void allProductsShouldBeSendWithSameDeliveryMethod() throws BasketException, ConfigurationException, IncompleteConfigurationException {
        var products = BasketFileReader.getProductsFromBasket("src/test/java/com/ocado/basket/split/all_products_same_delivery_basket.json");
        var splitter = new BasketSplitter("src/test/java/com/ocado/basket/split/all_products_in_same_delivery.json");
        var solution = splitter.split(products);
        assertEquals(solution.size(), 1);
        assertEquals(
                solution.values().stream().max(Comparator.comparingInt(List::size)).get().size(),
                products.size()
        );
    }

    @Test
    void eachProductShouldBeSendWithSeparateDeliveryMethod() throws BasketException, ConfigurationException, IncompleteConfigurationException {
        var products = BasketFileReader.getProductsFromBasket("src/test/java/com/ocado/basket/split/separate_delivery_for_products_basket.json");
        var splitter = new BasketSplitter("src/test/java/com/ocado/basket/split/separate_delivery_for_each_product.json");
        var solution = splitter.split(products);
        assertEquals(solution.size(), products.size());
        assertEquals(
                solution.values().stream().max(Comparator.comparingInt(List::size)).get().size(),
                1
        );
    }
}
