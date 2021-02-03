package com.kach.tuts.dagger

import com.kach.tuts.remote.TutsServiceProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteModule {
    @Provides
    @Singleton
    fun remote() = TutsServiceProvider.tutsService
}