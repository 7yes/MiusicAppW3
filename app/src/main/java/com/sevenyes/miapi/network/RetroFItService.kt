package com.sevenyes.miapi.network

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetroFItService {

    private val interceptor by lazy {
        HttpLoggingInterceptor().apply {
            level= HttpLoggingInterceptor.Level.BODY
        }
    }

    private val okHttpCliente by lazy {
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30,TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
        // I dont use write time out 'cause i'm not writing
            .build()
    }

    var retrofit = Retrofit.Builder()
        .baseUrl(MusicAPI.BASE_PATH)
        .client(okHttpCliente)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(MusicAPI::class.java)
}