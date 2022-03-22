package com.sevenyes.miapi.model

import com.google.gson.annotations.SerializedName

data class PopList (
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val tracks: List<Pop>
)