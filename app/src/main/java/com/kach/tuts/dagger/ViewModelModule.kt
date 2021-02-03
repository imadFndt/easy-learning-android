package com.kach.tuts.dagger

import androidx.lifecycle.ViewModel
import com.kach.tuts.viewmodels.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun main(v: MainActivityViewModel): ViewModel
}