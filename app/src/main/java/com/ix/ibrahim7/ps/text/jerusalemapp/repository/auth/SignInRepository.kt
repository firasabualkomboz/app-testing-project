package com.ix.ibrahim7.ps.text.jerusalemapp.repository.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.ix.ibrahim7.ps.text.jerusalemapp.util.Resource

class SignInRepository{

    private val sigInLiveData = MutableLiveData<Resource<String>>()
    fun getSignIn(): LiveData<Resource<String>> = sigInLiveData


    fun signIn(email: String, password: String) {
        sigInLiveData.postValue(Resource.Loading())
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    sigInLiveData.postValue(Resource.Success(it.result.user!!.uid))
                } else {
                    sigInLiveData.postValue(Resource.Error(it.exception!!.message!!,""))
                }
            }
    }



}