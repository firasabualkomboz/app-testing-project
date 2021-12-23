package com.ix.ibrahim7.ps.text.jerusalemapp.repository.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ix.ibrahim7.ps.text.jerusalemapp.util.Resource
import com.ix.ibrahim7.ps.text.jerusalemapp.model.User
import com.ix.ibrahim7.ps.text.jerusalemapp.other.USERS

class SignUpRepository{

    private val sigInLiveData = MutableLiveData<Resource<Boolean>>()
    fun getSignUp(): LiveData<Resource<Boolean>> = sigInLiveData


    fun signup(username: String,email:String,password:String){
        sigInLiveData.postValue(Resource.Loading())
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                it.result!!.user!!.uid
                insertUserUsingSet(it.result!!.user!!.uid,username, email)
                sigInLiveData.postValue(Resource.Success(true))
            } else {
                sigInLiveData.postValue(Resource.Error(it.exception!!.message!!,false))
            }
        }
    }



    fun insertUserUsingSet(userID: String, username: String,email:String) {
        val obj = User(userID= userID,username = username,email = email)
        FirebaseFirestore.getInstance().collection(USERS).document(userID).set(obj)
    }


}