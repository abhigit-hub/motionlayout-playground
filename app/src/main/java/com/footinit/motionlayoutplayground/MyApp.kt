package com.footinit.motionlayoutplayground

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        CalligraphyConfig.initDefault(
                CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Google-Sans-Font/GoogleSans-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath).build())
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}