package com.example.marveldirectory.data.entity.characters.comics

data class CharacterComicEntry(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<CharacterComicResult>
)