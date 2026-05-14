package com.srinjoy.jobtracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.srinjoy.jobtracker.adapter.JobAdapter;
import com.srinjoy.jobtracker.data.JobApplication;
import com.srinjoy.jobtracker.viewmodel.JobViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private JobViewModel jobViewModel;

    private TextView textViewTotal;
    private TextView textViewInterviews;
    private TextView textViewOffers;
    private TextView textViewRejected;
    private JobAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestNotificationPermission();

        textViewTotal = findViewById(R.id.text_view_total);
        textViewInterviews = findViewById(R.id.text_view_interviews);
        textViewOffers = findViewById(R.id.text_view_offers);
        textViewRejected = findViewById(R.id.text_view_rejected);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_jobs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        com.google.android.material.button.MaterialButton buttonExportCsv =
                findViewById(R.id.button_export_csv);

        buttonExportCsv.setOnClickListener(v -> {
            exportToCsv();
            scheduleTestInterviewReminder();
        });

        adapter = new JobAdapter();
        recyclerView.setAdapter(adapter);

        jobViewModel = new ViewModelProvider(this).get(JobViewModel.class);

        jobViewModel.getAllJobs().observe(this, jobs -> {
            adapter.setJobs(jobs);
            setTitle("Job Tracker (" + jobs.size() + ")");
            updateDashboard(jobs);
        });

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
            intent.putExtra("applicationDate", jobApplication.getApplicationDate());
            intent.putExtra("interviewDate", jobApplication.getInterviewDate());
            intent.putExtra("salary", jobApplication.getSalary());
            intent.putExtra("notes", jobApplication.getNotes());

            startActivity(intent);
        });

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

    private void requestNotificationPermission() {
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.TIRAMISU) {

            if (checkSelfPermission(
                    android.Manifest.permission.POST_NOTIFICATIONS)
                    != android.content.pm.PackageManager.PERMISSION_GRANTED) {

                requestPermissions(
                        new String[]{
                                android.Manifest.permission.POST_NOTIFICATIONS
                        },
                        100
                );
            }
        }
    }


    private void updateDashboard(List<JobApplication> jobs) {
        int total = jobs.size();
        int interviews = 0;
        int offers = 0;
        int rejected = 0;

        for (JobApplication job : jobs) {
            String status = job.getStatus();

            if (status == null) continue;

            switch (status.trim().toLowerCase()) {
                case "interview":
                    interviews++;
                    break;

                case "offer":
                    offers++;
                    break;

                case "rejected":
                    rejected++;
                    break;
            }
        }

        textViewTotal.setText("Total Applications: " + total);
        textViewInterviews.setText("Interviews: " + interviews);
        textViewOffers.setText("Offers: " + offers);
        textViewRejected.setText("Rejected: " + rejected);
    }

    private void exportToCsv() {
        try {
            File csvFile = new File(getCacheDir(), "job_applications.csv");
            FileWriter writer = new FileWriter(csvFile);

            writer.append("Company,Role,Status,Application Date,Interview Date,Salary,Notes\n");

            for (int i = 0; i < adapter.getItemCount(); i++) {
                com.srinjoy.jobtracker.data.JobApplication job =
                        adapter.getJobAt(i);

                writer.append(escapeCsv(job.getCompanyName())).append(",");
                writer.append(escapeCsv(job.getJobRole())).append(",");
                writer.append(escapeCsv(job.getStatus())).append(",");
                writer.append(escapeCsv(job.getApplicationDate())).append(",");
                writer.append(escapeCsv(job.getInterviewDate())).append(",");
                writer.append(escapeCsv(job.getSalary())).append(",");
                writer.append(escapeCsv(job.getNotes())).append("\n");
            }

            writer.flush();
            writer.close();

            // Share file using FileProvider
            Uri uri = FileProvider.getUriForFile(
                    this,
                    getPackageName() + ".provider",
                    csvFile
            );

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/csv");
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Job Applications CSV");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivity(
                    Intent.createChooser(shareIntent, "Share CSV File")
            );

        } catch (IOException e) {
            e.printStackTrace();

            Snackbar.make(
                    findViewById(R.id.recycler_view_jobs),
                    "Failed to export CSV",
                    Snackbar.LENGTH_LONG
            ).show();
        }
    }

    private String escapeCsv(String value) {
        if (value == null) {
            return "";
        }

        value = value.replace("\"", "\"\"");

        return "\"" + value + "\"";
    }

    private void scheduleTestInterviewReminder() {
        android.app.AlarmManager alarmManager =
                (android.app.AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(this, InterviewReminderReceiver.class);
        intent.putExtra("company", "Google");
        intent.putExtra("role", "Software Engineer Intern");

        android.app.PendingIntent pendingIntent =
                android.app.PendingIntent.getBroadcast(
                        this,
                        1,
                        intent,
                        android.app.PendingIntent.FLAG_UPDATE_CURRENT
                                | android.app.PendingIntent.FLAG_IMMUTABLE
                );

        long triggerAtMillis =
                System.currentTimeMillis() + 10_000; // 10 seconds later

        if (alarmManager != null) {
            alarmManager.setExact(
                    android.app.AlarmManager.RTC_WAKEUP,
                    triggerAtMillis,
                    pendingIntent
            );
        }

        Toast.makeText(
                this,
                "Test reminder scheduled for 10 seconds from now",
                Toast.LENGTH_LONG
        ).show();
    }

}