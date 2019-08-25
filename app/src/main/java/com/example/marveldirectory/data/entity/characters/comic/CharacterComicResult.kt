package com.example.marveldirectory.data.entity.characters.comic

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterComicResult(
    val id: String,
    val title: String,
    val thumbnail: CharacterComicThumbnail
) : Parcelable