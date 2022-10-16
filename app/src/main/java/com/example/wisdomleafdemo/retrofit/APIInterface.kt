package com.example.wisdomleafdemo.retrofit

import com.example.wisdomleafdemo.pojo.ExampleData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {

    @GET("list")
    suspend fun getExamples(
        @Query("page") page: String,
        @Query("limit") limit: String,
    ): Response<List<ExampleData>?>
}