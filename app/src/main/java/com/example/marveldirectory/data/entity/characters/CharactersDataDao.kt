package com.example.marveldirectory.data.entity.characters

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface CharactersDataDao {

    @Query("select * from characters")
    fun getAllCharacters(): List<CharactersResults>

    @Insert(onConflict = REPLACE)
    fun insert(charactersData: List<CharactersResults>)
}