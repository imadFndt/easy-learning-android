package com.kach.easylearning.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EasyLearningServiceProvider {
    private const val URL = "http://62.109.14.53:8000"

    val easyLearningService: EasyLearningService by lazy {
        retrofitBuilder.build().create(EasyLearningService::class.java)
    }

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create())
    }
}