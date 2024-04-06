package com.ocado.basket;

import java.io.IOException;

public class ConfigurationException extends IOException {
    public ConfigurationException(String message) {
        super(message);
    }
}
