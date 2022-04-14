package com.example.samizdat.retrofit

import com.example.samizdat.retrofit.models.HomeModelItem
import com.example.samizdat.retrofit.models.SearchResult
import com.example.samizdat.searchfragment.Post
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @GET("recent")
    fun getRecent(): Call<Array<HomeModelItem>>

    @POST("search")
    fun search(@Body post: Post): Call<SearchResult>
}