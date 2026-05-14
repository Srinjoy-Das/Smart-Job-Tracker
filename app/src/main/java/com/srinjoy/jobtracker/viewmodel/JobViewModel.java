package com.srinjoy.jobtracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.srinjoy.jobtracker.data.JobApplication;
import com.srinjoy.jobtracker.repository.JobRepository;

import java.util.List;

public class JobViewModel extends AndroidViewModel {

    private final JobRepository repository;
    private final LiveData<List<JobApplication>> allJobs;

    public JobViewModel(@NonNull Application application) {
        super(application);
        repository = new JobRepository(application);
        allJobs = repository.getAllJobs();
    }

    public void insert(JobApplication jobApplication) {
        repository.insert(jobApplication);
    }

    public void update(JobApplication jobApplication) {
        repository.update(jobApplication);
    }

    public void delete(JobApplication jobApplication) {
        repository.delete(jobApplication);
    }

    public void deleteAllJobs() {
        repository.deleteAllJobs();
    }

    public LiveData<List<JobApplication>> getAllJobs() {
        return allJobs;
    }
}