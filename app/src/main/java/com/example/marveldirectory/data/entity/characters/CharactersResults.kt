package com.example.marveldirectory.data.entity.characters

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "characters")
data class CharactersResults(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val description: String,
    @Embedded(prefix = "thumbnail_")
    val thumbnail: CharactersThumbnail
) : Parcelable