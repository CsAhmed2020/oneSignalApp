package com.example.testnotificationapp;

import android.Manifest;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        reqPer();

        // Enable verbose OneSignal logging to debug issues if needed
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        OneSignal.initWithContext(this);
        OneSignal.setAppId("82eda602-5a03-4633-ada3-0e25c820ca45");

        OneSignal.setNotificationOpenedHandler(result -> {

            NotificationResult notificationResult = new NotificationResult();
            notificationResult.setSmallIcon(result.getNotification().getSmallIcon());
            notificationResult.setLargeIcon(result.getNotification().getLargeIcon());
            notificationResult.setTitle(result.getNotification().getTitle());
            notificationResult.setBody(result.getNotification().getBody());
            notificationResult.setBigPicture(result.getNotification().getBigPicture());
            notificationResult.setLaunchURL(result.getNotification().getLaunchURL());
            notificationResult.setJsonObject(result.toJSONObject());
            notificationResult.setAdditionalData(result.getNotification().getAdditionalData());
            Log.d("TAG", "handleOneSignalNotification1: " + notificationResult.toString());
            showOneSignalNotificationDialog(notificationResult);
        });
    }

    private void showOneSignalNotificationDialog(NotificationResult result) {
        Dialog confirmDialog = new Dialog(this);
        confirmDialog.setContentView(R.layout.dialog_show_notification);
        ImageView notificationIcon = confirmDialog.findViewById(R.id.icon_view);
        ImageView notificationImage = confirmDialog.findViewById(R.id.notif_img);
        TextView notificationTitle = confirmDialog.findViewById(R.id.notif_title);
        TextView notificationMessage = confirmDialog.findViewById(R.id.notif_message);
        Button doneBtn = confirmDialog.findViewById(R.id.btn_ok);


        if (result.getLargeIcon() != null) {
            Glide.with(this).load(result.getLargeIcon()).into(notificationIcon).waitForLayout();
            notificationIcon.setVisibility(View.VISIBLE);
        } else if (result.getTitle() != null) {
            notificationTitle.setText(result.getTitle());
            notificationTitle.setVisibility(View.VISIBLE);
        } else if (result.getBody() != null) {
            notificationMessage.setText(result.getBody());
            notificationMessage.setVisibility(View.VISIBLE);

        } else if (result.getBigPicture() != null) {
            Glide.with(this).load(result.getBigPicture()).into(notificationImage).waitForLayout();
            notificationImage.setVisibility(View.VISIBLE);
        }

        doneBtn.setOnClickListener(view -> confirmDialog.dismiss());

        confirmDialog.show();

    }

    private void reqPer() {
        createNotificationChannel(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
            }
        }
    }

    private void createNotificationChannel(Context context) {
        final String CHANNEL_ID = "7b709531-b89a-4d9d-905c-f894903e6715";
        final String CHANNEL_NAME = "Channel Name";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Uri soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.notification_sound);

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            channel.setSound(soundUri, audioAttributes);
            channel.setDescription("channel description");

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}