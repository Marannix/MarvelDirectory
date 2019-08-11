package com.example.marveldirectory.data.entity.characters

import androidx.room.Entity
import androidx.room.PrimaryKey

data class CharactersData (
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<CharactersResults>
)