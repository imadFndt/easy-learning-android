package com.kach.easylearning.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    val isLoadingCollections: LiveData<Boolean> get() = isLoadingCollectionsData
    val selectedCollection: LiveData<EasyLearningCollection?> get() = selectedCollectionData
    val showQuestionsLoadError: LiveData<Boolean> get() = showQuestionsLoadErrorData
    val showCollectionsLoadError: LiveData<Boolean> get() = showCollectionsLoadErrorData
    var isQuestionLoadErrorToastShown = false
    var isCollectionLoadErrorToastShown = false
    var testPosition = 0

    val collectionList: LiveData<List<EasyLearningCollection>> get() = collectionListData

    val questionList: LiveData<List<EasyLearningQuestion>> get() = questionListData
    val shuffledQuestionList: LiveData<List<EasyLearningQuestion>> get() = shuffledQuestionListData

    val timerTime: LiveData<Int> get() = timer


    private val mainNavState = MainNavState()
    private val navStateData = MutableLiveData<NavState>(mainNavState)

    private val isLoadingCollectionsData = MutableLiveData<Boolean>()
    private val selectedCollectionData = MutableLiveData<EasyLearningCollection>()
    private val showQuestionsLoadErrorData = MutableLiveData<Boolean>()
    private val showCollectionsLoadErrorData = MutableLiveData<Boolean>()

    private val collectionListData = MutableLiveData<List<EasyLearningCollection>>()

    private val questionListData = MutableLiveData<List<EasyLearningQuestion>>()
    private val shuffledQuestionListData = MutableLiveData<List<EasyLearningQuestion>>()

    private val timer = TimerLiveData(viewModelScope)

    init {
        loadCollections()
        mainNavState.setUpdateCallback { navStateData.value = mainNavState }
    }

    fun loadCollections() {
        runJob {
            isLoadingCollectionsData.postValue(true)
            isCollectionLoadErrorToastShown = false
            repository.getCollections().buffer().collect { result ->
                when {
                    result.isFailure -> {
                        showCollectionsLoadErrorData.postValue(true)
                    }
                    result.isSuccess -> {
                        collectionListData.postValue(result.getOrNull())
                        showCollectionsLoadErrorData.postValue(false)
                    }
                }
            }
            isLoadingCollectionsData.postValue(false)
        }
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

    fun requestTestStart() {
        setTestGoing(true)
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
        return when {
            mainNavState.isTestGoing -> {
                timer.stopTimer()
                setTestGoing(false)
                true
            }
            mainNavState.isItemSelected -> {
                setSelectedCollection(null)
                true
            }
            else -> false
        }
    }

    private fun setTestGoing(isGoing: Boolean) {
        if (isGoing) {
            testPosition = 0
            mainNavState.navigateStateIn()
            timer.runTimer()
            shuffledQuestionListData.value = questionList.value
        } else {
            timer.stopTimer()
            mainNavState.navigateStateUp()
            shuffledQuestionListData.value = null
        }
    }

    private fun requestQuestions(collection: EasyLearningCollection) {
        runJob {
            repository.getQuestions(collection).buffer().collect { result ->
                questionListData.postValue(result.getOrNull())
                isQuestionLoadErrorToastShown = false
                when {
                    result.isFailure -> showQuestionsLoadErrorData.postValue(true)
                    result.isSuccess -> showQuestionsLoadErrorData.postValue(false)
                }
            }
        }
    }

    override fun onCleared() {
        mainNavState.setUpdateCallback(null)
        super.onCleared()
    }
}