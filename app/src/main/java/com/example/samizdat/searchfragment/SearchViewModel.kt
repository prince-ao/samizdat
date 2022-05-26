package com.example.samizdat.searchfragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.samizdat.retrofit.ApiInstance
import com.example.samizdat.retrofit.models.SearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel(){
    val results: MutableLiveData<SearchResult> = MutableLiveData<SearchResult>()

    fun search(word: Post){
        ApiInstance.Api.search(word).enqueue(object: Callback<SearchResult?> {
            override fun onResponse(call: Call<SearchResult?>, response: Response<SearchResult?>) {
                Log.d("Data = ", response.toString())
                results.value = response.body()
            }

            override fun onFailure(call: Call<SearchResult?>, t: Throwable) {
                Log.d("Search Request failure", t.message.toString())
            }

        })
    }
}