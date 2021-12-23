package com.ix.ibrahim7.ps.text.jerusalemapp.ui.viewmodel.auth

import androidx.lifecycle.ViewModel
import com.ix.ibrahim7.ps.text.jerusalemapp.repository.auth.SignUpRepository

class SignUpViewModel(
    private val signUpRepository: SignUpRepository = SignUpRepository()
) : ViewModel() {

    fun signUpUser(username:String,email: String, password: String) =
        signUpRepository.signup(username,email,password)

    fun getSignUp() = signUpRepository.getSignUp()

}