package com.kach.easylearning.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    private var loadingJob: Job? = null
    private var previousJob: Job? = null

    protected fun runJob(block: suspend (() -> Unit)) {
        loadingJob?.let { previousJob = loadingJob }
        loadingJob = viewModelScope.launch {
            previousJob?.cancelAndJoin()
            block()
            loadingJob = null
        }
    }
}