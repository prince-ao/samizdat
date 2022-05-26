package com.example.samizdat.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.samizdat.retrofit.models.BookInfo

@Dao
interface FavBookDao {

    @Insert
    fun insertFavBook(favBook: FavBook)

    @Query("SELECT * FROM favBook")
    fun getAllFavBooks(): LiveData<List<FavBook>>

    @Query("SELECT * FROM favBook WHERE bookTitle = :title")
    fun findBook(title: String): List<FavBook>

    @Query("DELETE FROM favBook WHERE bookTitle = :title")
    fun deleteFavBook(title: String)
}