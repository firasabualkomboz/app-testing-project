package com.ix.ibrahim7.ps.text.jerusalemapp.ui.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.ix.ibrahim7.ps.text.jerusalemapp.model.Post
import com.ix.ibrahim7.ps.text.jerusalemapp.model.User
import com.ix.ibrahim7.ps.text.jerusalemapp.repository.auth.SignInRepository
import com.ix.ibrahim7.ps.text.jerusalemapp.repository.home.AddPostRepository
import com.ix.ibrahim7.ps.text.jerusalemapp.repository.home.ProfileRepository
import java.util.HashMap


class ProfileViewModel(
    private val profileRepository: ProfileRepository = ProfileRepository()
) : ViewModel() {


    fun updateUserData(hashMap: HashMap<String, Any>) =
        profileRepository.updateUserData(hashMap)

    val updateUserDataLiveData = profileRepository.updateUserDataLiveData


}