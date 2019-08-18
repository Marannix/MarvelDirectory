package com.example.marveldirectory.data.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.marveldirectory.data.entity.characters.comic.CharacterComicResult
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class ComicsDataSource(
    private val marvelApiService: MarvelApiService,
    private val compositeDisposable: CompositeDisposable,
    private val characterId: Int
) : PageKeyedDataSource<Int, CharacterComicResult>() {

    var state: MutableLiveData<NetworkState> = MutableLiveData()
    private var retryCompletable: Completable? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, CharacterComicResult>) {
        updateState(NetworkState.LOADING)
        if (characterId != -1) {
            getSpecificCharacterComics(callback, params)
        } else {
            getAllComics()
        }
    }

    private fun getSpecificCharacterComics(
        callback: LoadInitialCallback<Int, CharacterComicResult>,
        params: LoadInitialParams<Int>
    ) {
        compositeDisposable.add(
            marvelApiService.charactersApi().getComics(characterId, params.requestedLoadSize, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                {
                    updateState(NetworkState.DONE)
                    callback.onResult(it.characterComicData.results, null, 20)
                },
                {
                    updateState(NetworkState.ERROR)
                    Log.e("load comics Initial:", it.message)
                    setRetry(Action {
                        loadInitial(params, callback)
                    })
                }
            )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, CharacterComicResult>) {
        updateState(NetworkState.LOADING)
        compositeDisposable.add(
            marvelApiService.charactersApi().getComics(characterId, params.requestedLoadSize, params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        updateState(NetworkState.DONE)
                        if (it.characterComicData.total > params.key) {

                        }
                        callback.onResult(it.characterComicData.results, params.key + 20)
                    },
                    {
                        updateState(NetworkState.ERROR)
                        Log.e("comcisLoadAfter Error:", it.message)
                        setRetry(Action { loadAfter(params, callback) })
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, CharacterComicResult>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun updateState(state: NetworkState) {
        this.state.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(
                retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }


    private fun getAllComics() {

    }
}