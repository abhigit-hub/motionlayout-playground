package com.footinit.motionlayoutplayground.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.footinit.motionlayoutplayground.R;
import com.footinit.motionlayoutplayground.utils.AppConstants;
import com.footinit.motionlayoutplayground.utils.RoundCornersImageView;
import com.rd.PageIndicatorView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_01 extends AppCompatActivity {

    public int CURRENT_MODE = AppConstants.MODE_TRACKING_NONE;

    public static final String KEY_MESSAGE = "title";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.root)
    MotionLayout motionLayout;

    @BindView(R.id.ivStartTrack)
    RoundCornersImageView ivStartTrack;

    @BindView(R.id.ivStopTrack)
    RoundCornersImageView ivStopTrack;

    @BindView(R.id.ivSwipeLeft)
    ImageView ivSwipeLeft;

    @BindView(R.id.ivSwipeRight)
    ImageView ivSwipeRight;

    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;

    @OnClick(R.id.button_exit)
    void onExitModeClicked() {
        if (CURRENT_MODE == AppConstants.MODE_TRACKING_STARTED) {
            Toast.makeText(getApplicationContext(), "Stop the trip before exiting Trip Mode",
                    Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }

    AnimatedVectorDrawableCompat avdStart, avdStop, avdSwipeLeft, avdSwipeRight;

    private boolean isFirstTransitionStateComplete = false;

    public static Intent getStartIntent(Context context, String message) {
        Intent intent = new Intent(context, Activity_01.class);
        intent.putExtra(KEY_MESSAGE, message);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_01);

        ButterKnife.bind(this);

        setUpToolbar();

        initTrackingState();
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getIntent().getStringExtra(KEY_MESSAGE));
        }
    }

    private void initTrackingState() {
        if (CURRENT_MODE == AppConstants.MODE_TRACKING_STARTED) {
            motionLayout.loadLayoutDescription(R.xml.scene_01_second_stage);
            motionLayout.setTransition(R.id.start, R.id.end);
            motionLayout.transitionToStart();
            isFirstTransitionStateComplete = true;

            updateAnimationState();
        } else {
            updateAnimationState();
        }

        setUpListenersForMotionLayout();
    }

    private void setUpListenersForMotionLayout() {
        motionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {

            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int id) {
                if (id == R.id.end) {

                    if (!isFirstTransitionStateComplete) {
                        /*
                         * First Transition State is complete.
                         * Tracking Mode ON - Tracking starts here
                         *
                         * */

                        motionLayout.loadLayoutDescription(R.xml.scene_01_second_stage);
                        motionLayout.setTransition(R.id.start, R.id.end);
                        motionLayout.transitionToStart();
                        isFirstTransitionStateComplete = true;

                        startTracking();
                    } else {
                        /*
                         * Second Transition State, which is now at END state
                         * Tracking Mode OFF - Tracking ends here
                         * */

                        stopTracking();
                    }
                } else if (id == R.id.start) {
                    /*
                     * Second Transition State, which is now at START state
                     * Tracking Mode ON - Tracking restarts here
                     * */

                    startTracking();
                }
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {

            }
        });
    }

    private void startTracking() {
        if (CURRENT_MODE == AppConstants.MODE_TRACKING_STARTED)
            return;

        if (!isFirstTransitionStateComplete &&
                CURRENT_MODE == AppConstants.MODE_TRACKING_NONE)
            return;

        CURRENT_MODE = AppConstants.MODE_TRACKING_STARTED;
        pageIndicatorView.setSelection(0);

        updateAnimationState();
    }

    private void stopTracking() {
        if (CURRENT_MODE == AppConstants.MODE_TRACKING_STOPPED)
            return;

        CURRENT_MODE = AppConstants.MODE_TRACKING_STOPPED;
        pageIndicatorView.setSelection(1);

        updateAnimationState();
    }

    private void updateAnimationState() {
        if (CURRENT_MODE == AppConstants.MODE_TRACKING_STARTED) {
            stopAnimationStopTracking();
            createAnimationStartTracking();

            stopAnimationSwipeRight();
            createAnimationSwipeLeft();
        } else if (CURRENT_MODE == AppConstants.MODE_TRACKING_STOPPED) {
            stopAnimationStartTracking();
            createAnimationStopTracking();

            stopAnimationSwipeLeft();
            createAnimationSwipeRight();
        } else if (CURRENT_MODE == AppConstants.MODE_TRACKING_NONE) {
            stopAnimationSwipeLeft();
            createAnimationSwipeRight();
        }
    }



    /*
     *
     * Methods to help with starting and stopping of an Animation
     *
     * */

    private void createAnimationStartTracking() {
        if (avdStart == null)
            avdStart = AnimatedVectorDrawableCompat.create(this, R.drawable.avd_start_tracking);

        avdStart.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                ivStartTrack.post(new Runnable() {
                    @Override
                    public void run() {
                        avdStart.start();
                    }
                });
            }
        });
        ivStartTrack.setImageDrawable(avdStart);
        avdStart.start();
    }

    private void stopAnimationStartTracking() {
        if (avdStart != null) {
            avdStart.stop();
            avdStart.clearAnimationCallbacks();
        }
    }

    private void createAnimationStopTracking() {
        if (avdStop == null)
            avdStop = AnimatedVectorDrawableCompat.create(this, R.drawable.avd_stop_tracking);

        avdStop.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                ivStopTrack.post(new Runnable() {
                    @Override
                    public void run() {
                        avdStop.start();
                    }
                });
            }
        });
        ivStopTrack.setImageDrawable(avdStop);
        avdStop.start();
    }

    private void stopAnimationStopTracking() {
        if (avdStop != null) {
            avdStop.stop();
            avdStop.clearAnimationCallbacks();
        }
    }

    private void createAnimationSwipeLeft() {
        if (avdSwipeLeft == null)
            avdSwipeLeft = AnimatedVectorDrawableCompat.create(this, R.drawable.avd_swipe_left);

        avdSwipeLeft.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                ivSwipeLeft.post(new Runnable() {
                    @Override
                    public void run() {
                        avdSwipeLeft.start();
                    }
                });
            }
        });
        ivSwipeLeft.setImageDrawable(avdSwipeLeft);
        avdSwipeLeft.start();
    }

    private void stopAnimationSwipeLeft() {
        if (avdSwipeLeft != null) {
            avdSwipeLeft.stop();
            avdSwipeLeft.clearAnimationCallbacks();
        }
    }

    private void createAnimationSwipeRight() {
        if (avdSwipeRight == null)
            avdSwipeRight = AnimatedVectorDrawableCompat.create(this, R.drawable.avd_swipe_right);

        avdSwipeRight.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                ivSwipeRight.post(new Runnable() {
                    @Override
                    public void run() {
                        avdSwipeRight.start();
                    }
                });
            }
        });
        ivSwipeRight.setImageDrawable(avdSwipeRight);
        avdSwipeRight.start();
    }

    private void stopAnimationSwipeRight() {
        if (avdSwipeRight != null) {
            avdSwipeRight.stop();
            avdSwipeRight.clearAnimationCallbacks();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
