package com.example.marveldirectory.data.network

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.marveldirectory.data.entity.characters.CharactersResults
import com.example.marveldirectory.data.entity.characters.comic.CharacterComicResult
import io.reactivex.disposables.CompositeDisposable

class ComicsDataSourceFactory(
    private val marvelApiService: MarvelApiService,
    private val compositeDisposable: CompositeDisposable,
    private val character: CharactersResults,
    private val comic: CharacterComicResult
) : DataSource.Factory<Int, CharacterComicResult>() {

    val comicsDataSourceLiveData = MutableLiveData<ComicsDataSource>()

    override fun create(): DataSource<Int, CharacterComicResult> {
        val comicsDataSource = ComicsDataSource(marvelApiService, compositeDisposable, character, comic)
        comicsDataSourceLiveData.postValue(comicsDataSource)
        return comicsDataSource
    }
}