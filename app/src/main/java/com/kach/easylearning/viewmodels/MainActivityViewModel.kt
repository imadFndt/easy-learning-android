package com.kach.easylearning.viewmodels

import androidx.lifecycle.*
import com.kach.easylearning.data.model.EasyLearningCollection
import com.kach.easylearning.data.model.EasyLearningQuestion
import com.kach.easylearning.data.repository.BaseRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val repository: BaseRepository) : ViewModel() {
    val collectionList: LiveData<List<EasyLearningCollection>>
        get() = collectionListData.switchMap {
            MutableLiveData(it)
        }
    val questionList: LiveData<List<EasyLearningQuestion>> get() = questionListData
    val selectedCollection: LiveData<EasyLearningCollection?> get() = selectedCollectionData

    private val collectionListData = MutableLiveData<MutableList<EasyLearningCollection>>()
    private val questionListData = MutableLiveData<List<EasyLearningQuestion>>()
    private val selectedCollectionData = MutableLiveData<EasyLearningCollection>()


    private var loadingJob: Job? = null
    private var previousJob: Job? = null

    init {
        runJob {
            repository.getCollections().buffer().collect { collectionListData.value = it.toMutableList() }
        }
    }

    fun setSelectedCollection(collection: EasyLearningCollection?) {
        selectedCollectionData.value = collection
        collection?.let { requestQuestions(it) }
    }

    private fun requestQuestions(collection: EasyLearningCollection) {
        runJob {
            repository.requestQuestions(collection).buffer().collect {
                questionListData.value = it.toMutableList()
            }
        }
    }

    private fun onNext(collection: EasyLearningCollection) {
        val items = collectionListData.value ?: mutableListOf()
        items.add(collection)
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