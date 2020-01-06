package com.ecommerce.microcommerce.web.exceptions;

public class CustomErrorResponse {

    private String date;
    private int status;
    private String error;

    public CustomErrorResponse(String date, int status, String error) {
        this.date = date;
        this.status = status;
        this.error = error;
    }
}
