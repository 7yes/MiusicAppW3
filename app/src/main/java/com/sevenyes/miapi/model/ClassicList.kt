package com.sevenyes.miapi.model

import com.google.gson.annotations.SerializedName

class ClassicList (
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val tracks: List<Classic>
)