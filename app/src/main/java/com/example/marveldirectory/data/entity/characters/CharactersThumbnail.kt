package com.example.marveldirectory.data.entity.characters

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharactersThumbnail(
    //http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784"
    val path: String,
    //"jpg"
    val extension: String
) : Parcelable