package com.kach.easylearning.data.model

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class TimerLiveData(private val scope: CoroutineScope) : MutableLiveData<Int>() {
    private var timerJob: Job? = null

    fun runTimer() {
        timerJob?.let { return }
        timerJob = scope.launch(Dispatchers.Main) {
            value = 0
            var current: Int
            while (isActive) {
                current = value ?: 0
                delay(1000)
                value = current + 1
            }
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
        this.value = null
    }
}