package com.kach.tuts.dagger

import com.kach.tuts.viewmodels.ViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RemoteModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    fun inject(viewModelFactory: ViewModelFactory)
}