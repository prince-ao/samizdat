package com.example.samizdat.database

import android.graphics.Bitmap
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.samizdat.retrofit.models.BookInfo

@Entity(tableName = "favBook")
class FavBook {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "bookNum")
    var idN: Int = 0

    @ColumnInfo(name = "bookTitle")
    var title: String? = null

    @ColumnInfo(name = "bookImage")
    var image: String? = null

    @ColumnInfo(name = "bookLink")
    var link: String? = null

    @ColumnInfo(name = "bookPublisher")
    var publisher: String? = null

    @ColumnInfo(name = "bookYear")
    var year: String? = null

    @ColumnInfo(name = "bookLanguage")
    var language: String? = null

    @ColumnInfo(name = "bookSize")
    var size: String? = null

    @ColumnInfo(name = "bookDescription")
    var description: String? = null

    @ColumnInfo(name = "bookIsbm")
    var isbm: String? = null

    @ColumnInfo(name = "bookId")
    var id: String? = null

    @ColumnInfo(name = "bookPages")
    var pages: String? = null

    @ColumnInfo(name = "bookAuthor")
    var author: String? = null

    @ColumnInfo(name = "bookFormat")
    var format: String? = null

    constructor(title: String, image: String, link: String, publisher: String, year: String, language: String, size: String, description: String, isbm: String, id: String, author: String, format: String, pages: String) {
        this.title = title
        this.image = image
        this.link = link
        this.publisher = publisher
        this.year = year
        this.language = language
        this.size = size
        this.description = description
        this.isbm = isbm
        this.id = id
        this.author = author
        this.format = format
        this.pages = pages
    }
}