package com.kach.tuts.model

import com.google.gson.annotations.SerializedName

data class TutsCollection(
    val id: String,
    val title: String,
    val description: String,
    @SerializedName("author_id")
    val authorId: String,
    //TODO DateConverter
    val created: String
)
