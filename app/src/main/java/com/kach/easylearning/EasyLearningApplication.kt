package com.kach.easylearning

import android.app.Application
import com.kach.easylearning.dagger.AppComponent
import com.kach.easylearning.dagger.DaggerAppComponent

class EasyLearningApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().build()
    }

    companion object {
        lateinit var component: AppComponent
    }
}