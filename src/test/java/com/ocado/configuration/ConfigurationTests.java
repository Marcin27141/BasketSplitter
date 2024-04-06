package com.ocado.configuration;

import com.ocado.basket.ConfigurationException;
import com.ocado.basket.ConfigurationReader;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigurationTests {
    @Test
    void shouldNotThrowExceptionOnValidConfigFile() {
        assertDoesNotThrow(() -> {
            ConfigurationReader.getDeliveryForProducts("src/test/java/com/ocado/configuration/valid_config_file.json");
        });
    }

    @Test
    void shouldThrowExceptionOnNonexistentConfigFile() {
        var nonexistentFilepath = "/non/existent/filepath/file.json";
        Throwable exception = assertThrows(ConfigurationException.class, () -> {
            ConfigurationReader.getDeliveryForProducts(nonexistentFilepath);
        });
        assertEquals("There was a problem while reading a configuration file: " + nonexistentFilepath, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionOnInvalidFileFormat() {
        var invalidConfigFilepath = "src/test/java/com/ocado/configuration/invalid_config_format.json";
        Throwable exception = assertThrows(ConfigurationException.class, () -> {
            ConfigurationReader.getDeliveryForProducts(invalidConfigFilepath);
        });
        assertEquals("There was a problem while reading a configuration file: " + invalidConfigFilepath, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionOnProductsWithoutDeliveryOption() {
        var productsWithoutDeliveryConfigFile = "src/test/java/com/ocado/configuration/no_delivery_for_products_config.json";
        Throwable exception = assertThrows(ConfigurationException.class, () -> {
            ConfigurationReader.getDeliveryForProducts(productsWithoutDeliveryConfigFile);
        });
        assertTrue(exception.getMessage().startsWith("No delivery specified for following products:"));
    }
}
