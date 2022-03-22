package com.sevenyes.miapi.presenter

import com.sevenyes.miapi.model.ClassicList
import com.sevenyes.miapi.network.RetroFItService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ClassicPresenter(
    private var musicGenreViewContract: MusicGenreViewContract<ClassicList>? = null
) {

    private var disposable = CompositeDisposable()

    fun reload (){
        RetroFItService.retrofit.getClassic()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    musicGenreViewContract?.onSuccess(it)
                },
                {
                    musicGenreViewContract?.onError(it)
                }
            ).also {
                disposable.add(it)
            }
    }

    fun destroy() {
        musicGenreViewContract = null
        disposable.dispose()
    }
}