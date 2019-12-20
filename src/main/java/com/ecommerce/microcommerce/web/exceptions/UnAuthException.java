package com.ecommerce.microcommerce.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthException extends RuntimeException {
    public UnAuthException(String s) {
        super(s);
    }
    public UnAuthException() {
        super("Utilisateur non authorisé");
    }
}
