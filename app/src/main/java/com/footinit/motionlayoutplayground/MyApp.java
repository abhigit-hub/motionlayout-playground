package com.footinit.motionlayoutplayground;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig.Builder;

public class MyApp extends Application {

    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(
                new Builder()
                        .setDefaultFontPath("fonts/Google-Sans-Font/GoogleSans-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath).build());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
