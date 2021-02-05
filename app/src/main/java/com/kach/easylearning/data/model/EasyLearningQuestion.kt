package com.kach.easylearning.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class EasyLearningQuestion(
    val id: String,
    val data: String,
    val description: String?,
    @SerializedName("author_id")
    val authorId: String,
    val created: Date
)