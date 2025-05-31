package com.example.netflix.entity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseItem {
    private String message;
    private HttpStatus status;

    public ResponseItem(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public static ResponseEntity<Object> errorCheckForRelationItemsPOST(String message) {
        if (message.contains("Duplicate")) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponseItem("Custom error: This relation already exists", HttpStatus.NOT_ACCEPTABLE));
        }
        else if (message.contains("foreign key constraint fails")) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponseItem("Custom error: At least one of the IDs does not exist", HttpStatus.NOT_ACCEPTABLE));
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Failed to delete: " + message);
    }

}
