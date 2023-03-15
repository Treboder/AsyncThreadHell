package com.example.AsyncThreadHell.taskrunner;

public class RunnerResponse {

    private String status;
    private int value;

    public RunnerResponse(String status) {
        this.status = status;
    }

    public RunnerResponse(int value) {
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