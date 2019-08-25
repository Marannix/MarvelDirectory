package com.example.marveldirectory.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.marveldirectory.data.entity.characters.CharactersResults
import com.example.marveldirectory.data.entity.characters.comic.CharacterComicResult
import com.example.marveldirectory.data.network.ComicsDataSource
import com.example.marveldirectory.data.network.ComicsDataSourceFactory
import com.example.marveldirectory.data.network.MarvelApiService
import com.example.marveldirectory.data.network.NetworkState
import io.reactivex.disposables.CompositeDisposable

class ComicsViewModel : ViewModel() {

    private var marvelApiService = object : MarvelApiService {}
    lateinit var comicsList: LiveData<PagedList<CharacterComicResult>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 10
    private lateinit var comicsDataSourceFactory: ComicsDataSourceFactory

    fun initViewModel(character: CharactersResults, comic: CharacterComicResult) {
        comicsDataSourceFactory = ComicsDataSourceFactory(marvelApiService, compositeDisposable, character, comic)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setEnablePlaceholders(true)
            .setPrefetchDistance(10)
            .build()
        comicsList = LivePagedListBuilder(comicsDataSourceFactory, config).build()
    }

    fun getState(): LiveData<NetworkState> = Transformations.switchMap<ComicsDataSource, NetworkState>(
        comicsDataSourceFactory.comicsDataSourceLiveData,
        ComicsDataSource::state
    )

    fun retry() {
        comicsDataSourceFactory.comicsDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty() : Boolean {
        return comicsList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}