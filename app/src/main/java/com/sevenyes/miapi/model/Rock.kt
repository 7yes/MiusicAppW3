package com.sevenyes.miapi.model


import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Rock(
    @SerializedName("artistName")
    val artistName: String,
    @SerializedName("artworkUrl60")
    val artworkUrl60: String,
    @SerializedName("previewUrl")
    val previewUrl: String,
    @SerializedName("trackId")
    @PrimaryKey val trackId: Int,
    @SerializedName("trackName")
    val trackName: String,
    @SerializedName("trackPrice")
    val trackPrice: Double,
)