package com.example.eventsapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.example.eventsapp.database.EventsViewModel;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

public class MyJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        addNotification();
        scheduleJob(getApplicationContext());
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
    private void scheduleAlarms(){
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, 0, pendingIntent);
    }

    public void addNotification() {


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
                DateTime currentTime = formatter.parseDateTime(String.valueOf(LocalDate.now()));
                EventsViewModel viewModel = new EventsViewModel(getApplication());
                List<DateTime> startDateList = new ArrayList<>();
                List<String> startDates = new ArrayList<>(viewModel.getAllStartDates());

                for (int i = 0; i < startDates.size(); i++) {

                    startDateList.add(formatter.parseDateTime(startDates.get(i)));
                    if (Days.daysBetween(currentTime, startDateList.get(i)).getDays() == 2) {
                        scheduleAlarms();
                        break;
                    }
                }
            }
        });
        thread.start();

    }
    public static void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, MyJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(3600*1000);
        builder.setOverrideDeadline(24*3600*1000);
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
    }

}
