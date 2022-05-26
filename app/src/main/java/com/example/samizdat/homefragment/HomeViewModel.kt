package com.example.samizdat.homefragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.samizdat.retrofit.ApiInstance
import com.example.samizdat.retrofit.models.BookInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    var recent: MutableLiveData<Array<BookInfo>> = MutableLiveData<Array<BookInfo>>()

    fun getRecent(){
        if(recent.value != null){
            return
        }
        ApiInstance.Api.getRecent().enqueue(object : Callback<Array<BookInfo>?> {
            override fun onResponse(
                call: Call<Array<BookInfo>?>,
                response: Response<Array<BookInfo>?>
            ) {
                recent.value = response.body()
            }

            override fun onFailure(call: Call<Array<BookInfo>?>, t: Throwable) {
                Log.d("Recent Request failure", t.message.toString())
            }
        })
    }

    fun observeRecent(): MutableLiveData<Array<BookInfo>> {
        return recent;
    }

}