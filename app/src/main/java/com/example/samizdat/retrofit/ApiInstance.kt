package com.example.samizdat.retrofit

import com.example.samizdat.retrofit.ApiInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object ApiInstance {
    val s: OkHttpClient.Builder = OkHttpClient.Builder()
        .callTimeout(5, TimeUnit.MINUTES)
        .connectTimeout(5, TimeUnit.MINUTES)
        .readTimeout(5, TimeUnit.MINUTES)
    val Api: ApiInterface by lazy {
        Retrofit.Builder()
            .client(s.build())
            .baseUrl("http://192.168.0.14:8081/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}