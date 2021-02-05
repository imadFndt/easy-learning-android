package com.kach.easylearning.data.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DateFormat

object EasyLearningServiceProvider {
    private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    private const val URL = "http://62.109.14.53:8000"

    val easyLearningService: EasyLearningService by lazy {
        retrofitBuilder.build().create(EasyLearningService::class.java)
    }

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create())
    }
    private val gson: Gson by lazy {
        GsonBuilder().create()
    }
}