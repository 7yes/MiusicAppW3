package com.sevenyes.miapi.model

import com.google.gson.annotations.SerializedName

class Pop (
        @SerializedName("artistName")
        val artistName: String,
        @SerializedName("artworkUrl60")
        val artworkUrl60: String,
        @SerializedName("previewUrl")
        val previewUrl: String,
        @SerializedName("trackId")
        val trackId: Int,
        @SerializedName("trackName")
        val trackName: String,
        @SerializedName("trackPrice")
        val trackPrice: Double,
)