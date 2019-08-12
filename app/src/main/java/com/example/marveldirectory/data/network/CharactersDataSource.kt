package com.example.marveldirectory.data.network

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.marveldirectory.data.entity.characters.CharactersData
import com.example.marveldirectory.data.entity.characters.CharactersResults
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class CharactersDataSource(
    private val marvelApiService: MarvelApiService,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, CharactersResults>() {

    var state: MutableLiveData<NetworkState> = MutableLiveData()
    private var retryCompletable: Completable? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, CharactersResults>) {
        updateState(NetworkState.LOADING)
        compositeDisposable.add(
            marvelApiService.charactersApi().getCharacters(params.requestedLoadSize, 0).subscribe(
                { response ->
                    updateState(NetworkState.DONE)
                    callback.onResult(response.charactersData.results, null, 2)
                },
                {
                    updateState(NetworkState.ERROR)
                    setRetry(Action { loadInitial(params, callback) })
                }
            )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, CharactersResults>) {
        updateState(NetworkState.LOADING)
        compositeDisposable.add(marvelApiService.charactersApi().getCharacters(params.requestedLoadSize, params.key)
            .subscribe(
                { response ->
                    updateState(NetworkState.DONE)
                    callback.onResult(response.charactersData.results, params.key + 1)

                },
                {
                    updateState(NetworkState.ERROR)
                    setRetry(Action { loadAfter(params, callback) })
                }
            ))
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, CharactersResults>) {

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
}