package com.example.samizdat.homefragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.samizdat.retrofit.ApiInstance
import com.example.samizdat.retrofit.models.HomeModelItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    var recent: MutableLiveData<Array<HomeModelItem>> = MutableLiveData<Array<HomeModelItem>>()

    fun getRecent(){
        if(recent.value != null){
            return
        }
        ApiInstance.Api.getRecent().enqueue(object : Callback<Array<HomeModelItem>?> {
            override fun onResponse(
                call: Call<Array<HomeModelItem>?>,
                response: Response<Array<HomeModelItem>?>
            ) {
                recent.value = response.body()
            }

            override fun onFailure(call: Call<Array<HomeModelItem>?>, t: Throwable) {
                Log.d("Recent Request failure", t.message.toString())
            }
        })
    }

    fun observeRecent(): MutableLiveData<Array<HomeModelItem>> {
        return recent;
    }

}