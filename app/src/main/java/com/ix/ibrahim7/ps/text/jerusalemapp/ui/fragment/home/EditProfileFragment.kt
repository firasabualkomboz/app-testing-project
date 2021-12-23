package com.ix.ibrahim7.ps.text.jerusalemapp.ui.fragment.home

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.ps.text.jerusalemapp.R
import com.ix.ibrahim7.ps.text.jerusalemapp.databinding.FragmentEditProfileBinding
import com.ix.ibrahim7.ps.text.jerusalemapp.model.User
import com.ix.ibrahim7.ps.text.jerusalemapp.other.USERS
import com.ix.ibrahim7.ps.text.jerusalemapp.other.getSnackBar
import com.ix.ibrahim7.ps.text.jerusalemapp.other.setToolbarView
import com.ix.ibrahim7.ps.text.jerusalemapp.ui.dialog.LoadingDialog
import com.ix.ibrahim7.ps.text.jerusalemapp.ui.viewmodel.ProfileViewModel
import com.ix.ibrahim7.ps.text.jerusalemapp.util.Resource


class EditProfileFragment : Fragment() {

    private val mBinding by lazy {
        FragmentEditProfileBinding.inflate(layoutInflater)
    }

    private val user by lazy {
        requireArguments().getParcelable<User>(USERS)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    private lateinit var loadingDialog : LoadingDialog
    private val map = HashMap<String,Any>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog()
        requireActivity().setToolbarView(mBinding.toolbarView, getString(R.string.my_account), false) {
            findNavController().navigateUp()
        }

        mBinding.apply {
            txtUsername.setText(user!!.username)
            txtAboutUser.setText(user!!.about)
            txtEmail.setText(user!!.email)

            btnSave.setOnClickListener {
                when {
                    TextUtils.isEmpty(txtUsername.text.toString()) -> {
                        txtUsername.apply {
                            error = getString(R.string.required_field)
                            requestFocus()
                        }
                    }
                    TextUtils.isEmpty(txtAboutUser.text.toString()) -> {
                        txtAboutUser.apply {
                            error = getString(R.string.required_field)
                            requestFocus()
                        }
                    }
                    TextUtils.isEmpty(txtEmail.text.toString()) -> {
                        txtEmail.apply {
                            error = getString(R.string.required_field)
                            requestFocus()
                        }
                    }
                    else -> {
                        map[User.USERNAME] = txtUsername.text.toString().trim()
                        map[User.ABOUT] = txtAboutUser.text.toString().trim()
                        map[User.EMAIL] = txtEmail.text.toString().trim()
                        viewModel.updateUserData(map)
                    }
                }
            }

        }
        subscribeToObserver()

    }


    private fun subscribeToObserver() {

        lifecycleScope.launchWhenStarted {
            viewModel.updateUserDataLiveData.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Loading -> {
                        loadingDialog.show(childFragmentManager, "")
                    }
                    is Resource.Success -> {
                        if (loadingDialog.isAdded)
                            loadingDialog.dismiss()
                        requireActivity().getSnackBar(
                            R.layout.item_custom_snackbar,
                            mBinding.root,
                            "تم تحديث البيانات بنجاح"
                        )
                        findNavController().navigateUp()
                    }
                    is Resource.Error -> {
                        if (loadingDialog.isAdded)
                            loadingDialog.dismiss()
                        Log.e("eee addPost Error", response.message.toString())
                    }
                }
            })
        }
    }


}