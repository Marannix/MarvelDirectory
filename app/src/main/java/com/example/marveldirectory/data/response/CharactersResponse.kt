package com.example.marveldirectory.data.response

import com.example.marveldirectory.data.entity.DataEntry
import com.google.gson.annotations.SerializedName

data class CharactersResponse (
    @SerializedName("data")
    val dataEntry: DataEntry
)