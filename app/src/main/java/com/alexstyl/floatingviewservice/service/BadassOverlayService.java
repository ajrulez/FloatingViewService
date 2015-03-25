package com.alexstyl.floatingviewservice.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.alexstyl.floatingviewservice.ControllerActivity;
import com.alexstyl.floatingviewservice.R;
import com.alexstyl.floatingviewservicelib.AbstractOverlayService;

/**
 * A simple service that extends the {@linkplain com.alexstyl.floatingviewservicelib.AbstractOverlayService}.
 * <p>Created by alexstyl on 23/03/15.</p>
 */
public class BadassOverlayService extends AbstractOverlayService {


    public interface Constants {

        String PACKAGE = "com.alexstyl.fvsdemo";
        /**
         * </br>
         * [Value: Integer]
         */
        public String ACTION_CHANGE_VISIBILITY = PACKAGE + ".ACTION_CHANGE_VISIBILITY";
        public int VISIBILITY_SHOW = View.VISIBLE;
        public int VISIBILITY_HIDE = View.INVISIBLE;
        public String EXTRA_VISIBILITY = PACKAGE + ".visibility";

    }

    private static final String TAG = "BadAssService";

    private static boolean isRunning = false;
    public static boolean isVisible = true;

    public static boolean isRunning() {
        return isRunning;
    }

    @Override
    protected View onCreateOverlayingView() {
        // here we are inflating the view that is going to act as an overlay.
        // pass any click listeners here.
        View view = LayoutInflater.from(this).inflate(R.layout.overlay_test, null, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(BadassOverlayService.this, ControllerActivity.class);
                main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(main);
            }
        });
        return view;
    }

    @Override
    protected Notification getServiceNotification() {
        Intent intent = new Intent(this, ControllerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_action_picture_in_picture)
                .setContentTitle(getString(R.string.stat_title))
                .setContentText(getString(R.string.stat_text))
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                        // we are creating a notification with priority set to MIN,
                        // so that it won't appear to the user's notification status bar
                        // unless they pull it down
                .setContentIntent(pendingIntent)
                .build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isRunning = true;

        if (intent.getAction() != null) {
            handleExtras(intent.getAction(), intent.getExtras());
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void handleExtras(String action, Bundle extras) {
        if (Constants.ACTION_CHANGE_VISIBILITY.equals(action)) {
            int reqVisiblity = extras.getInt(Constants.EXTRA_VISIBILITY);
            if (reqVisiblity == Constants.VISIBILITY_SHOW ||
                    reqVisiblity == Constants.VISIBILITY_HIDE) {
                setOverlayVisibility(extras.getInt(Constants.EXTRA_VISIBILITY));
            } else {
                Log.w(TAG, "Unknown visibility " + reqVisiblity);
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }
}
