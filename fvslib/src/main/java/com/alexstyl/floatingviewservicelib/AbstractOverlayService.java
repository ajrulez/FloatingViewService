package com.alexstyl.floatingviewservicelib;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.alexstyl.floatingviewservicelib.util.DragMeAroundTouchListener;


/**
 * Service that runs in foreground and creates a window that overlays other applications
 * <p>Created by alexstyl on 07/03/15.</p>
 */
abstract public class AbstractOverlayService extends Service {


    /**
     * Called when the overlaying view needs to be created.
     * <p>You can later on get the instance of the service by calling {@linkplain #getOverlayView()}</p>
     */
    protected abstract View onCreateOverlayingView();

    private View mView;
    private int mOverlayVisibility = View.VISIBLE;
    private WindowManager windowManager;
    private int NOTIFICATION_FOREGROUND_ID = 999242;


    /**
     * Sets the visibility of the service's overlay view
     */
    protected void setOverlayVisibility(int visibility) {
//        int visibility = (show ? View.VISIBLE : View.GONE);
        if (mView != null) {
            mView.setVisibility(visibility);
        }

        mOverlayVisibility = visibility;

    }


    @Override
    public void onCreate() {
        super.onCreate();

        mView = onCreateOverlayingView();
        mView.setOnTouchListener(new DragMeAroundTouchListener(this) {

            @Override
            protected void onStartMoving() {
                onOverlayStartedMoving();
            }

            @Override
            protected void onStopMoving() {
                onOverlayStoppedMoving();
            }
        });

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT);

        params.windowAnimations = getWindowAnimationStyle();

        params.gravity = getWindowGravity();
        mView.setVisibility(mOverlayVisibility);
        windowManager.addView(mView, params);
        moveToForeground();

    }

    protected void onOverlayStoppedMoving() {

    }

    /**
     * Called when the overlay has started moving
     */
    protected void onOverlayStartedMoving() {

    }

    /**
     * Returns a resource ID of the window animations. The default style will cause the floating view to
     * fade in (on enter) and fade out (on exit)
     *
     * @return
     */
    protected int getWindowAnimationStyle() {
        return R.style.Overlay;
    }

    protected void moveToForeground() {
        startForeground(NOTIFICATION_FOREGROUND_ID, getServiceNotification());
    }

    /**
     * Returns the notification to be displayed while the service runs on foreground.
     * <p>Tip: You can set the notification's priority to {@linkplain android.support.v4.app.NotificationCompat#PRIORITY_LOW} so that it doesn't
     * appear on the user's notifiation tray (unless expanded)</p>
     *
     * @see android.app.Service#startForeground(int, android.app.Notification)
     */
    protected abstract Notification getServiceNotification();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mView != null) windowManager.removeView(mView);
    }


    protected int getWindowGravity() {
        return Gravity.CLIP_HORIZONTAL | Gravity.TOP;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Returns the Overlay view of the service
     */
    protected View getOverlayView() {
        return mView;
    }


}
