package com.kach.easylearning.dagger

import androidx.lifecycle.ViewModel
import com.kach.easylearning.viewmodels.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun main(v: MainViewModel): ViewModel
}