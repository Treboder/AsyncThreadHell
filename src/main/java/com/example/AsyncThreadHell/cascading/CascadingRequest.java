package com.example.AsyncThreadHell.cascading;

public class CascadingRequest {

    private String jobDescription;      // human-readable info (e.g. used by jobRunr Dashboard)
    private int totalSubTasks;          // number of subtasks to spawn
    private int maxConcurrentThreads;   // maximum number of concurrent threads for the job

    public CascadingRequest() { }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public int getTotalSubTasks() {
        return totalSubTasks;
    }

    public void setTotalSubTasks(int totalSubTasks) {
        this.totalSubTasks = totalSubTasks;
    }

    public int getMaxConcurrentThreads() {
        return maxConcurrentThreads;
    }

    public void setMaxConcurrentThreads(int maxConcurrentThreads) {
        this.maxConcurrentThreads = maxConcurrentThreads;
    }
}
