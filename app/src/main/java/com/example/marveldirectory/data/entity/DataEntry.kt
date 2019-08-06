package com.example.marveldirectory.data.entity

import com.example.marveldirectory.data.entity.characters.Results

data class DataEntry (
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Results>
)