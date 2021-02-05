package com.kach.easylearning.data.repository

import com.kach.easylearning.data.model.EasyLearningCollection
import com.kach.easylearning.data.model.EasyLearningQuestion
import kotlinx.coroutines.flow.Flow

interface BaseRepository {
    suspend fun getCollections(): Flow<List<EasyLearningCollection>>
    suspend fun requestQuestions(collection: EasyLearningCollection): Flow<List<EasyLearningQuestion>>
}