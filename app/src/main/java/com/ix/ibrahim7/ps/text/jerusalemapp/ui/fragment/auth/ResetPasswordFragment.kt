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
import com.ix.ibrahim7.ps.text.jerusalemapp.R
import com.ix.ibrahim7.ps.text.jerusalemapp.databinding.FragmentResetPasswordBinding
import com.ix.ibrahim7.ps.text.jerusalemapp.other.getSnackBar
import com.ix.ibrahim7.ps.text.jerusalemapp.other.setToolbarView
import com.ix.ibrahim7.ps.text.jerusalemapp.ui.dialog.LoadingDialog
import com.ix.ibrahim7.ps.text.jerusalemapp.ui.viewmodel.auth.ResetPasswordViewModel
import com.ix.ibrahim7.ps.text.jerusalemapp.util.Resource

class ResetPasswordFragment : Fragment() {

    lateinit var dialog: LoadingDialog
    private val mBinding by lazy {
        FragmentResetPasswordBinding.inflate(layoutInflater)
    }

    val viewModel : ResetPasswordViewModel by lazy {
        ViewModelProvider(this)[ResetPasswordViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().setToolbarView(mBinding.toolbarView, getString(R.string.reset_password), false) {
            findNavController().navigateUp()
        }
        dialog = LoadingDialog()

        forgetPassword()
        subscribeToObserve()

    }

    private fun subscribeToObserve()
    {
    viewModel.getforgetPassword().observe(viewLifecycleOwner, Observer { response->
        when(response){
        is Resource.Loading->
        {
        dialog.show(childFragmentManager,"")
        }
        is Resource.Error-> // when any error
        {
        if (dialog.isAdded)
        dialog.dismiss()
        requireActivity().getSnackBar(R.layout.item_custom_snackbar,mBinding.root,response.message!!)
        }
        is Resource.Success-> // when success
        {
        if (dialog.isAdded)
        dialog.dismiss()
        findNavController().navigateUp()
        }
        }
    })
    }

    fun forgetPassword(){
        mBinding.apply {
            btnResetPassword.setOnClickListener {
                when {
                    TextUtils.isEmpty(txtEmail.text.toString()) -> {
                        txtEmail.requestFocus()
                        txtEmail.error = getString(R.string.require_field)
                        return@setOnClickListener
                    }
                    else -> {
                        viewModel.forgetPassword(txtEmail.text.toString())
                    }
                }
            }
        }
    }

}