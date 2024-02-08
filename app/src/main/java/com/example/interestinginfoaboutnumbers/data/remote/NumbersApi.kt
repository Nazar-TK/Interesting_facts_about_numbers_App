package com.example.interestinginfoaboutnumbers.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface NumbersApi {

    @GET("{number}")
    suspend fun getNumberInfo(@Path("number") number: Int): String

    @GET("random/math")
    suspend fun getRandomNumberInfo(): String

}