package com.example.marveldirectory.api

import com.example.marveldirectory.data.network.response.CharactersComicResponse
import com.example.marveldirectory.data.network.response.CharactersResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_KEY = "f0bc3cc36c0b6b9a740687938bb7ec18"
const val HASH_KEY = "8d04dfcad3727abed7b121c4b2d90178"
const val TIME_STAMP = "1"
const val AUTH = "ts=$TIME_STAMP&apikey=$API_KEY&hash=$HASH_KEY"

interface CharactersApi {

    @GET("characters?$AUTH")
    fun getCharacters(@Query("limit") limit: Int, @Query("offset") offset: Int): Single<CharactersResponse>

    @GET("characters/{id}/comics?$AUTH")
    fun getComics(@Path("id") id: Int): Single<CharactersComicResponse>

}