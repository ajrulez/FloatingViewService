package com.alexstyl.floatingviewservice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * <p>Created by alexstyl on 23/03/15.</p>
 */
public class ControllerActivity extends ViewReportingActivity {
    private Button toggleBtn;
    private Button visiblityBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_controller);
        visiblityBtn = (Button) findViewById(R.id.btn_visibility);

        visiblityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ControllerActivity.this, BadassOverlayService.class);
                i.setAction(BadassOverlayService.Constants.ACTION_CHANGE_VISIBILITY);
                int visibility;
                if (BadassOverlayService.isVisible) {
                    visiblityBtn.setText(R.string.show);
                    visibility = BadassOverlayService.Constants.VISIBILITY_HIDE;
                } else {
                    visiblityBtn.setText(R.string.hide);
                    visibility = BadassOverlayService.Constants.VISIBILITY_SHOW;
                }
                i.putExtra(BadassOverlayService.Constants.EXTRA_VISIBILITY, visibility);
                startService(i);
            }
        });

        toggleBtn = (Button) findViewById(R.id.btn_control);
        toggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ControllerActivity.this, BadassOverlayService.class);
                if (!BadassOverlayService.isRunning()) {
                    toggleBtn.setText(R.string.stop);
                    startService(i);
                    visiblityBtn.setEnabled(true);
                } else {
                    toggleBtn.setText(R.string.start);
                    stopService(i);
                    visiblityBtn.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean running = BadassOverlayService.isRunning();
        visiblityBtn.setEnabled(running);
        toggleBtn.setText(running ? R.string.stop : R.string.start);
        visiblityBtn.setText(BadassOverlayService.isVisible ? R.string.hide : R.string.show);

    }
}
