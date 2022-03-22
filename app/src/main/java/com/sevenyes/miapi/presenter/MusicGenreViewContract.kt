package com.sevenyes.miapi.presenter

import com.sevenyes.miapi.model.ClassicList

interface MusicGenreViewContract<T> {
    fun onSuccess(trackList: T)
    fun onError(throwable: Throwable)
}