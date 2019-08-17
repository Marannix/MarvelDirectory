package com.example.marveldirectory.data.network

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.marveldirectory.data.entity.characters.CharactersData
import com.example.marveldirectory.data.entity.characters.CharactersResults
import com.example.marveldirectory.repository.CharactersRepository
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class CharactersDataSource(
    private val charactersRepository: CharactersRepository,
    private val marvelApiService: MarvelApiService,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, CharactersResults>() {

    var state: MutableLiveData<NetworkState> = MutableLiveData()
    private var retryCompletable: Completable? = null
    private var offset = 60

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, CharactersResults>) {
        updateState(NetworkState.LOADING)
        compositeDisposable.add(
            marvelApiService.charactersApi().getCharacters(params.requestedLoadSize, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                { response ->
                    updateState(NetworkState.DONE)
                    charactersRepository.persistFetchedCharacters(response.charactersData.results)
                    callback.onResult(response.charactersData.results, null, offset)
                    Log.d("total", "total is ${response.charactersData.total}")
                    Log.d("total", "count is ${response.charactersData.count}")
                    Log.d("total", "offset is ${response.charactersData.offset}")
                },
                {
                    updateState(NetworkState.ERROR)
                    Log.e("loadInitial:", it.message)
                    setRetry(Action { loadInitial(params, callback) })
                }
            )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, CharactersResults>) {
        updateState(NetworkState.LOADING)
        compositeDisposable.add(
            marvelApiService.charactersApi().getCharacters(params.requestedLoadSize, params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                    { response ->
                        Log.d("loadAfter", params.key.toString())
                        updateState(NetworkState.DONE)
                        charactersRepository.persistFetchedCharacters(response.charactersData.results)
                        callback.onResult(response.charactersData.results, params.key + offset + 1)
                        Log.d("loadAfterNewKey", params.key.toString())
                        Log.d("total2", "total is ${response.charactersData.total}")
                        Log.d("total2", "count is ${response.charactersData.count}")
                        Log.d("total2", "offset is ${response.charactersData.offset}")
                    },
            {
                updateState(NetworkState.ERROR)
                Log.e("loadAfter Error:", it.message)
                setRetry(Action { loadAfter(params, callback) })
            }
                )
        )
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