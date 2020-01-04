package com.ecommerce.microcommerce.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ProduitInternalErrorException extends RuntimeException {
    public ProduitInternalErrorException(String s) {
        super(s);
    }
}
