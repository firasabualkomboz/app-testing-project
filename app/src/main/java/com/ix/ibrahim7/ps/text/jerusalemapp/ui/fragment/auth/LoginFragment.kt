package com.ix.ibrahim7.ps.text.jerusalemapp.ui.fragment.auth

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.ps.text.jerusalemapp.R
import com.ix.ibrahim7.ps.text.jerusalemapp.databinding.FragmentLoginBinding
import com.ix.ibrahim7.ps.text.jerusalemapp.other.*
import com.ix.ibrahim7.ps.text.jerusalemapp.other.editor
import com.ix.ibrahim7.ps.text.jerusalemapp.other.getSnackBar
import com.ix.ibrahim7.ps.text.jerusalemapp.other.setToolbarView
import com.ix.ibrahim7.ps.text.jerusalemapp.ui.dialog.LoadingDialog
import com.ix.ibrahim7.ps.text.jerusalemapp.ui.viewmodel.auth.SignInViewModel
import com.ix.ibrahim7.ps.text.jerusalemapp.util.Resource

class LoginFragment : Fragment() {

    lateinit var dialog: LoadingDialog
    private val mBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    val viewModel: SignInViewModel by lazy {
        ViewModelProvider(this)[SignInViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().setToolbarView(mBinding.toolbarView, "تسجيل الدخول", true) {}
        dialog = LoadingDialog()

        signIn()
        subscribeToObserve()

        mBinding.apply {

            btnCreateNew.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
            }

            btnForgetPassword.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_resetPasswordFragment)
            }

            btnLoginAsGeust.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment2)
            }
        }
    }

    private fun subscribeToObserve() {
        viewModel.getSignIn().observe(
            viewLifecycleOwner,
            Observer { response ->
                when (response) {
                    is Resource.Loading -> {
                        dialog.show(childFragmentManager, "")
                    }
                    is Resource.Error -> {
                        if (dialog.isAdded)
                            dialog.dismiss()
                        requireActivity().getSnackBar(
                            R.layout.item_custom_snackbar, mBinding.root,
                            response.message!!
                        )
                    }
                    is Resource.Success -> {
                        if (dialog.isAdded)
                            dialog.dismiss()
                        requireActivity().editor().apply {
                            putString(USERID, response.data)
                            putBoolean(ISVERIFIED, true)
                            apply()
                        }
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment2)
                    }
                }
            }
        )
    }

    fun signIn() {
        mBinding.apply {
            btnSignIn.setOnClickListener {
                when {
                    TextUtils.isEmpty(txtEmail.text.toString()) -> {
                        txtEmail.requestFocus()
                        txtEmail.error = getString(R.string.require_field)
                        return@setOnClickListener
                    }
                    TextUtils.isEmpty(txtPassword.text.toString()) -> {
                        txtPassword.requestFocus()
                        txtPassword.error = getString(R.string.require_field)
                        return@setOnClickListener
                    }
                    else -> {
                        viewModel.signInWithEmailAndPassword(
                            txtEmail.text.toString(),
                            txtPassword.text.toString()
                        )
                    }
                }
            }
        }
    }
}
