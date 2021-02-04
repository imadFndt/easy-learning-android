package com.kach.easylearning.dagger

import com.kach.easylearning.viewmodels.ViewModelFactory
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