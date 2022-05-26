package com.example.samizdat.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.samizdat.retrofit.models.BookInfo
import kotlinx.coroutines.*

class FavBookRepository(application: Application) {
    val searchRes = MutableLiveData<List<FavBook>>()
    private var favBookDao: FavBookDao?
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    val allFavBook: LiveData<List<FavBook>>?

    init {
        val db: FavBookRoomDatabase? = FavBookRoomDatabase.getDatabase(application)
        favBookDao = db?.favBookDao()
        allFavBook = favBookDao?.getAllFavBooks()
    }

    fun findFavBook(title: String){
            coroutineScope.launch(Dispatchers.Main) {
                searchRes.value = asyncFind(title).await()
            }
    }

    fun insertFavBook(newFavBook: FavBook){
        coroutineScope.launch(Dispatchers.IO) {
            asyncInsert(newFavBook)
        }
    }

    fun deleteFavBook(book: BookInfo){
        coroutineScope.launch(Dispatchers.IO) {
            asyncDelete(book)
        }
    }

    private suspend fun asyncDelete(book: BookInfo){
        favBookDao?.deleteFavBook(book.title!!)
    }

    private suspend fun asyncInsert(favBook: FavBook){
        favBookDao?.insertFavBook(favBook)
    }

    private suspend fun asyncFind(name: String): Deferred<List<FavBook>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async favBookDao?.findBook(name)
        }

}