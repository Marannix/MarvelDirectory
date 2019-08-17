package com.example.marveldirectory.data.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.marveldirectory.data.entity.characters.CharactersResults
import com.example.marveldirectory.repository.CharactersRepository
import io.reactivex.disposables.CompositeDisposable


class CharactersDataSourceFactory(
    private val charactersRepository: CharactersRepository,
    private val marvelApiService: MarvelApiService,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, CharactersResults>() {

    val charactersDataSourceLiveData = MutableLiveData<CharactersDataSource>()

    override fun create(): DataSource<Int, CharactersResults> {
        val charactersDataSource = CharactersDataSource(charactersRepository, marvelApiService, compositeDisposable)
        charactersDataSourceLiveData.postValue(charactersDataSource)
        return charactersDataSource
    }

}