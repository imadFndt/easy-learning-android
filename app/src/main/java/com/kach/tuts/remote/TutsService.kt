package com.kach.tuts.remote

import com.kach.tuts.model.AuthToken
import com.kach.tuts.model.TutsCollection
import com.kach.tuts.model.TutsQuestion
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TutsService {
    @POST("/auth/login")
    fun authenticate(@Header("Authorization") credentials: String): AuthToken

    @GET("auth/check")
    fun checkAuth(@Header("Authorization") credentials: String)

    @GET("/collections")
    fun getCollections(): List<TutsCollection>

    @GET("/collections/{collectionsId}")
    fun getCollection(collectionId: String): TutsCollection

    @GET
    fun getQuestions(collectionId: String): List<TutsQuestion>
}