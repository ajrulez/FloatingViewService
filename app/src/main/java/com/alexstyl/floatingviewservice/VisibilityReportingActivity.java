package com.alexstyl.floatingviewservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.alexstyl.floatingviewservice.service.BadassOverlayService;

/**
 * An activity that notifies the {@link com.alexstyl.floatingviewservice.service.BadassOverlayService} to show or hide the overlay,
 * while the activity is on the foreground/background
 * <p>Created by alexstyl on 23/03/15.</p>
 */
public class VisibilityReportingActivity extends ActionBarActivity {


    /**
     * Returns whether this activity's visibility will trigger the Overlay to be visible or not
     */
    protected boolean isReportingVisibility() {
        return true;
    }

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = new Intent(BadassOverlayService.Constants.ACTION_CHANGE_VISIBILITY, null, this, BadassOverlayService.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isReportingVisibility() && BadassOverlayService.isRunning()) {
            intent.putExtra(BadassOverlayService.Constants.EXTRA_VISIBILITY, BadassOverlayService.Constants.VISIBILITY_HIDE);
            startService(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isReportingVisibility() && BadassOverlayService.isRunning()) {
            intent.putExtra(BadassOverlayService.Constants.EXTRA_VISIBILITY, BadassOverlayService.Constants.VISIBILITY_SHOW);
            startService(intent);
        }
    }
}
