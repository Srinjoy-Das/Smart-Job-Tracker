package com.srinjoy.jobtracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.srinjoy.jobtracker.R;
import com.srinjoy.jobtracker.data.JobApplication;

import java.util.ArrayList;
import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    private List<JobApplication> jobList = new ArrayList<>();

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_item, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        JobApplication currentJob = jobList.get(position);

        holder.textViewCompany.setText(currentJob.getCompanyName());
        holder.textViewRole.setText(currentJob.getJobRole());
        holder.textViewStatus.setText(currentJob.getStatus());
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public void setJobs(List<JobApplication> jobs) {
        this.jobList = jobs;
        notifyDataSetChanged();
    }

    public JobApplication getJobAt(int position) {
        return jobList.get(position);
    }

    static class JobViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewCompany;
        private final TextView textViewRole;
        private final TextView textViewStatus;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewCompany = itemView.findViewById(R.id.text_view_company);
            textViewRole = itemView.findViewById(R.id.text_view_role);
            textViewStatus = itemView.findViewById(R.id.text_view_status);
        }
    }
}