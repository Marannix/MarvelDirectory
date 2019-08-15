package com.example.marveldirectory.data.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.marveldirectory.data.entity.characters.CharactersData
import com.example.marveldirectory.data.entity.characters.CharactersResults

class DatabaseHelper(val context: Context) {

    private var database: MarvelDatabase = MarvelDatabase.DatabaseProvider.getInstance(context)!!

    fun retrieveDatabase() {
        database = MarvelDatabase.DatabaseProvider.getInstance(context)!!
    }

    fun getCharacters(): List<CharactersResults> {
        return database.charactersDataDao().getAllCharacters()
    }

    fun insertCharacters(characters: List<CharactersResults>) {
        database.charactersDataDao().insert(characters)
    }
}