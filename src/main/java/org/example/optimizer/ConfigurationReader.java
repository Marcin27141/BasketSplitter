package org.example.optimizer;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigurationReader {
    public static List<String> getProductsFromCart(String filepath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<?> products = mapper.readValue(Paths.get(filepath).toFile(), List.class);
            return products.stream()
                    .filter(e -> e instanceof String)
                    .map(e -> ((String) e))
                    .collect(Collectors.toList());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Map<String, List<String>> getDeliveryForProducts(String filepath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, List<String>> deliveryForProduct = mapper.readValue(Paths.get(filepath).toFile(), Map.class);
            return deliveryForProduct;
        }
        catch (DatabindException ex) {
            System.out.println("Incorrect file format");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
