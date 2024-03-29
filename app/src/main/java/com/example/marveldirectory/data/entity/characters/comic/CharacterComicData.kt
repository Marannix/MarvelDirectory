package com.example.marveldirectory.data.entity.characters.comic

data class CharacterComicData(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<CharacterComicResult>
)