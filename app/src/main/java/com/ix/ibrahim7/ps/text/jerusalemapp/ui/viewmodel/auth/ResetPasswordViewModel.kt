package com.ix.ibrahim7.ps.text.jerusalemapp.ui.viewmodel.auth

import androidx.lifecycle.ViewModel
import com.ix.ibrahim7.ps.text.jerusalemapp.repository.auth.ResetPasswordRepository
import com.ix.ibrahim7.ps.text.jerusalemapp.repository.auth.SignInRepository


class ResetPasswordViewModel(
    private val resetPasswordRepository: ResetPasswordRepository = ResetPasswordRepository()
) : ViewModel() {

    fun forgetPassword(email: String) =
        resetPasswordRepository.forgetPassword(email)

    fun getforgetPassword() = resetPasswordRepository.getforgetPassword()


}