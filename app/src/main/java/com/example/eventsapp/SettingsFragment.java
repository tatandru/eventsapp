package com.example.eventsapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.eventsapp.database.EventsViewModel;
import com.example.eventsapp.retrofitAPI.Image;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import static androidx.core.content.ContextCompat.getSystemService;

public class SettingsFragment extends Fragment {
    private ImageView imageView;
    private static final String CHANNEL_ID = "1";
    private int notificationID=1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings, container, false);
        imageView = view.findViewById(R.id.img_x_logout);
        imageView.setOnClickListener(v -> getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomepageFragment()).commit());
        return view;
    }

    private int getRemainingDays() {
        int daysDifference = 0;
        DateTime startDate;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime currentTime = formatter.parseDateTime(String.valueOf(LocalDate.now()));
        EventsViewModel viewModel = new EventsViewModel(this.getActivity().getApplication());
        try {
            for (int i = 0; i < viewModel.getAllEvents().getValue().size(); i++) {
                startDate = formatter.parseDateTime(viewModel.getAllEvents().getValue().get(i).getStartDate());
                daysDifference = Days.daysBetween(currentTime, startDate).getDays();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return daysDifference;
    }

    private void notificationBuilder() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.heart_icon)
                .setContentTitle("Reminder")
                .setContentText("You have an event in 2 days")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this.getContext());
        notificationManager.notify(notificationID, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(this.getContext(), NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification() {
        int days = getRemainingDays();
        if (days == 2) {
            notificationBuilder();
            createNotificationChannel();
        }
    }
}