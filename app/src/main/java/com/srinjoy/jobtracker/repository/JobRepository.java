package com.srinjoy.jobtracker.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.srinjoy.jobtracker.data.AppDatabase;
import com.srinjoy.jobtracker.data.JobApplication;
import com.srinjoy.jobtracker.data.JobDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JobRepository {

    private final JobDao jobDao;
    private final LiveData<List<JobApplication>> allJobs;
    private final ExecutorService executorService;

    public JobRepository(Application application) {

        AppDatabase database = Room.databaseBuilder(
                application,
                AppDatabase.class,
                "job_database"
        ).build();

        jobDao = database.jobDao();
        allJobs = jobDao.getAllJobs();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(JobApplication jobApplication) {
        executorService.execute(() -> jobDao.insert(jobApplication));
    }

    public void update(JobApplication jobApplication) {
        executorService.execute(() -> jobDao.update(jobApplication));
    }

    public void delete(JobApplication jobApplication) {
        executorService.execute(() -> jobDao.delete(jobApplication));
    }

    public void deleteAllJobs() {
        executorService.execute(jobDao::deleteAllJobs);
    }

    public LiveData<List<JobApplication>> getAllJobs() {
        return allJobs;
    }
}