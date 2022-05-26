package com.example.samizdat.retrofit.models

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import kotlin.Array

data class BookInfo(
    val image: String?,
    val link: String?,
    val title: String?,
    val publisher: String?,
    val year: String?,
    val language: String?,
    val pages: String?,
    val size: String?,
    val description: String?,
    val isbm: String?,
    val id: String?,
    val author: String?,
    val format: String?,
    var imageBitmap: Bitmap?
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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
        parcel.writeString(link)
        parcel.writeString(title)
        parcel.writeString(publisher)
        parcel.writeString(year)
        parcel.writeString(language)
        parcel.writeString(pages)
        parcel.writeString(size)
        parcel.writeString(description)
        parcel.writeString(isbm)
        parcel.writeString(id)
        parcel.writeString(author)
        parcel.writeString(format)
        parcel.writeParcelable(imageBitmap, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BookInfo> {
        override fun createFromParcel(parcel: Parcel): BookInfo {
            return BookInfo(parcel)
        }

        override fun newArray(size: Int): Array<BookInfo?> {
            return arrayOfNulls(size)
        }
    }

}