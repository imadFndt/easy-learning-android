package com.kach.easylearning.remote

import com.kach.easylearning.model.AuthToken
import com.kach.easylearning.model.EasyLearningCollection
import com.kach.easylearning.model.EasyLearningQuestion
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface EasyLearningService {
    @POST("/auth/login")
    suspend fun authenticate(@Header("Authorization") credentials: String): AuthToken

    @GET("auth/check")
    suspend fun checkAuth(@Header("Authorization") credentials: String)

    @GET("/collections")
    suspend fun getCollections(): List<EasyLearningCollection>

    @GET("/collections/{collectionsId}")
    suspend fun getCollection(collectionId: String): EasyLearningCollection

    @GET("/collections/{collectionsId}/questions")
    suspend fun getQuestions(@Path("collectionsId") collectionId: String): List<EasyLearningQuestion>
}