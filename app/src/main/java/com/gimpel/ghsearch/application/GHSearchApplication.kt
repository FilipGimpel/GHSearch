package com.gimpel.ghsearch.application

import android.app.Application
import com.gimpel.ghsearch.koin.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GHSearchApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GHSearchApplication)
            modules(listOf(appModule))
        }
    }
}