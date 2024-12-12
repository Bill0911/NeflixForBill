package com.example.netflix.dto;

public class MethodResponse {
    private boolean success;

    private String message;

    public MethodResponse() {
        this.success = false;
        this.message = "";
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
