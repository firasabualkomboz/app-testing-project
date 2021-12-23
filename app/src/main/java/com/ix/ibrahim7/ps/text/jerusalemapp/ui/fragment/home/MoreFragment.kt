package com.ix.ibrahim7.ps.text.jerusalemapp.ui.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.ps.text.jerusalemapp.BR
import com.ix.ibrahim7.ps.text.jerusalemapp.R
import com.ix.ibrahim7.ps.text.jerusalemapp.adapter.GenericAdapter
import com.ix.ibrahim7.ps.text.jerusalemapp.databinding.FragmentMoreBinding
import com.ix.ibrahim7.ps.text.jerusalemapp.model.More
import com.ix.ibrahim7.ps.text.jerusalemapp.model.getData
import com.ix.ibrahim7.ps.text.jerusalemapp.other.setToolbarView

class MoreFragment : Fragment() ,GenericAdapter.OnListItemViewClickListener<More>{

    private val mBinding by lazy {
        FragmentMoreBinding.inflate(layoutInflater)
    }

    private val moreAdapter by lazy {
        GenericAdapter(R.layout.item_information_about, BR.More,this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().setToolbarView(mBinding.toolbarView, getString(R.string.do_you_want_to_know_more), true) {
            findNavController().navigateUp()
        }

        mBinding.apply {
            rcMoreList.adapter =  moreAdapter
            moreAdapter.submitList(requireActivity().getData())
        }

    }

    override fun onClickItem(itemViewModel: More, type: Int) {
        when(type){
            1->{
                when(itemViewModel.title){
                    getString(R.string.imges)->{
                        findNavController().navigate(R.id.action_moreFragment_to_wallpaperFragment)
                    }
                    getString(R.string.occasions)->{
                        findNavController().navigate(R.id.action_moreFragment_to_occasionsFragment)
                    }
                }
            }
        }
    }

}