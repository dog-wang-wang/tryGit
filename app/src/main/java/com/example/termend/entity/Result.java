package com.example.termend.entity;

public class Result<T> {
    private Integer statusCode ;
    private String message ;
    private T data;
    public Integer getStatusCode() {
        return statusCode;
    }
    public String getMessage() {
        return message;
    }
    public T getData() {
        return data;
    }
    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setData(T data) {
        this.data = data;
    }
}
