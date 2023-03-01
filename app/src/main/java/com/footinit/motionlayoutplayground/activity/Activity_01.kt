package com.footinit.motionlayoutplayground.activity

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.footinit.motionlayoutplayground.R
import com.footinit.motionlayoutplayground.databinding.Activity01Binding
import com.footinit.motionlayoutplayground.extensions.showToast
import com.footinit.motionlayoutplayground.utils.AppConstants

class Activity_01 : AppCompatActivity() {

    private lateinit var binding: Activity01Binding

    companion object {
        val TAG = Activity_01::class.java.simpleName

        val KEY_MESSAGE = "title"

        fun getStartIntent(context: Context, message: String): Intent {
            val intent = Intent(context, Activity_01::class.java)
            intent.putExtra(KEY_MESSAGE, message)
            return intent
        }
    }

    var CURRENT_MODE = AppConstants.MODE_TRACKING_NONE
    var isFirstTransitionStateComplete = false

    var avdStart: AnimatedVectorDrawableCompat? = null
    var avdStop: AnimatedVectorDrawableCompat? = null
    var avdSwipeLeft: AnimatedVectorDrawableCompat? = null
    var avdSwipeRight: AnimatedVectorDrawableCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Activity01Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setUpToolbar()

        initTrackingState()

        initClickListeners()
    }

    private fun initClickListeners() {
        binding.buttonExit.setOnClickListener {
            if (CURRENT_MODE == AppConstants.MODE_TRACKING_STARTED) {
                applicationContext.showToast("Stop the trip before exiting Trip Mode")
            } else {
                finish()
            }
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setHomeButtonEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.setTitle(intent.getStringExtra(KEY_MESSAGE))
        }
    }

    private fun initTrackingState() {
        if (CURRENT_MODE == AppConstants.MODE_TRACKING_STARTED) {
            binding.root.loadLayoutDescription(R.xml.scene_01_second_stage)
            binding.root.setTransition(R.id.start, R.id.end)
            binding.root.transitionToStart()
            isFirstTransitionStateComplete = true

            updateAnimationState()
        } else {
            updateAnimationState()
        }

        setUpListenersForMotionLayout()
    }

    private fun setUpListenersForMotionLayout() {

        binding.root.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}

            override fun onTransitionCompleted(motionLayout: MotionLayout, id: Int) {
                if (id == R.id.end) {

                    if (!isFirstTransitionStateComplete) {
                        /*
                         * First Transition State is complete.
                         * Tracking Mode ON - Tracking starts here
                         *
                         * */

                        motionLayout.loadLayoutDescription(R.xml.scene_01_second_stage)
                        motionLayout.setTransition(R.id.start, R.id.end)
                        motionLayout.transitionToStart()
                        isFirstTransitionStateComplete = true

                        startTracking()
                    } else {
                        /*
                         * Second Transition State, which is now at END state
                         * Tracking Mode OFF - Tracking ends here
                         * */

                        stopTracking()
                    }
                } else if (id == R.id.start) {
                    /*
                     * Second Transition State, which is now at START state
                     * Tracking Mode ON - Tracking restarts here
                     * */

                    startTracking()
                }
            }
        })
    }

    private fun startTracking() {
        if (CURRENT_MODE == AppConstants.MODE_TRACKING_STARTED)
            return

        if (!isFirstTransitionStateComplete && CURRENT_MODE == AppConstants.MODE_TRACKING_NONE)
            return

        CURRENT_MODE = AppConstants.MODE_TRACKING_STARTED
        //binding.pageIndicatorView.setSelected(0)

        updateAnimationState()
    }

    private fun stopTracking() {
        if (CURRENT_MODE == AppConstants.MODE_TRACKING_STOPPED)
            return

        CURRENT_MODE = AppConstants.MODE_TRACKING_STOPPED
        //pageIndicatorView.setSelected(1)

        updateAnimationState()
    }

    private fun updateAnimationState() {
        when (CURRENT_MODE) {
            AppConstants.MODE_TRACKING_STARTED -> {
                stopAnimationStopTracking()
                createAnimationStartTracking()

                stopAnimationSwipeRight()
                createAnimationSwipeLeft()
            }
            AppConstants.MODE_TRACKING_STOPPED -> {
                stopAnimationStartTracking()
                createAnimationStopTracking()

                stopAnimationSwipeLeft()
                createAnimationSwipeRight()
            }
            AppConstants.MODE_TRACKING_NONE -> {
                stopAnimationSwipeLeft()
                createAnimationSwipeRight()
            }
        }
    }


    /*
     *
     * All the Methods that follow helps with starting and stopping of an Animation
     * Utility methods to show Animation start and end state
     *
     * */

    private fun createAnimationStartTracking() {
        if (avdStart == null)
            avdStart = AnimatedVectorDrawableCompat.create(this@Activity_01,
                    R.drawable.avd_start_tracking)

        avdStart?.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                binding.ivStartTrack.post {
                    avdStart?.start()
                }
            }
        })
        binding.ivStartTrack.setImageDrawable(avdStart)
        avdStart?.start()
    }

    private fun stopAnimationStartTracking() {
        avdStart?.let {
            it.stop()
            it.clearAnimationCallbacks()
        }
    }

    private fun createAnimationStopTracking() {
        if (avdStop == null)
            avdStop = AnimatedVectorDrawableCompat.create(this@Activity_01, R.drawable.avd_stop_tracking)

        avdStop?.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                binding.ivStopTrack.post {
                    avdStop?.start()
                }
            }
        })
        binding.ivStopTrack.setImageDrawable(avdStop)
        avdStop?.start()
    }

    private fun stopAnimationStopTracking() {
        avdStop?.let {
            it.stop()
            it.clearAnimationCallbacks()
        }
    }

    private fun createAnimationSwipeLeft() {
        if (avdSwipeLeft == null)
            avdSwipeLeft = AnimatedVectorDrawableCompat.create(this@Activity_01, R.drawable.avd_swipe_left)

        avdSwipeLeft?.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                binding.ivSwipeLeft.post {
                    avdSwipeLeft?.start()
                }
            }
        })
        binding.ivSwipeLeft.setImageDrawable(avdSwipeLeft)
        avdSwipeLeft?.start()
    }

    private fun stopAnimationSwipeLeft() {
        avdSwipeLeft?.let {
            it.stop()
            it.clearAnimationCallbacks()
        }
    }

    private fun createAnimationSwipeRight() {
        if (avdSwipeRight == null)
            avdSwipeRight = AnimatedVectorDrawableCompat.create(this@Activity_01, R.drawable.avd_swipe_right)

        avdSwipeRight?.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                binding.ivSwipeRight.post {
                    avdSwipeRight?.start()
                }
            }
        })
        binding.ivSwipeRight.setImageDrawable(avdSwipeRight)
        avdSwipeRight?.start()
    }

    private fun stopAnimationSwipeRight() {
        avdSwipeRight?.let {
            it.stop()
            it.clearAnimationCallbacks()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}