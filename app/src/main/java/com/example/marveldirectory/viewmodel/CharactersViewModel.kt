package com.example.marveldirectory.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
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
import com.example.marveldirectory.repository.CharactersRepository
import io.reactivex.disposables.CompositeDisposable
import kotlin.coroutines.coroutineContext

class CharactersViewModel(application: Application) : AndroidViewModel(application) {

    private val marvelApiService = object : MarvelApiService {}
    var charactersList: LiveData<PagedList<CharactersResults>>
    private val charactersRepository = CharactersRepository(application.baseContext)
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 60
    private var charactersDataSourceFactory: CharactersDataSourceFactory

    init {
        charactersDataSourceFactory = CharactersDataSourceFactory(charactersRepository, marvelApiService, compositeDisposable)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(90)
            .setEnablePlaceholders(true)
            .setPrefetchDistance(90)
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

    fun getTotal() : Int {
        return charactersDataSourceFactory.charactersDataSourceLiveData.value?.getTotalCount()!!
    }

    fun listIsEmpty() : Boolean {
        return charactersList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}