package com.ecommerce.microcommerce.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProduitIntrouvableException.class)
    public ResponseEntity<CustomErrorResponse> customHandleNotFound(Exception ex, WebRequest request) {
        String dateError = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        CustomErrorResponse errors = new CustomErrorResponse(dateError, HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProduitBadRequest.class)
    public ResponseEntity<CustomErrorResponse> customBadRequest(Exception ex, WebRequest request) {
        String dateError = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        CustomErrorResponse errors = new CustomErrorResponse(dateError, HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProduitGratuitException.class)
    public ResponseEntity<CustomErrorResponse> customNotAcceptable(Exception ex, WebRequest request) {
        String dateError = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        CustomErrorResponse errors = new CustomErrorResponse(dateError, HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ProduitInternalError.class)
    public ResponseEntity<CustomErrorResponse> customInternalError(Exception ex, WebRequest request) {
        String dateError = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        CustomErrorResponse errors = new CustomErrorResponse(dateError, HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnAuthException.class)
    public ResponseEntity<CustomErrorResponse> customUnAuthorized(Exception ex, WebRequest request) {
        String dateError = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        CustomErrorResponse errors = new CustomErrorResponse(dateError, HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }
}
