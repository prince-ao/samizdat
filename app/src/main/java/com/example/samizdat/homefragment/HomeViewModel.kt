package com.example.samizdat.homefragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.samizdat.homefragment.retrofit.HomeAPIInstance
import com.example.samizdat.homefragment.retrofit.HomeModelItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    val recent: MutableLiveData<List<HomeModelItem>> = MutableLiveData<List<HomeModelItem>>()

    init {
        getRecent()
    }

    private fun getRecent(){
        HomeAPIInstance.homeApi.getRecent().enqueue(object : Callback<List<HomeModelItem>?> {
            override fun onResponse(
                call: Call<List<HomeModelItem>?>,
                response: Response<List<HomeModelItem>?>
            ) {
                recent.value = response.body()!!
            }

            override fun onFailure(call: Call<List<HomeModelItem>?>, t: Throwable) {
                Log.d("Recent Request failure", t.message.toString())
            }
        })
    }

    fun observeRecent(): LiveData<List<HomeModelItem>>{
        return recent;
    }

}