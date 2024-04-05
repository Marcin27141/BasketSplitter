package org.example;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.optimizer.CartOptimizer;
import org.example.optimizer.Solution;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    private static final String CART_FILEPATH = "C:\\Users\\marci\\Downloads\\Zadanie\\Zadanie\\basket-1.json";
    private static final String DELIVERY_CONFIG_FILEPATH = "C:\\Users\\marci\\Downloads\\Zadanie\\Zadanie\\config.json";

    public static void main(String[] args) {
        var optimizer = new CartOptimizer(CART_FILEPATH, DELIVERY_CONFIG_FILEPATH);
        var solution = optimizer.getOptimizedSolution();
        solution.presentSolution();
    }


}