package com.ix.ibrahim7.ps.text.jerusalemapp.ui.viewmodel.auth

import androidx.lifecycle.ViewModel
import com.ix.ibrahim7.ps.text.jerusalemapp.repository.auth.SignInRepository


class SignInViewModel(
    private val sigInRepository :SignInRepository = SignInRepository()
) : ViewModel() {



    fun signInWithEmailAndPassword(email: String, password: String) =
        sigInRepository.signIn(email,password)

    fun getSignIn() = sigInRepository.getSignIn()


}