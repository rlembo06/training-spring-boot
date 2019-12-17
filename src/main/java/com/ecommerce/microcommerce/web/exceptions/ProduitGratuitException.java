package com.ecommerce.microcommerce.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Le produit ne doit pas être gratuit")
public class ProduitGratuitException extends Exception {

    public ProduitGratuitException() {

    }


}
