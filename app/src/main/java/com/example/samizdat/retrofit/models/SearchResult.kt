package com.example.samizdat.retrofit.models

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

data class SearchResult(
    val array: ArrayList<BookInfo>?,
    val suggestion: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(BookInfo),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(array)
        parcel.writeString(suggestion)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchResult> {
        override fun createFromParcel(parcel: Parcel): SearchResult {
            return SearchResult(parcel)
        }

        override fun newArray(size: Int): kotlin.Array<SearchResult?> {
            return arrayOfNulls(size)
        }
    }
}