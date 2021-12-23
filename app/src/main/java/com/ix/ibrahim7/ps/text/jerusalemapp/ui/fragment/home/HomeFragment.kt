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
import com.ix.ibrahim7.ps.text.jerusalemapp.model.Post
import com.ix.ibrahim7.ps.text.jerusalemapp.model.User
import com.ix.ibrahim7.ps.text.jerusalemapp.other.*
import com.ix.ibrahim7.ps.text.jerusalemapp.ui.dialog.LoadingDialog
import com.ix.ibrahim7.ps.text.jerusalemapp.ui.dialog.PostInformationDialog
import com.ix.ibrahim7.ps.text.jerusalemapp.ui.viewmodel.HomeViewModel
import com.ix.ibrahim7.ps.text.jerusalemapp.util.Resource
import kotlinx.android.synthetic.main.activity_main.*


class HomeFragment : Fragment(),GenericAdapter.OnListItemViewClickListener<Post> {

    private val mBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val postAdapter by lazy {
        GenericAdapter(R.layout.item_post,BR.Post,this)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    lateinit var dialog: LoadingDialog
    private var user: User ?= null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = LoadingDialog()
        requireActivity().bottom_navigation.menu.findItem(R.id.homeFragment2).isChecked = true
        requireActivity().setToolbarView(mBinding.toolbarView, getString(R.string.home_page), true,
                hasMenu = true,
                menuIcon = R.drawable.ic_add
        ) {
            val bundle = Bundle().apply {
                    putParcelable(USERS,user)
                }
            findNavController().navigate(R.id.action_homeFragment2_to_addPostFragment,bundle)
        }

        viewModel.getUserInfo(requireActivity())

        mBinding.rcPostList.adapter = postAdapter

        subscribeToObserver()
    }

    private fun subscribeToObserver() {

        lifecycleScope.launchWhenStarted {
            viewModel.userLiveData.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Loading ->{
                    }
                    is Resource.Success  -> {
                        user = response.data!!
                        Log.e("eee UserData",response.data.toString())
                    }
                    is Resource.Error -> {
                        Log.e("eee addPost Error",response.message.toString())
                    }
                }
            })
        }

        lifecycleScope.launchWhenStarted {
            viewModel.postsLiveData.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Loading ->{
                        dialog.show(childFragmentManager,"")
                    }
                    is Resource.Success  -> {
                        if (dialog.isAdded)
                            dialog.dismiss()
                        postAdapter.submitList(response.data!!)
                        Log.e("eee UserData",response.data.toString())
                    }
                    is Resource.Error -> {
                        if (dialog.isAdded)
                            dialog.dismiss()
                        Log.e("eee addPost Error",response.message.toString())
                    }
                }
            })
        }

    }


    override fun onClickItem(itemViewModel: Post, type: Int) {
        when(type){
            ONE ->{
                if (itemViewModel.post_type == IMAGE){
                    PostInformationDialog(itemViewModel,EnumConstant.IMAGE).show(childFragmentManager,"")
                }else if (itemViewModel.post_type == VIDEO){
                    PostInformationDialog(itemViewModel,EnumConstant.VIDEO).show(childFragmentManager,"")
                }
            }
        }
    }

}