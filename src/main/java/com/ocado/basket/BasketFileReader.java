package com.ocado.basket;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class BasketFileReader {
    public static List<String> getProductsFromBasket(String filepath) throws BasketException {
        TypeReference<List<String>> typeRef = new TypeReference<>() {};
        try {
            return new ObjectMapper().readValue(Paths.get(filepath).toFile(), typeRef);
        } catch (IOException e) {
            throw new BasketException("There was a problem while reading a basket file: " + filepath);
        }
    }
}
