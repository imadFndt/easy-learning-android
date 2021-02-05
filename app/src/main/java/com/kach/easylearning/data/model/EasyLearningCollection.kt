package com.kach.easylearning.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class EasyLearningCollection(
    val id: String,
    val title: String,
    val description: String,
    @SerializedName("author_id")
    val authorId: String,
    //TODO DateConverter
    val created: String,
    @SerializedName("questions_count")
    val questionsCount: Int
)