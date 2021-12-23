package com.ix.ibrahim7.ps.text.jerusalemapp.repository.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.ix.ibrahim7.ps.text.jerusalemapp.util.Resource


class ResetPasswordRepository{

    private val forgetPasswordLiveData = MutableLiveData<Resource<Boolean>>()
    fun getforgetPassword(): LiveData<Resource<Boolean>> = forgetPasswordLiveData


    fun forgetPassword(email: String) {
        forgetPasswordLiveData.postValue(Resource.Loading())
        val auth = FirebaseAuth.getInstance()

        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    forgetPasswordLiveData.postValue(Resource.Success(true))
                } else {
                    forgetPasswordLiveData.postValue(Resource.Error(task.exception!!.message!!,false))
                }
            }
    }



}