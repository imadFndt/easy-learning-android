package com.kach.easylearning.dagger

import com.kach.easylearning.remote.EasyLearningServiceProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteModule {
    @Provides
    @Singleton
    fun remote() = EasyLearningServiceProvider.easyLearningService
}