package com.example.samizdat.global

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.samizdat.database.FavBook
import com.example.samizdat.database.FavBookRepository
import com.example.samizdat.retrofit.models.BookInfo

class FavBookViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavBookRepository = FavBookRepository(application)
    private val allFavBook: LiveData<List<FavBook>>?
    private val searchResult: MutableLiveData<List<FavBook>>
    init {
        allFavBook = repository.allFavBook
        searchResult = repository.searchRes
    }

    fun findBook(book: String){
        repository.findFavBook(book)
    }

    fun insertBook(favBook: FavBook){
        repository.insertFavBook(favBook)
    }

    fun deleteBook(book: BookInfo){
        repository.deleteFavBook(book)
    }

    fun getSearchResults(): MutableLiveData<List<FavBook>> {
        return searchResult
    }

    fun getAllProducts(): LiveData<List<FavBook>>? {
        return allFavBook
    }

}