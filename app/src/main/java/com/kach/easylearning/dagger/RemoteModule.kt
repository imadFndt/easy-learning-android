package com.kach.easylearning.dagger

import com.kach.easylearning.data.remote.EasyLearningService
import com.kach.easylearning.data.remote.EasyLearningServiceProvider
import com.kach.easylearning.data.repository.BaseRepository
import com.kach.easylearning.data.repository.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteModule {
    @Provides
    @Singleton
    fun remote() = EasyLearningServiceProvider.easyLearningService

    @Provides
    @Singleton
    fun repository(easyLearningService: EasyLearningService): BaseRepository = Repository(easyLearningService)
}