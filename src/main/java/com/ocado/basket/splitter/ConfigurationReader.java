package com.ocado.basket.splitter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocado.basket.splitter.exceptions.ConfigurationException;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class ConfigurationReader {
    public static Map<String, List<String>> getDeliveryForProducts(String filepath) throws ConfigurationException {
        TypeReference<Map<String, List<String>>> typeRef = new TypeReference<>() {};
        Map<String, List<String>> result;

        try {
            result = new ObjectMapper().readValue(Paths.get(filepath).toFile(), typeRef);
        } catch (IOException e) {
            throw new ConfigurationException("There was a problem while reading a configuration file: " + filepath);
        }

        makeSureNoProductsWithoutDelivery(result);
        return result;
    }

    private static void makeSureNoProductsWithoutDelivery(Map<String, List<String>> result) throws ConfigurationException {
        var productsWithoutDelivery = result.entrySet().stream().filter(kvp -> kvp.getValue().isEmpty()).map(Map.Entry::getKey).toList();
        if (!productsWithoutDelivery.isEmpty()) {
            throw new ConfigurationException("No delivery specified for following products: " + String.join(", ", productsWithoutDelivery));
        }
    }
}
