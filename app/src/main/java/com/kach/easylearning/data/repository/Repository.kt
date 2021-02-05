package com.kach.easylearning.data.repository

import com.kach.easylearning.data.model.EasyLearningCollection
import com.kach.easylearning.data.model.EasyLearningQuestion
import com.kach.easylearning.data.remote.EasyLearningService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class Repository @Inject constructor(private val easyLearningService: EasyLearningService) : BaseRepository {

    private val questionsMap = mutableMapOf<String, List<EasyLearningQuestion>>()

    override suspend fun requestQuestions(collection: EasyLearningCollection) = flow {
        val questions = questionsMap[collection.id] ?: run {
            easyLearningService.getQuestions(collection.id).apply { questionsMap[collection.id] = this }
        }
        emit(questions)
    }.flowOn(Dispatchers.IO)

    override suspend fun getCollections() =
        flow { easyLearningService.getCollections().also { emit(it) } }.flowOn(Dispatchers.IO)
}