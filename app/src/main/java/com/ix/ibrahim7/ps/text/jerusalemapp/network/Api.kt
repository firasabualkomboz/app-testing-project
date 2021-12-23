package com.ix.ibrahim7.ps.text.jerusalemapp.network

import com.ix.ibrahim7.ps.text.jerusalemapp.model.news.News
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @GET("everything")
    suspend fun getNews(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String
    ): Response<News>

}