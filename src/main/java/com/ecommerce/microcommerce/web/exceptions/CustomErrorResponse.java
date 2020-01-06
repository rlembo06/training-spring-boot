package com.ecommerce.microcommerce.web.exceptions;

import java.util.Date;

public class CustomErrorResponse {

    private String date;
    private int status;
    private String error;

    public CustomErrorResponse(String date, int status, String error) {
        this.date = date;
        this.status = status;
        this.error = error;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
