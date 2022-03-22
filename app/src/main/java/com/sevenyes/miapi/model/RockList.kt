package com.sevenyes.miapi.model


import com.google.gson.annotations.SerializedName

data class RockList(
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val tracks: List<Rock>
)