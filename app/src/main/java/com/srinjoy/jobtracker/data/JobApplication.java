package com.srinjoy.jobtracker.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "job_applications")
public class JobApplication {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String companyName;
    private String jobRole;
    private String status;
    private String applicationDate;
    private String interviewDate;
    private String salary;
    private String notes;

    public JobApplication(String companyName,
                          String jobRole,
                          String status,
                          String applicationDate,
                          String interviewDate,
                          String salary,
                          String notes) {
        this.companyName = companyName;
        this.jobRole = jobRole;
        this.status = status;
        this.applicationDate = applicationDate;
        this.interviewDate = interviewDate;
        this.salary = salary;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(String interviewDate) {
        this.interviewDate = interviewDate;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}