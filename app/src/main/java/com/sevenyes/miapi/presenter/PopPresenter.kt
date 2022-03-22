package com.sevenyes.miapi.presenter

import android.util.Log
import com.sevenyes.miapi.model.PopList
import com.sevenyes.miapi.network.RetroFItService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PopPresenter (
    private var musicContract : MusicGenreViewContract<PopList>? = null
)  {

    private var disposable = CompositeDisposable()

    fun getPop(){
        RetroFItService.retrofit.getPop()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            {
                musicContract?.onSuccess(it)
            },
            {
                musicContract?.onError(it)
            }
        ).also {
            disposable.add(it)
        }}

    fun destroy (){
        musicContract = null
        disposable.dispose()
    }

}