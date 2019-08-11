package com.example.marveldirectory.data.entity.characters.comics

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterComicResult(
    val title: String,
    val thumbnail: CharacterComicThumbnail
) : Parcelable