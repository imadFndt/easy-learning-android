package com.kach.easylearning.viewmodels

import androidx.lifecycle.*
import com.kach.easylearning.data.model.EasyLearningCollection
import com.kach.easylearning.data.model.EasyLearningQuestion
import com.kach.easylearning.data.repository.BaseRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val repository: BaseRepository) : ViewModel() {
    val collectionList: LiveData<List<EasyLearningCollection>>
        get() = collectionListData.switchMap {
            MutableLiveData(it)
        }
    val questionList: LiveData<List<EasyLearningQuestion>> get() = questionListData
    val selectedCollection: LiveData<EasyLearningCollection?> get() = selectedCollectionData
    val timer: LiveData<Int?> get() = timerData

    private val collectionListData = MutableLiveData<MutableList<EasyLearningCollection>>()
    private val questionListData = MutableLiveData<List<EasyLearningQuestion>>()
    private val selectedCollectionData = MutableLiveData<EasyLearningCollection>()

    private val timerData = MutableLiveData<Int?>()

    private var loadingJob: Job? = null
    private var previousJob: Job? = null

    private var timerJob: Job? = null

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

    private fun runJob(block: suspend (() -> Unit)) {
        loadingJob?.let { previousJob = loadingJob }
        loadingJob = viewModelScope.launch {
            previousJob?.cancelAndJoin()
            block()
            loadingJob = null
        }
    }

    fun shuffleQuestions() {
        val list: MutableList<EasyLearningQuestion>? = questionListData.value?.toMutableList()
        val secondList = list?.toMutableList()?.apply { shuffle() }
        secondList?.let { list.addAll(it) }
        questionListData.value = list
    }

    fun runTimer() {
        timerJob = viewModelScope.launch(Dispatchers.Main) {
            timerData.value = 0
            var current: Int
            while (isActive) {
                current = timerData.value ?: 0
                delay(1000)
                timerData.value = current + 1
            }
        }
    }
}