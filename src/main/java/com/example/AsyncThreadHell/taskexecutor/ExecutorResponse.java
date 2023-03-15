package com.example.AsyncThreadHell.taskexecutor;

public class ExecutorResponse {

    private String status;
    private int value;

    public ExecutorResponse(String status) {
        this.status = status;
    }

    public ExecutorResponse(int value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
