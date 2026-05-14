# 📌 Smart Job Tracker

Smart Job Tracker is a professional Android application that helps students and job seekers organize and monitor their internship and job applications in one place.

The app allows users to add, view, and delete job applications while storing all data locally using Room Database. It follows the MVVM architectural pattern and uses LiveData for automatic UI updates, making it a strong resume-worthy Android project.

---

## ✨ Features

- ➕ Add new job applications
- 📋 View all saved applications
- 📋 Edit existing applications
- 🗑️ Swipe left or right to delete applications
- 💾 Local data storage using Room Database
- 🔄 Automatic UI updates with LiveData
- 🏗️ MVVM Architecture
- 📱 RecyclerView-based card layout
- 🎨 Material Design interface
- 📊 Dashboard Statistics
  - Total Applications
  - Interviews Scheduled
  - Offers Received
  - Rejected Applications
    
- 📄 CSV Export
- 🔔 Interview Reminders
  - Local notifications for upcoming interviews
  - Runtime notification permission support for Android 13+

---

## 📊 Data Stored for Each Application

- Company Name
- Job Role
- Application Status
- Application Date
- Interview Date
- Expected Salary
- Notes

---

## 🛠️ Tech Stack

- Java
- Android Studio
- Room Database
- MVVM Architecture
- LiveData
- RecyclerView
- Material Design Components
- FileProvider
- AlarmManager
- BroadcastReceiver
- Android NotificationManager

---

📂 Project Architecture

com.srinjoy.jobtracker
├── adapter
│   └── JobAdapter.java
├── data
│   ├── AppDatabase.java
│   ├── JobApplication.java
│   └── JobDao.java
├── repository
│   └── JobRepository.java
├── viewmodel
│   └── JobViewModel.java
├── AddEditJobActivity.java
├── InterviewReminderReceiver.java
└── MainActivity.java


<img width="373" height="667" alt="image" src="https://github.com/user-attachments/assets/46e0e557-1b36-43c3-96ea-e59f96d00c50" />
<img width="376" height="658" alt="image" src="https://github.com/user-attachments/assets/565c36ef-b4f4-4b7b-ab3a-ad966374406b" />
<img width="370" height="664" alt="image" src="https://github.com/user-attachments/assets/193e02a3-752c-4476-854b-9c0b0244f4dc" />
<img width="365" height="661" alt="image" src="https://github.com/user-attachments/assets/79450c1e-f468-431f-9541-b23e51c11a9a" />





