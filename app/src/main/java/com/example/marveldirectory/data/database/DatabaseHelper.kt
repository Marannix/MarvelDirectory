package com.example.marveldirectory.data.database

import android.content.Context
import com.example.marveldirectory.data.entity.characters.CharactersData
import com.example.marveldirectory.data.entity.characters.CharactersResults

class DatabaseHelper(val context: Context) {

    private var database: MarvelDatabase? = null

    fun retrieveDatabase() {
        database = MarvelDatabase.DatabaseProvider.getInstance(context)
    }

    fun insertCharacters(characters: List<CharactersResults>) {
        database?.charactersDataDao()?.insert(characters)
    }
}