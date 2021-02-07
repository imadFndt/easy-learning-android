package com.kach.easylearning.data.repository

import com.kach.easylearning.data.model.EasyLearningCollection
import com.kach.easylearning.data.model.EasyLearningQuestion
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getCollections(): Flow<Result<List<EasyLearningCollection>>>
    suspend fun getQuestions(collection: EasyLearningCollection): Flow<Result<List<EasyLearningQuestion>>>
}