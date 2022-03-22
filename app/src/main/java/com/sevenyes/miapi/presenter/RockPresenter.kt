package com.sevenyes.miapi.presenter

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.sevenyes.miapi.database.MusicTracksDatabase
import com.sevenyes.miapi.model.Rock
import com.sevenyes.miapi.model.RockList
import com.sevenyes.miapi.network.RetroFItService
import com.sevenyes.miapi.utils.NetworkMonitor
import com.sevenyes.miapi.utils.NetworkState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RockPresenter(
    private var context: Context? = null,
    private var musicGenreView: MusicGenreViewContract<List<Rock>>? = null,
    private var networkMonitor: NetworkMonitor? = NetworkMonitor(context),
    private var database: MusicTracksDatabase? = MusicTracksDatabase.getInstance(context!!)

) {
    private var disposable = CompositeDisposable()

    private var isConnected = true

    private fun readFromDb() {
        database?.let {
            it.rockDao()
                .getAllRock()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { tracks ->
                        musicGenreView?.onSuccess(tracks)
                    },
                    {error2 -> musicGenreView?.onError(error2)}
                ).apply { disposable.add(this) }
        }
    }

    fun checkNetwork() {
        networkMonitor?.registerNetworkMonitor()
        NetworkState
            .observeNetworkState
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    isConnected = it
                    Log.d("TAG", "$it")
                },
                {
                    isConnected = false
                    Log.d("TAG", "checkNetwork FALSE: ")

                }
            ).apply {
                disposable.add(this)
            }
    }

    fun getRock() {
        if (isConnected) {
            RetroFItService.retrofit.getRock()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { rockList ->
                        database?.let {
                            it.rockDao().writeAll(rockList.tracks)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                    { readFromDb() },
                                    { error1 -> musicGenreView?.onError(error1) }
                                ).apply { disposable.add(this) }
                        }
                    },
                    {
                        musicGenreView?.onError(it)
                    }
                ).also { disposable.add(it) }
        } else {
            readFromDb()
        }
    }

    fun destroy() {
        musicGenreView = null
        disposable.dispose()
        context = null
        networkMonitor?.unregisterNetworkMonitor()
        networkMonitor = null
        database = null
    }

    fun isConnected(): Boolean = isConnected

}

