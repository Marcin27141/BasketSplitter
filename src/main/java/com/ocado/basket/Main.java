package com.ocado.basket;

import com.ocado.basket.splitter.BasketSplitter;
import com.ocado.basket.splitter.exceptions.ConfigurationException;
import com.ocado.basket.splitter.exceptions.IncompleteConfigurationException;

import java.util.List;
import java.util.Map;

public class Main {
    private static final String BASKET_FILEPATH = "C:\\Users\\marci\\Downloads\\Zadanie\\Zadanie\\basket-2.json";
    private static final String DELIVERY_CONFIG_FILEPATH = "C:\\Users\\marci\\Downloads\\Zadanie\\Zadanie\\config.json";

    public static void main(String[] args) {
        try {
            splitBasket();
        } catch (BasketException | ConfigurationException | IncompleteConfigurationException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void splitBasket() throws BasketException, ConfigurationException, IncompleteConfigurationException {
        List<String> cartProducts = BasketFileReader.getProductsFromBasket(BASKET_FILEPATH);
        var basketSplitter = new BasketSplitter(DELIVERY_CONFIG_FILEPATH);
        var solution = basketSplitter.split(cartProducts);
        presentSolution(solution);
    }

    public static void presentSolution(Map<String, List<String>> solution) {
        System.out.println("Number of groups: " + solution.size());
        solution.entrySet().forEach(System.out::println);
    }


}