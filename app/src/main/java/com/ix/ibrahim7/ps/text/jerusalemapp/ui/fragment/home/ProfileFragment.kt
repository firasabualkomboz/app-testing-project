package com.ix.ibrahim7.ps.text.jerusalemapp.ui.fragment.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.ps.text.jerusalemapp.BR
import com.ix.ibrahim7.ps.text.jerusalemapp.R
import com.ix.ibrahim7.ps.text.jerusalemapp.adapter.GenericAdapter
import com.ix.ibrahim7.ps.text.jerusalemapp.databinding.FragmentHomeBinding
import com.ix.ibrahim7.ps.text.jerusalemapp.databinding.FragmentProfileBinding
import com.ix.ibrahim7.ps.text.jerusalemapp.model.User
import com.ix.ibrahim7.ps.text.jerusalemapp.other.USERS
import com.ix.ibrahim7.ps.text.jerusalemapp.other.setToolbarView
import com.ix.ibrahim7.ps.text.jerusalemapp.ui.dialog.EditAboutDialog
import com.ix.ibrahim7.ps.text.jerusalemapp.ui.viewmodel.HomeViewModel
import com.ix.ibrahim7.ps.text.jerusalemapp.util.Resource
import kotlinx.android.synthetic.main.activity_main.*

class ProfileFragment : Fragment() {

    private val mBinding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private var user: User?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().setToolbarView(mBinding.toolbarView, getString(R.string.profile), true) {
            findNavController().navigateUp()
        }

        viewModel.getUserInfo(requireActivity())

        mBinding.apply {

            btnEditInfo.setOnClickListener {
                EditAboutDialog().show(childFragmentManager,"")
            }

            btnAccount.setOnClickListener {
                val bundle = Bundle().apply {
                    putParcelable(USERS,user)
                }
                findNavController().navigate(R.id.action_profileFragment2_to_editProfileFragment,bundle)
            }

        }

        subscribeToObserver()
    }


    private fun subscribeToObserver() {

        lifecycleScope.launchWhenStarted {
            viewModel.userLiveData.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        user = response.data!!
                        mBinding.apply {
                            tvUsername.text = user!!.username
                            tvAboutMe.text = user!!.about
                        }
                        Log.e("eee UserData", response.data.toString())
                    }
                    is Resource.Error -> {
                        Log.e("eee addPost Error", response.message.toString())
                    }
                }
            })
        }

    }

}