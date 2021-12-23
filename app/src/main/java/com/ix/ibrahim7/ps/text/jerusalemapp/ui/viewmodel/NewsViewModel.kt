package com.ix.ibrahim7.ps.text.jerusalemapp.ui.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.ix.ibrahim7.ps.text.jerusalemapp.other.USERID
import com.ix.ibrahim7.ps.text.jerusalemapp.other.getSharePref
import com.ix.ibrahim7.ps.text.jerusalemapp.repository.ApiRepository
import com.ix.ibrahim7.ps.text.jerusalemapp.repository.home.HomeRepository
import okhttp3.RequestBody


class NewsViewModel(
    private val apiRepository: ApiRepository = ApiRepository()
) : ViewModel() {

    fun getNews(q: String, apiKey: String) =
        apiRepository.getNews(q, apiKey)

    fun getNewsLiveData() = apiRepository.newsLiveData

}