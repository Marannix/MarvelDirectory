package com.example.marveldirectory.data.network.response

import com.example.marveldirectory.data.entity.characters.CharactersData
import com.google.gson.annotations.SerializedName

data class CharactersResponse (
    @SerializedName("data")
    val charactersData: CharactersData
)