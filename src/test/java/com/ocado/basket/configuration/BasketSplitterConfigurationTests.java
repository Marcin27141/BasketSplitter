package com.ocado.basket.configuration;

import com.ocado.basket.splitter.BasketSplitter;
import com.ocado.basket.splitter.exceptions.ConfigurationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BasketSplitterConfigurationTests {
    @Test
    void shouldNotThrowExceptionOnValidConfigFile() {
        assertDoesNotThrow(() -> {
            new BasketSplitter("src/test/java/com/ocado/basket/configuration/valid_config_file.json");
        });
    }

    @Test
    void shouldThrowExceptionOnNonexistentConfigFile() {
        var nonexistentFilepath = "/non/existent/filepath/file.json";
        Throwable exception = assertThrows(ConfigurationException.class, () -> {
            new BasketSplitter(nonexistentFilepath);
        });
        assertEquals("There was a problem while reading a configuration file: " + nonexistentFilepath, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionOnInvalidFileFormat() {
        var invalidConfigFilepath = "src/test/java/com/ocado/basket/configuration/invalid_config_format.json";
        Throwable exception = assertThrows(ConfigurationException.class, () -> {
            new BasketSplitter(invalidConfigFilepath);
        });
        assertEquals("There was a problem while reading a configuration file: " + invalidConfigFilepath, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionOnProductsWithoutDeliveryOption() {
        var productsWithoutDeliveryConfigFile = "src/test/java/com/ocado/basket/configuration/no_delivery_for_products_config.json";
        Throwable exception = assertThrows(ConfigurationException.class, () -> {
            new BasketSplitter(productsWithoutDeliveryConfigFile);
        });
        assertTrue(exception.getMessage().startsWith("No delivery specified for following products:"));
    }
}
