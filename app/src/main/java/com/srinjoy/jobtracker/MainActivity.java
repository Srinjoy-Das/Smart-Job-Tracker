package com.srinjoy.jobtracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.srinjoy.jobtracker.adapter.JobAdapter;
import com.srinjoy.jobtracker.viewmodel.JobViewModel;

public class MainActivity extends AppCompatActivity {

    private JobViewModel jobViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RecyclerView setup
        RecyclerView recyclerView = findViewById(R.id.recycler_view_jobs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        JobAdapter adapter = new JobAdapter();
        recyclerView.setAdapter(adapter);

        jobViewModel = new ViewModelProvider(this).get(JobViewModel.class);

        jobViewModel.getAllJobs().observe(this, jobs -> {
            adapter.setJobs(jobs);
            setTitle("Job Tracker (" + jobs.size() + ")");
        });

        // Add new job
        FloatingActionButton buttonAddJob = findViewById(R.id.button_add_job);
        buttonAddJob.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditJobActivity.class);
            startActivity(intent);
        });

        adapter.setOnItemClickListener(jobApplication -> {
            Intent intent = new Intent(MainActivity.this, AddEditJobActivity.class);

            intent.putExtra("id", jobApplication.getId());
            intent.putExtra("company", jobApplication.getCompanyName());
            intent.putExtra("role", jobApplication.getJobRole());
            intent.putExtra("status", jobApplication.getStatus());

             intent.putExtra("date", jobApplication.getApplicationDate());
             intent.putExtra("notes", jobApplication.getNotes());

            startActivity(intent);
        });

        // Swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
        ) {
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                jobViewModel.delete(
                        adapter.getJobAt(viewHolder.getAdapterPosition())
                );

                Snackbar.make(
                        recyclerView,
                        "Job application deleted",
                        Snackbar.LENGTH_SHORT
                ).show();
            }
        }).attachToRecyclerView(recyclerView);
    }
}