package com.ocado.basket;

import java.io.IOException;

public class BasketException extends IOException {
    public BasketException(String message) {
        super(message);
    }
}
