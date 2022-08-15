package com.example.exception.dto;

public class Error {
    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInvlid() {
        return invlid;
    }

    public void setInvlid(String invlid) {
        this.invlid = invlid;
    }

    private String field;
    private String message;
    private String invlid;
}
