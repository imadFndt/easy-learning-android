package com.kach.easylearning.model

import com.google.gson.annotations.SerializedName

data class EasyLearningAnswer(
    val id: String,
    val data: String,
    val description: String?,
    @SerializedName("author_id")
    val authorId: String,
    //TODO DateConverter
    val created: String,
    val questionId: String
)
