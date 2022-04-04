package com.ix.ibrahim7.ps.text.jerusalemapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.ix.ibrahim7.ps.text.jerusalemapp.repository.ApiRepository

class NewsViewModel(private val apiRepository: ApiRepository = ApiRepository()) : ViewModel() {

    fun getNews(q: String, apiKey: String) = apiRepository.getNews(q, apiKey)

    fun getNewsLiveData() = apiRepository.newsLiveData
}
