package com.kach.easylearning.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.kach.easylearning.R
import com.kach.easylearning.data.model.EasyLearningCollection
import com.kach.easylearning.data.model.EasyLearningQuestion
import com.kach.easylearning.data.model.TimerLiveData
import com.kach.easylearning.data.repository.Repository
import com.kach.easylearning.view.util.MainNavState
import com.kach.easylearning.view.util.NavState
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {
    val navState: LiveData<NavState> get() = navStateData

    val collectionList: LiveData<List<EasyLearningCollection>>
        get() = collectionListData.switchMap { MutableLiveData(it) }
    val questionList: LiveData<List<EasyLearningQuestion>> get() = questionListData
    val selectedCollection: LiveData<EasyLearningCollection?> get() = selectedCollectionData
    val shuffledQuestionList: LiveData<List<EasyLearningQuestion>> get() = shuffledQuestionListData

    val timerTime: LiveData<Int> get() = timer

    private val mainNavState = MainNavState()
    private val navStateData = MutableLiveData<NavState>(mainNavState)

    private val collectionListData = MutableLiveData<MutableList<EasyLearningCollection>>()
    private val questionListData = MutableLiveData<List<EasyLearningQuestion>>()
    private val shuffledQuestionListData = MutableLiveData<List<EasyLearningQuestion>>()
    private val selectedCollectionData = MutableLiveData<EasyLearningCollection>()

    private val timer = TimerLiveData(viewModelScope)

    init {
        runJob {
            repository.getCollections().buffer().collect { collectionListData.value = it.toMutableList() }
        }
        mainNavState.setUpdateCallback { navStateData.value = mainNavState }
    }

    fun setSelectedCollection(collection: EasyLearningCollection?) {
        selectedCollectionData.value = collection
        collection?.let {
            requestQuestions(it)
            mainNavState.navigateStateIn()
        } ?: run {
            mainNavState.navigateStateUp()
        }
    }

    fun setTestGoing(isGoing: Boolean) {
        if (isGoing) {
            mainNavState.navigateStateIn()
            timer.runTimer()
            shuffledQuestionListData.value = questionList.value
        } else {
            timer.stopTimer()
            mainNavState.navigateStateUp()
        }
    }

    fun shuffleQuestions() {
        val list: MutableList<EasyLearningQuestion>? = shuffledQuestionList.value?.toMutableList()
        val secondList = list?.toMutableList()?.apply { shuffle() }
        secondList?.let { list.addAll(it) }
        shuffledQuestionListData.value = list
    }

    fun setNavState(graphId: Int) {
        navStateData.value = when (graphId) {
            R.id.main_nav_graph -> mainNavState
            R.id.profile_nav_graph -> null
            R.id.favorites_nav_graph -> null
            else -> null
        }
    }

    fun onBackPressed(): Boolean {
        when {
            mainNavState.isTestGoing -> {
                timer.stopTimer()
                setTestGoing(false)
            }
            mainNavState.isItemSelected -> {
                setSelectedCollection(null)
            }
        }
        return true
    }

    private fun requestQuestions(collection: EasyLearningCollection) {
        runJob {
            repository.getQuestions(collection).buffer().collect {
                questionListData.value = it.toMutableList()
                shuffledQuestionListData.value = it.toMutableList()
            }
        }
    }

    override fun onCleared() {
        mainNavState.setUpdateCallback(null)
        super.onCleared()
    }
}