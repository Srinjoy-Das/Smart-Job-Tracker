package com.srinjoy.jobtracker.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface JobDao {

    @Insert
    void insert(JobApplication jobApplication);

    @Update
    void update(JobApplication jobApplication);

    @Delete
    void delete(JobApplication jobApplication);

    @Query("DELETE FROM job_applications")
    void deleteAllJobs();

    @Query("SELECT * FROM job_applications ORDER BY id DESC")
    LiveData<List<JobApplication>> getAllJobs();
}