package com.ix.ibrahim7.ps.text.jerusalemapp.ui.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.ix.ibrahim7.ps.text.jerusalemapp.model.Post
import com.ix.ibrahim7.ps.text.jerusalemapp.model.User
import com.ix.ibrahim7.ps.text.jerusalemapp.repository.auth.SignInRepository
import com.ix.ibrahim7.ps.text.jerusalemapp.repository.home.AddPostRepository


class AddPostViewModel(
    private val addPostRepository: AddPostRepository = AddPostRepository()
) : ViewModel() {



    fun addPost(postDescription:String, user: User, postType:String,postContent:String) =
        addPostRepository.addPost(postDescription, user, postType,postContent)


    fun ZipImage(uri: Uri, context: Context, onsuccess: (image: ByteArray) -> Unit) =
        addPostRepository.ZipImage(uri, context, onsuccess)

    fun uploadImage(selectedImage: ByteArray, onsuccess: (imagePath: String) -> Unit) =
        addPostRepository.uploadImage(selectedImage, onsuccess)


    fun uploadVideo(uri: Uri, onsuccess: (imagePath: String) -> Unit) =
        addPostRepository.uploadVideo(uri, onsuccess)

    fun getAddPostLiveData() = addPostRepository.getAddPostLiveData()
    fun postContentLiveData() = addPostRepository.postContentLiveData


}