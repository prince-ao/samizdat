package com.example.samizdat.homefragment.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface HomeAPIInterface {
    @GET("recent")
    fun getRecent(): Call<List<HomeModelItem>>
}