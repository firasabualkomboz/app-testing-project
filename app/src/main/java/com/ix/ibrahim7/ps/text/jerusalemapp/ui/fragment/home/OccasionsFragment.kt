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
import com.ix.ibrahim7.ps.text.jerusalemapp.databinding.FragmentOccasionsBinding
import com.ix.ibrahim7.ps.text.jerusalemapp.databinding.FragmentProfileBinding
import com.ix.ibrahim7.ps.text.jerusalemapp.model.Occasions
import com.ix.ibrahim7.ps.text.jerusalemapp.model.getData
import com.ix.ibrahim7.ps.text.jerusalemapp.model.getOccasionsData
import com.ix.ibrahim7.ps.text.jerusalemapp.other.setToolbarView
import com.ix.ibrahim7.ps.text.jerusalemapp.ui.dialog.EditAboutDialog

class OccasionsFragment : Fragment() ,GenericAdapter.OnListItemViewClickListener<Occasions>{

    private val mBinding by lazy {
        FragmentOccasionsBinding.inflate(layoutInflater)
    }

    private val occasionsAdapter by lazy {
        GenericAdapter(R.layout.item_occasions, BR.Occasions,this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().setToolbarView(mBinding.toolbarView, getString(R.string.occasions), false) {
            findNavController().navigateUp()
        }

        mBinding.apply {

            rcOccasionsList.adapter =  occasionsAdapter
            occasionsAdapter.submitList(requireActivity().getOccasionsData())

        }
    }

    override fun onClickItem(itemViewModel: Occasions, type: Int) {

    }

}