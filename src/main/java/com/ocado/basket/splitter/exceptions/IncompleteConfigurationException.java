package com.ocado.basket.splitter.exceptions;

import java.io.IOException;

public class IncompleteConfigurationException extends IOException {
    public IncompleteConfigurationException(String message) {
        super(message);
    }
}
