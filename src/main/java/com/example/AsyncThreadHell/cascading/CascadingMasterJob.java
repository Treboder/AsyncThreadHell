package com.example.AsyncThreadHell.cascading;

import com.example.AsyncThreadHell.AsyncThreadHellApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CascadingMasterJob {

    private static Logger logger = LoggerFactory.getLogger(AsyncThreadHellApplication.class);

    public enum JobStatus { CREATED, QUEUED, RUNNING, FINISHED, DELETED };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long runID;                 // unique id for persistence (created by JPA)
    private String jobID;               // unique id during runtime (created by jobRunr)
    private String jobDescription;      // human-readable info (e.g. used by jobRunr Dashboard)
    private JobStatus jobStatus;        // current status from job creation to deletion
    private int totalSubTasks;          // number of subtasks to spawn
    private int maxConcurrentThreads;   // maximum number of concurrent threads for the job
    private int runningSubTasks;        // number of concurrently running subtask
    private int queuedSubTasks;         // number of queued subtasks

    public CascadingMasterJob() {

    }

    public CascadingMasterJob(CascadingRequest request) {
        setJobDescription(request.getJobDescription());
        setJobStatus(JobStatus.CREATED);
        setTotalSubTasks(request.getTotalSubTasks());
        setMaxConcurrentThreads(request.getMaxConcurrentThreads());
    }

    public int getRemainingJobs() {
        return getQueuedSubTasks() + getRunningSubTasks();
    }

    public int getFinishedJobs() {
        return getTotalSubTasks() - getRemainingJobs();
    }

    public int getProgressInPercent() {
        return  100* (getTotalSubTasks() - getRemainingJobs()) / getTotalSubTasks();
    }

    public Long getRunID() {
        return runID;
    }

    public void setRunID(Long runID) {
        this.runID = runID;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
        this.jobStatus = JobStatus.QUEUED; // JobRunr returns a jobID after queueing the job
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
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

    public int getRunningSubTasks() {
        return runningSubTasks;
    }

    public void setRunningSubTasks(int runningSubTasks) {
        this.runningSubTasks = runningSubTasks;
    }

    public int getQueuedSubTasks() {
        return queuedSubTasks;
    }

    public void setQueuedSubTasks(int queuedSubTasks) {
        this.queuedSubTasks = queuedSubTasks;
    }
}
