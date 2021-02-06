package com.kach.easylearning.data.repository

import com.kach.easylearning.data.model.EasyLearningCollection
import com.kach.easylearning.data.model.EasyLearningQuestion
import com.kach.easylearning.data.remote.EasyLearningService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val easyLearningService: EasyLearningService) : Repository {

    private val questionsMap = mutableMapOf<String, List<EasyLearningQuestion>>()

    override suspend fun getQuestions(collection: EasyLearningCollection) = flow {
        val questions = questionsMap[collection.id] ?: run {
            easyLearningService.getQuestions(collection.id).apply { questionsMap[collection.id] = this }
        }
        emit(questions)
    }

    override suspend fun getCollections() = flow { easyLearningService.getCollections().also { emit(it) } }
}