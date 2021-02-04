package com.kach.easylearning.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kach.easylearning.model.EasyLearningCollectionTemp
import com.kach.easylearning.model.EasyLearningQuestion
import com.kach.easylearning.model.Repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val collectionList: LiveData<List<EasyLearningCollectionTemp>> get() = tempCollectionListData
    val questionList: LiveData<List<EasyLearningQuestion>> get() = repository.questionList
    val selectedCollection: LiveData<EasyLearningCollectionTemp?> get() = selectedCollectionData
    val messages = repository.messages

    private val tempCollectionListData = repository.tempCollectionList
    private val selectedCollectionData = MutableLiveData<EasyLearningCollectionTemp>()

    private var loadingJob: Job? = null
    private var previousJob: Job? = null

    init {
        runJob { repository.loadAll() }
    }

    private fun requestQuestions(collection: EasyLearningCollectionTemp) {
        runJob { repository.requestQuestions(collection) }
    }

    fun setSelectedCollection(collection: EasyLearningCollectionTemp?) {
        selectedCollectionData.value = collection
        collection?.let { requestQuestions(it) }
    }

    private fun runJob(block: suspend (() -> Unit)) {
        loadingJob?.let { previousJob = loadingJob }
        loadingJob = viewModelScope.launch {
            previousJob?.cancelAndJoin()
            block()
            loadingJob = null
        }
    }
}