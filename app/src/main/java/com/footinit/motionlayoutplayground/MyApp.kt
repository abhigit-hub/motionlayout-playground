package com.footinit.motionlayoutplayground

import android.app.Application
import com.footinit.motionlayoutplayground.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApp)
            androidLogger()
            modules(appModules)
        }
    }
}