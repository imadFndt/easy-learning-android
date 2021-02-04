package com.kach.easylearning.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kach.easylearning.EasyLearningApplication
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    @Inject
    internal lateinit var map: MutableMap<Class<out ViewModel>, Provider<ViewModel>>

    init {
        EasyLearningApplication.component.inject(this)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        map[modelClass]?.get() as? T ?: error("ViewModel ${modelClass.simpleName} did not found!")
}