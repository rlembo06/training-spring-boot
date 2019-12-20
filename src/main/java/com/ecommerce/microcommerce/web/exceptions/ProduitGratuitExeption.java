package com.ecommerce.microcommerce.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ProduitGratuitExeption extends RuntimeException {
    public ProduitGratuitExeption(String s) {
        super(s);
    }
}
