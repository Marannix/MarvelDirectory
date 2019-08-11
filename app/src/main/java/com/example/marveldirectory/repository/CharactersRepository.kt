package com.example.marveldirectory.repository

import android.content.Context
import com.example.marveldirectory.data.database.DatabaseHelper
import com.example.marveldirectory.data.entity.characters.CharactersData
import com.example.marveldirectory.data.entity.characters.CharactersResults
import com.example.marveldirectory.data.network.MarvelApiService
import com.example.marveldirectory.data.network.response.CharactersComicResponse
import com.example.marveldirectory.data.network.response.CharactersResponse
import io.reactivex.Single

class CharactersRepository(val context:Context){

    private val marvelApiService = object : MarvelApiService {}
    private val databaseHelper = DatabaseHelper(context)

    fun fetchCharacters(): Single<CharactersResponse> {
        return marvelApiService.charactersApi().getCharacters()
    }

    fun fetchCharacterComics(id: Int): Single<CharactersComicResponse> {
        return marvelApiService.charactersApi().getComics(id)
    }

    fun persistFetchedCharacters(characters: List<CharactersResults>) {
        databaseHelper.insertCharacters(characters)
    }

}