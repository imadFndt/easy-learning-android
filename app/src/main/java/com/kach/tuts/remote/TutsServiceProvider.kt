package com.kach.tuts.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TutsServiceProvider {
    private const val URL = "http://api.github.com"

    val tutsService: TutsService by lazy {
        retrofitBuilder.build().create(TutsService::class.java)
    }

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
    }
}