package com.example.samizdat.retrofit.models

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

data class Array(
    val author: String?,
    val description: String?,
    val format: String?,
    val id: String?,
    val image: String?,
    val isbm: String?,
    val language: String?,
    val link: String?,
    val pages: String?,
    val publisher: String?,
    val size: String?,
    val title: String?,
    val year: String?,
    var bitmap: Bitmap?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Bitmap::class.java.classLoader)
    ) {
    }

    companion object CREATOR : Parcelable.Creator<Array> {
        override fun createFromParcel(parcel: Parcel): Array {
            return Array(parcel)
        }

        override fun newArray(size: Int):  kotlin.Array<Array?> {
            return arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(author)
        dest?.writeString(description)
        dest?.writeString(format)
        dest?.writeString(id)
        dest?.writeString(image)
        dest?.writeString(isbm)
        dest?.writeString(language)
        dest?.writeString(link)
        dest?.writeString(pages)
        dest?.writeString(publisher)
        dest?.writeString(size)
        dest?.writeString(title)
        dest?.writeString(year)
        dest?.writeParcelable(bitmap, flags)
    }

}