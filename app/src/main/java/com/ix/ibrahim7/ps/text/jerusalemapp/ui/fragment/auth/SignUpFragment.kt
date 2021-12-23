package com.ix.ibrahim7.ps.text.jerusalemapp.ui.fragment.auth

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.ps.text.jerusalemapp.util.Resource
import com.ix.ibrahim7.ps.text.jerusalemapp.R
import com.ix.ibrahim7.ps.text.jerusalemapp.databinding.FragmentSignUpBinding
import com.ix.ibrahim7.ps.text.jerusalemapp.other.getSnackBar
import com.ix.ibrahim7.ps.text.jerusalemapp.other.setToolbarView
import com.ix.ibrahim7.ps.text.jerusalemapp.ui.dialog.LoadingDialog
import com.ix.ibrahim7.ps.text.jerusalemapp.ui.viewmodel.auth.SignUpViewModel


class SignUpFragment : Fragment() {

    lateinit var dialog: LoadingDialog
    private val mBinding by lazy {
        FragmentSignUpBinding.inflate(layoutInflater)
    }

    val viewModel : SignUpViewModel by lazy {
        ViewModelProvider(this)[SignUpViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().setToolbarView(mBinding.toolbarView, "إنشاء حساب جديد", false) {
            findNavController().navigateUp()
        }
        dialog = LoadingDialog()

        signUp()
        subscribeToObserve()

    }

    private fun subscribeToObserve(){
        viewModel.getSignUp().observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Loading->{
                    dialog.show(childFragmentManager,"")
                }
                is Resource.Error->{
                    if (dialog.isAdded)
                        dialog.dismiss()
                    requireActivity().getSnackBar(R.layout.item_custom_snackbar,mBinding.root,response.message!!)
                }
                is Resource.Success->{
                    if (dialog.isAdded)
                        dialog.dismiss()
                    findNavController().navigate(R.id.action_signUpFragment_to_homeFragment2)
                }
            }
        })
    }

    fun signUp(){
        mBinding.apply {
            btnSignUp.setOnClickListener {
                when {
                    TextUtils.isEmpty(txtUsername.text.toString()) -> {
                        txtUsername.requestFocus()
                        txtUsername.error = getString(R.string.require_field)
                        return@setOnClickListener
                    }
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
                    TextUtils.isEmpty(txtConfirmPassword.text.toString()) -> {
                        txtConfirmPassword.requestFocus()
                        txtConfirmPassword.error = getString(R.string.require_field)
                        return@setOnClickListener
                    }
                    else -> {
                        viewModel.signUpUser(txtUsername.text.toString(),txtEmail.text.toString(),txtPassword.text.toString())
                    }
                }
            }
        }
    }

}