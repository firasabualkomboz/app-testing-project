package com.ix.ibrahim7.ps.text.jerusalemapp.ui.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.ix.ibrahim7.ps.text.jerusalemapp.other.USERID
import com.ix.ibrahim7.ps.text.jerusalemapp.other.getSharePref
import com.ix.ibrahim7.ps.text.jerusalemapp.repository.home.HomeRepository


class HomeViewModel(
    private val homeRepository: HomeRepository = HomeRepository()
) : ViewModel() {



    fun getUserInfo(activity: Activity) =
        homeRepository.getUserInfo(activity.getSharePref().getString(USERID,"")!!)

    val userLiveData = homeRepository.userLiveData()

    fun getAllPosts() =
        homeRepository.getAllPosts()

    val postsLiveData = homeRepository.postsLiveData()

    init {
        getAllPosts()
    }

}