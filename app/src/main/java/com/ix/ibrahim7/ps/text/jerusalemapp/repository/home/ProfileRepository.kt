package com.ix.ibrahim7.ps.text.jerusalemapp.repository.home

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.ix.ibrahim7.ps.text.jerusalemapp.model.Comment
import com.ix.ibrahim7.ps.text.jerusalemapp.model.Post
import com.ix.ibrahim7.ps.text.jerusalemapp.model.User
import com.ix.ibrahim7.ps.text.jerusalemapp.other.*
import com.ix.ibrahim7.ps.text.jerusalemapp.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ProfileRepository{

    private val updateUserDataMutableLiveData = MutableLiveData<Resource<Any>>()

    fun updateUserData(hashMap: HashMap<String,Any>) {
        CoroutineScope(Dispatchers.IO).launch {
            updateUserDataMutableLiveData.postValue(Resource.Loading())
            FirebaseFirestore.getInstance().collection(USERS).document(FirebaseAuth.getInstance().uid!!)
                .update(hashMap)
                .addOnSuccessListener {
                    updateUserDataMutableLiveData.postValue(Resource.Success(true))
                }.addOnFailureListener {
                    updateUserDataMutableLiveData.postValue(Resource.Error(it.message!!, false))
                }
        }
    }

    val updateUserDataLiveData: LiveData<Resource<Any>> = updateUserDataMutableLiveData


}