package com.example.marveldirectory.data.network.response

import com.example.marveldirectory.data.entity.characters.comics.CharacterComicEntry
import com.google.gson.annotations.SerializedName

data class CharactersComicResponse(
    @SerializedName("data")
    val characterComicEntry: CharacterComicEntry
)