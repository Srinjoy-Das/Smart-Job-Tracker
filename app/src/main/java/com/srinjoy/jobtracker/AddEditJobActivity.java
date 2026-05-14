package com.srinjoy.jobtracker;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.srinjoy.jobtracker.data.JobApplication;
import com.srinjoy.jobtracker.viewmodel.JobViewModel;

public class AddEditJobActivity extends AppCompatActivity {

    private EditText editTextCompany;
    private EditText editTextRole;
    private EditText editTextStatus;
    private EditText editTextApplicationDate;
    private EditText editTextInterviewDate;
    private EditText editTextSalary;
    private EditText editTextNotes;

    private JobViewModel jobViewModel;
    private int jobId = -1;   // -1 means Add Mode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_job);

        editTextCompany = findViewById(R.id.edit_text_company);
        editTextRole = findViewById(R.id.edit_text_role);
        editTextStatus = findViewById(R.id.edit_text_status);
        editTextApplicationDate = findViewById(R.id.edit_text_application_date);
        editTextInterviewDate = findViewById(R.id.edit_text_interview_date);
        editTextSalary = findViewById(R.id.edit_text_salary);
        editTextNotes = findViewById(R.id.edit_text_notes);

        Button buttonSave = findViewById(R.id.button_save_job);

        jobViewModel = new ViewModelProvider(this).get(JobViewModel.class);

        if (getIntent().hasExtra("id")) {
            setTitle("Edit Job Application");

            jobId = getIntent().getIntExtra("id", -1);

            editTextCompany.setText(getIntent().getStringExtra("company"));
            editTextRole.setText(getIntent().getStringExtra("role"));
            editTextStatus.setText(getIntent().getStringExtra("status"));

            editTextApplicationDate.setText(
                    getIntent().getStringExtra("applicationDate")
            );
            editTextInterviewDate.setText(
                    getIntent().getStringExtra("interviewDate")
            );
            editTextSalary.setText(
                    getIntent().getStringExtra("salary")
            );
            editTextNotes.setText(
                    getIntent().getStringExtra("notes")
            );

        } else {
            setTitle("Add Job Application");
        }

        buttonSave.setOnClickListener(v -> saveJob());
    }

    private void saveJob() {
        String company = editTextCompany.getText().toString().trim();
        String role = editTextRole.getText().toString().trim();
        String status = editTextStatus.getText().toString().trim();
        String applicationDate =
                editTextApplicationDate.getText().toString().trim();
        String interviewDate =
                editTextInterviewDate.getText().toString().trim();
        String salary = editTextSalary.getText().toString().trim();
        String notes = editTextNotes.getText().toString().trim();

        if (TextUtils.isEmpty(company)
                || TextUtils.isEmpty(role)
                || TextUtils.isEmpty(status)) {

            Toast.makeText(
                    this,
                    "Please fill Company, Role and Status",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        JobApplication jobApplication = new JobApplication(
                company,
                role,
                status,
                applicationDate,
                interviewDate,
                salary,
                notes
        );


        if (jobId != -1) {
            jobApplication.setId(jobId);
            jobViewModel.update(jobApplication);

            Toast.makeText(
                    this,
                    "Job application updated successfully",
                    Toast.LENGTH_SHORT
            ).show();
        }
        else {
            jobViewModel.insert(jobApplication);

            Toast.makeText(
                    this,
                    "Job application saved successfully",
                    Toast.LENGTH_SHORT
            ).show();
        }

        finish();
    }
}