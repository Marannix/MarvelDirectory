package com.example.marveldirectory.repository

import com.example.marveldirectory.data.network.MarvelApiService
import com.example.marveldirectory.data.network.response.CharactersComicResponse
import com.example.marveldirectory.data.network.response.CharactersResponse
import io.reactivex.Single

class CharactersRepository{

    private val marvelApiService = object : MarvelApiService {}

    fun fetchCharacters(): Single<CharactersResponse> {
        return marvelApiService.charactersApi().getCharacters()
    }

    fun fetchCharacterComics(id: Int): Single<CharactersComicResponse> {
        return marvelApiService.charactersApi().getComics(id)
    }

}