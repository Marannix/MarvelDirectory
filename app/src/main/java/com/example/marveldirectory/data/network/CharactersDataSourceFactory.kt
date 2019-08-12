package com.example.marveldirectory.data.network

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.marveldirectory.data.entity.characters.CharactersResults
import io.reactivex.disposables.CompositeDisposable


class CharactersDataSourceFactory(
    private val marvelApiService: MarvelApiService,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, CharactersResults>() {

    val charactersDataSourceLiveData = MutableLiveData<CharactersDataSource>()

    override fun create(): DataSource<Int, CharactersResults> {
        val charactersDataSource = CharactersDataSource(marvelApiService, compositeDisposable)
        charactersDataSourceLiveData.postValue(charactersDataSource)
        return charactersDataSource
    }

}