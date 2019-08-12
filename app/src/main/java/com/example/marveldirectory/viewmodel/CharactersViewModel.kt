package com.example.marveldirectory.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.marveldirectory.data.entity.characters.CharactersResults
import com.example.marveldirectory.data.network.CharactersDataSource
import com.example.marveldirectory.data.network.CharactersDataSourceFactory
import com.example.marveldirectory.data.network.MarvelApiService
import com.example.marveldirectory.data.network.NetworkState
import io.reactivex.disposables.CompositeDisposable

class CharactersViewModel : ViewModel() {

    private val marvelApiService = object : MarvelApiService {}
    var charactersList: LiveData<PagedList<CharactersResults>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 20
    private var charactersDataSourceFactory: CharactersDataSourceFactory

    init {
        charactersDataSourceFactory = CharactersDataSourceFactory(marvelApiService, compositeDisposable)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 5)
            .setEnablePlaceholders(false)
            .build()
        charactersList = LivePagedListBuilder(charactersDataSourceFactory, config).build()
    }

    fun getState(): LiveData<NetworkState> = Transformations.switchMap<CharactersDataSource, NetworkState>(
        charactersDataSourceFactory.charactersDataSourceLiveData,
        CharactersDataSource::state
    )

    fun retry() {
        charactersDataSourceFactory.charactersDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty() : Boolean {
        return charactersList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}