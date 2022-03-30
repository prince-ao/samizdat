package com.example.samizdat.homefragment.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HomeAPIInstance {

    val homeApi:HomeAPIInterface by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.0.14:8081/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HomeAPIInterface::class.java)
    }
}