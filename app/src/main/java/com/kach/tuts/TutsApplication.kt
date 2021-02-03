package com.kach.tuts

import android.app.Application
import com.kach.tuts.dagger.AppComponent
import com.kach.tuts.dagger.DaggerAppComponent

class TutsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().build()
    }

    companion object {
        lateinit var component: AppComponent
    }
}