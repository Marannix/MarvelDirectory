package com.example.marveldirectory.data.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.marveldirectory.data.entity.characters.CharactersDataDao
import com.example.marveldirectory.data.entity.characters.CharactersResults

@Database(
    entities = [CharactersResults::class],
    version = 1
)
abstract class MarvelDatabase : RoomDatabase() {
    abstract fun charactersDataDao(): CharactersDataDao

    object DatabaseProvider {
        private var instance: MarvelDatabase? = null

        fun getInstance(context: Context): MarvelDatabase? {
            if (instance == null) {
                synchronized(MarvelDatabase::class) {
                    instance = buildDatabase(context)
                }
            }
            return instance
        }

        private fun buildDatabase(context: Context): MarvelDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                MarvelDatabase::class.java, "weather.db"
            ).allowMainThreadQueries().build()
        }


    }
}