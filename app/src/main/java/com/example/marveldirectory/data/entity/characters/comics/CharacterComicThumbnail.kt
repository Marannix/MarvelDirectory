package com.example.marveldirectory.data.entity.characters.comics

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterComicThumbnail(
    //http://i.annihil.us/u/prod/marvel/i/mg/d/03/58dd080719806"
    val path: String,
    //"jpg"
    val extension: String
) : Parcelable
