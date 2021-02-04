package com.kach.easylearning.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kach.easylearning.remote.EasyLearningService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val easyLearningService: EasyLearningService) {
    val questionList: LiveData<List<EasyLearningQuestion>> get() = questionListData
    val collectionList: LiveData<List<EasyLearningCollection>> get() = collectionListData
    val tempCollectionList: LiveData<List<EasyLearningCollectionTemp>> get() = tempCollectionListData
    val messages: LiveData<Boolean> get() = messagesData

    private val questionListData = MutableLiveData<List<EasyLearningQuestion>>()
    private val collectionListData = MutableLiveData<List<EasyLearningCollection>>()
    private val tempCollectionListData = MutableLiveData<List<EasyLearningCollectionTemp>>()
    private val questionsMap = mutableMapOf<EasyLearningCollectionTemp, List<EasyLearningQuestion>>()
    private val messagesData = MutableLiveData<Boolean>()


    suspend fun requestQuestions(collection: EasyLearningCollectionTemp) = withContext(Dispatchers.IO) {
        questionListData.postValue(questionsMap[collection])
    }

    suspend fun loadAll() = withContext(Dispatchers.IO) {
        try {
            val collectionListTemp = mutableListOf<EasyLearningCollectionTemp>()
            val collections = easyLearningService.getCollections()
            collectionListTemp.addAll(collections.map {
                val questions = easyLearningService.getQuestions(it.id)
                val collection = EasyLearningCollectionTemp(
                    id = it.id,
                    authorId = it.authorId,
                    created = it.created,
                    description = it.description,
                    title = it.title,
                    totalItems = questions.size
                )
                questionsMap[collection] = questions
                collection
            })
            tempCollectionListData.postValue(collectionListTemp)
            messagesData.postValue(true)
        } catch (e: Exception) {
            messagesData.postValue(false)
        }
    }
}