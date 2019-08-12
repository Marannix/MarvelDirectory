package com.example.marveldirectory.data.entity.characters

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

const val CURRENT_CHARACTER_ID = 0

@Parcelize
@Entity(tableName = "characters")
data class CharactersResults(
    val id: Int,
    val name: String,
    val description: String,
    @Embedded(prefix = "thumbnail_")
    val thumbnail: CharactersThumbnail
) : Parcelable
{
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var primaryId: Int = CURRENT_CHARACTER_ID
}